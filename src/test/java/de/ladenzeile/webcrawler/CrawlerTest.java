package de.ladenzeile.webcrawler;

import org.junit.Ignore;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.List;

import static org.junit.Assert.*;

public class CrawlerTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalSeedUrlTest() {
		String seedUrl = "http://www.golem.de/staticrl/images/logo.png";
		new Crawler(seedUrl);
	}

	@Test
	public void visitNonHTMLPageTest() {
		HTMLPage page = HTMLPage
				.createFromUrl("http://www.golem.de/staticrl/images/logo.png");
		assertNull(page);
	}

	@Test
	public void findLinksInPageTest() throws MalformedURLException {
		HTMLPage page = HTMLPage.createFromUrl("http://www.golem.de");
		assertTrue(page.findLinksBeginWithHttp().contains(
				"http://www.golem.de/specials/google/"));
	}

	@Test
	public void crawlNonExistingPageTest() {
		String seedUrl = "http://www.spiegel-qc.de/deutsch/partner__preise/heise_online/video.php";
		Crawler crawler = new Crawler(seedUrl);
		crawler.startCrawling();
		List<String> urLs = crawler.getResultURLs();
		assertTrue(urLs.contains(seedUrl));
		assertTrue(urLs.size() == 1);
	}

	@Test
	public void crawlerMiniTest() {
		Crawler crawler = new Crawler("http://www.golem.de");
		int expectedResultSize = 200;
		crawler.setMinResultSize(expectedResultSize);
		crawler.startCrawling();
		List<String> urls = crawler.getResultURLs();
		System.out.println(urls);
		assertNotNull(urls);
		assertTrue(urls.size() >= expectedResultSize);
	}

	@Test @Ignore
	public void crawl10000LinksTest() {
		Crawler crawler = new Crawler("http://www.golem.de");
		int expectedResultSize = 10000;
		crawler.setMinResultSize(expectedResultSize);
		crawler.startCrawling();
		List<String> urls = crawler.getResultURLs();
		System.out.println(urls);
		assertNotNull(urls);
		assertTrue(urls.size() >= expectedResultSize);
	}

}
