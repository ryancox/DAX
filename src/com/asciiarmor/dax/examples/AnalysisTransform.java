package com.asciiarmor.dax.examples;


import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;

import com.asciiarmor.dax.Path;
import com.asciiarmor.dax.Transformer;

/**
 * Demonstrate how DAX can be used for analysis of XML documents
 * 
 * Using Romeo and Juliet XML document, populate Map<String, Integer> with 
 * speakers and number of speaker's lines
 * 
 */
public class AnalysisTransform extends Transformer {

	public Map<String, Integer> lines = new HashMap<String, Integer>( );

	private XPath speakerXPath = DocumentHelper
			.createXPath("preceding-sibling::speaker");

	/**
	 * Clear map before processing starts. Allows for multiple execution in
	 * same thread.
	 */
	public void init() {
		lines.clear();
	}
	
	/**
	 * Dump out contents of map when processing completes
	 */
	public void complete() {
		System.out.println(lines);
	}

	/**
	 * 
	 * Match all romeo and juliet line elements and update speaker map
	 * 
	 * @param node line element
	 * 
	 */
	@Path("//line[../speaker='Rom.' or ../speaker='Jul.']") 
	public void speaker(
			Node node) {
		// grab speaker node from preceeding sibling axis
		Node speakerNode = speakerXPath.selectSingleNode(node);
		String speaker = speakerNode.getStringValue();

		updateStatistics(speaker);
		
	}

	/**
	 * @param speaker
	 */
	private void updateStatistics(String speaker) {
		Integer count = (Integer) lines.get(speaker);
		if (count == null) {
			count = new Integer(1);
		} else {
			int value = count.intValue();
			count = new Integer(value + 1);
		}
		lines.put(speaker, count);
	}
}