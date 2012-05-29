package de.ladenzeile.webcrawler;

import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.html.HTML.Tag;

public class AnchorTag {
	
	private Tag tag;
	private AttributeSet attributeSet;

	public AnchorTag(Tag tag, AttributeSet attributeSet) {
		this.tag = tag;
		this.attributeSet = attributeSet;
	}
	
	public String getAttribute(Attribute key) {
		return (String) attributeSet.getAttribute(key);
	}
	
	public boolean isAttributeDefined(Attribute key) {
		return attributeSet.isDefined(key);
	}
	
	public boolean hasRelNofollow() {
		if (isAttributeDefined(Attribute.REL)) {
			return getAttribute(Attribute.REL).equalsIgnoreCase("nofollow");
		}
		return false;
	}
	
	public boolean hasValidUrlAsHrefValue() {
		if (isAttributeDefined(Attribute.HREF)) {
			return getAttribute(Attribute.HREF).startsWith("http://");
		}
		return false;
	}
	
	public String getHrefAttributeValue() {
		return (String) getAttribute(Attribute.HREF);
	}
	
}
