package com.asciiarmor.dax.examples;


import java.util.ArrayList;
import java.util.List;

import org.dom4j.Node;

import com.asciiarmor.dax.Path;
import com.asciiarmor.dax.Transformer;

/**
 * 
 * Demonstrate how DAX can be used to bind XML documents to objects
 * 
 * Convert a 10 item Slashdot RSS feed into a List<RSSItem> 
 * 
 */
public class BindingTransform extends Transformer {

	public List<RSSItem> items = new ArrayList<RSSItem>();
	
	private RSSItem currentItem;
	
	public BindingTransform() {

		// tell engine about anticipated namespace
		setNamespace("dc", "http://purl.org/dc/elements/1.1/");
	}

	/**
	 * Clear out items list to allow reuse in same thread
	 */
	public void init() {
		items.clear();
	}

	/**
	 * Dump out titles of RSS items when processing completes
	 */
	public void complete() {
		for (RSSItem i : items) {
			System.out.println(i.getTitle());
		}
	}

	/**
	 * 
	 * Match item elements and create counterpart RSSItem object for each
	 *
	 * @param node Item node
	 */
	@Path("//item") 
	public void item(Node node) {
		currentItem = new RSSItem();
		items.add(currentItem);
		applyTemplates(node);
	}

	/**
	 * 
	 * Match title and set object field
	 *  
	 * @param node Title node
	 */
	@Path("item/title") 
	public void title(Node node) {
		currentItem.setTitle(node.getStringValue());
	}

	/**
	 * 
	 * Match link and set object field
	 * 
	 * @param node Link element
	 */
	@Path("item/link") 
	public void link(Node node) {
		currentItem.setLink(node.getStringValue());
	}

	/**
	 * 
	 * Match description and set object field
	 * 
	 * @param node Description element
	 */
	@Path("item/description") 
	public void description(Node node) {
		currentItem.setDescription(node.getStringValue());
	}

	/**
	 * Match dublin core creator element and set object field
	 * 
	 * @param node Creator element
	 */
	@Path("item/dc:creator") 
	public void creator(Node node) {
		currentItem.setCreator(node.getStringValue());
	}
}
