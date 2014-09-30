package de.ladenzeile.webcrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class Crawler {

	static Logger log = Logger.getLogger("de.ladenzeile.webcrawler.Crawler");

	private int expectedResultSize = 200;
	private List<String> unvisitedURLs = new ArrayList<String>();
	private List<String> crawledURLs = new ArrayList<String>();
	private List<String> resultURLs = new ArrayList<String>();

	public Crawler(String seedUrl) {
		if (HTMLPage.isHtml(seedUrl)) {
			unvisitedURLs.add(seedUrl);
		} else {
			throw new IllegalArgumentException("illegal seed url");
		}
	}

	public void startCrawling() {
		log.info("start crawling ...");
		while (!unvisitedURLs.isEmpty()) {
			if (isEnoughUrlsFound()) {
				return;
			}
			String unvisitedUrl = unvisitedURLs.remove(0);
			resultURLs.add(unvisitedUrl);
			crawlPage(unvisitedUrl);
		}
		log.info("finished!");
	}

	private boolean isEnoughUrlsFound() {
		return resultURLs.size() >= expectedResultSize;
	}

	private void crawlPage(String unvisitedUrl) {
		HTMLPage page = HTMLPage.createFromUrl(unvisitedUrl);
		if (page != null) {
			log.info("crawling: " + unvisitedUrl);
			findUnvisitedLinksInPage(page);
			crawledURLs.add(unvisitedUrl);
		}
	}

	private void findUnvisitedLinksInPage(HTMLPage page) {
		Set<String> links = page.findLinksBeginWithHttp();
		for (String l : links) {
			if (!page.getUrl().contains(l) && !crawledURLs.contains(l)
					&& !unvisitedURLs.contains(l))
				if (HTMLPage.isHtml(l)) {
					resultURLs.add(l);
					unvisitedURLs.add(l);
				}
		}
		log.info("resultURLs.size(): " + resultURLs.size());
	}

	public void setMinResultSize(int number) {
		this.expectedResultSize = number;
	}

	public List<String> getResultURLs() {
		return resultURLs;
	}

}
