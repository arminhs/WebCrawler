package de.ladenzeile.webcrawler;

import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		Crawler crawler = new Crawler("http://www.spiegel.de");
		crawler.setMinResultSize(200);
		crawler.startCrawling();
		List<String> results = crawler.getResultURLs();
		System.out.println(results);

	}

}
