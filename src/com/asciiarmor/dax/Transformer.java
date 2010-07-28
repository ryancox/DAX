package com.asciiarmor.dax;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.rule.Action;
import org.dom4j.rule.Rule;
import org.dom4j.rule.Stylesheet;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.SimpleVariableContext;

/**
 * 
 * Main workhorse class that handles interogation of annotations and 
 * registration of patterns
 * 
 */
public class Transformer {

	private final Map<XPathPatternEx, Method> map = getMethodMap();
	private final SimpleVariableContext variableContext = new SimpleVariableContext();
	private final SimpleNamespaceContext namespaceContext = new SimpleNamespaceContext();
	private final Stylesheet style = new Stylesheet();

	
	/**
	 * 
	 *  Finds annotations associated with subclass and converts them into XPath patterns
	 *   
	 * @return Map<XPathPatternEx, Method> containing methods and corresponding XPath 
	 * patterns
	 */
	private Map<XPathPatternEx, Method> getMethodMap() {
		Map<XPathPatternEx, Method> localMap = new HashMap<XPathPatternEx, Method>();

		for (Method m : this.getClass().getMethods()) {
			if (m.isAnnotationPresent(Path.class)) {
				Path path = m.getAnnotation(Path.class);
				XPathPatternEx pattern = new XPathPatternEx(path.value(),
						namespaceContext, variableContext);
				localMap.put(pattern, m);
			}
		}

		return localMap;
	}

	
	/**
	 * Default constructor 
	 */
	public Transformer() {
		for (XPathPatternEx p : map.keySet()) {
			Rule r = new Rule();
			r.setPattern(DocumentHelper.createPattern(p.getText()));
			r.setAction(new DynaAction(this, p));
			style.addRule(r);
		}

	}

	/**
	 * 
	 * Primary entrypoint to Transformer class
	 * 
	 * @param doc DOM4J Document to tranform
	 */
	public void execute(Document doc) {
		try {
			init();
			style.applyTemplates(doc);
			complete();
		} catch (Exception e) {
			throw new TransformerException(e);
		}
	}

	/**
	 * 
	 * Method called before transform processing begins. Optionally overriden by subclass.
	 *
	 */
	public void init() {
		;
	}

	/**
	 * 
	 * Method called when transform processing complete. Optionally overriden by subclass.
	 *
	 */
	public void complete() {
		;
	}

	/**
	 * 
	 * Method to recurse processing
	 * 
	 * @param node Context node
	 *
	 */
	public void applyTemplates(Node node) {

		try {
			style.applyTemplates(node);
		} catch (Exception e) {
			throw new TransformerException(e);
		}
	}

	/**
	 * 
	 * Configure anticipated namespaces for document 
	 * 
	 * @param prefix Namespace textual prefix
	 * @param uri Namespace URI
	 */
	public void setNamespace(String prefix, String uri) {
		namespaceContext.addNamespace(prefix, uri);
	}

	/**
	 * 
	 * Bind values to variables that may be referenced in annotation
	 * XPath patterns 
	 * 
	 * @param name Textual variable name
	 * @param value Value to associate with name
	 */
	public void setVariable(String name, Object value) {
		variableContext.setVariableValue(name, value);
	}

	/**
	 *
	 * Helper class to store method references
	 * 
	 */
	class DynaAction implements Action {
		XPathPatternEx p;

		Object obj;

		public DynaAction(Object obj, XPathPatternEx p) {
			this.p = p;
			this.obj = obj;
		}

		public void run(Node node) {
			try {
				map.get(p).invoke(obj, node);
			} catch (Exception e) {
				throw new TransformerException(e);
			}

		}

	}
}
