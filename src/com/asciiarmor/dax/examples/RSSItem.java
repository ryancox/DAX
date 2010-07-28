package com.asciiarmor.dax.examples;

public class RSSItem {

	private String title;
	private String description;
	private String link;
	private String creator;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
}
