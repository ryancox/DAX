package com.asciiarmor.dax;


import org.dom4j.xpath.XPathPattern;
import org.jaxen.NamespaceContext;
import org.jaxen.VariableContext;
import org.jaxen.pattern.Pattern;


/**
 * Augment existing XPathPattern class with methods to allow 
 * namespace and variable context to be set
 * 
 */
public class XPathPatternEx extends XPathPattern {

	public XPathPatternEx(String path) {
		super(path);

	}

	public XPathPatternEx(String path, NamespaceContext nc, VariableContext vc ) {
		super(path);
		setNamespaceContext(nc);
		setVariableContext( vc );
	}

	public XPathPatternEx(Pattern pattern) {
		super(pattern);
	}
	
	public void setNamespaceContext(NamespaceContext nc) {
		getContextSupport().setNamespaceContext(nc);
	}
	

}
