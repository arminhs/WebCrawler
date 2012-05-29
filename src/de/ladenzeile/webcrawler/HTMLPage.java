package de.ladenzeile.webcrawler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.text.html.HTMLEditorKit.Parser;

public class HTMLPage {

	static Logger log = Logger.getLogger("de.ladenzeile.webcrawler.HTMLPage");

	private URL url;

	private HTMLPage() {
	}

	public static HTMLPage createFromUrl(String url) {
		if (url.startsWith("http://")) {
			URL u = createUrl(url);
			if (isHtml(u)) {
				HTMLPage htmlPage = new HTMLPage();
				htmlPage.url = u;
				return htmlPage;
			}
		}
		return null;
	}

	private static URL createUrl(String url) {
		URL u = null;
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			log.severe(e.getMessage());
		}
		return u;
	}

	private static boolean isHtml(URL url) {
		boolean isHtml = false;
		try {
			URLConnection urlConnection = url.openConnection();
			String contentType = urlConnection.getContentType();
			//String contentType = URLConnection.guessContentTypeFromStream(url.openStream());
			if (contentType == null) {
				return false;
			}
			isHtml = contentType.startsWith("text/html", 0);
		} catch (Exception e) {
			log.warning(e.getStackTrace().toString());
			return false;
		}
		return isHtml;
	}

	public Set<String> findLinksBeginWithHttp() {
		Set<String> results = new HashSet<String>();
		for (AnchorTag t : findAllAnchorTags()) {
			if (t.hasValidUrlAsHrefValue() && !t.hasRelNofollow()) {
				results.add(t.getHrefAttributeValue());
			}
		}
		return results;
	}

	private Set<AnchorTag> findAllAnchorTags() {
		Callback callback = parseHTML();
		return callback.getAnchorTags();

	}

	private Callback parseHTML() {
		Callback callback = null;
		try {
			InputStream inputStream = url.openStream();
			InputStreamReader streamReader = new InputStreamReader(inputStream);
			callback = new Callback();
			parse(streamReader, callback);
		} catch (FileNotFoundException e) {
			log.warning("Got a 404 response for: " + url.toString());
			return new Callback();
		} catch (IOException e) {
			log.severe(e.getMessage());
			return new Callback();
		}
		return callback;
	}

    private void parse(InputStreamReader streamReader, Callback callback) throws IOException {
        Parser htmlParser = new HTMLParser().getParser();
        htmlParser.parse(streamReader, callback, true);
    }
    
    public static boolean isHtml(String url) {
    	return isHtml(createUrl(url));
    }
    
    public String getUrl() {
    	return url.toString();
    }

}
