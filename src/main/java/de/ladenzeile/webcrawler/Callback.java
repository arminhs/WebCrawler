package de.ladenzeile.webcrawler;

import java.util.HashSet;
import java.util.Set;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

public class Callback extends ParserCallback {

	private Set<AnchorTag> anchorTags = new HashSet<AnchorTag>();

	@Override
	public void handleStartTag(Tag t, MutableAttributeSet a, int pos) {
		findAnchorTags(t, a);
	}

	private void findAnchorTags(Tag t, MutableAttributeSet a) {
		if (t.equals(HTML.Tag.A)) {
			AnchorTag tag = new AnchorTag(t, a.copyAttributes());
			anchorTags.add(tag);
		}
	}
	
	public Set<AnchorTag> getAnchorTags() {
		return anchorTags;
	}


}
