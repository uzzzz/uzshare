package com.uzshare.sitemapparser;

import java.io.InputStream;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class SitemapParser {

	private SitemapParserCallback callback;

	public SitemapParser(SitemapParserCallback callback) {
		this.callback = callback;
	}

	public void parseXml(String sitemap) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();

			InputStream in = new URL(sitemap).openStream();
			try {
				in = new GZIPInputStream(in);
			} catch (Exception e) {
				try {
					in.close();
				} catch (Exception ee) {
				}
				in = new URL(sitemap).openStream();
			}
			Document document = db.parse(in);

			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			NodeList sitemapNodes = (NodeList) xpath.evaluate("/sitemapindex/sitemap/loc", document,
					XPathConstants.NODESET);
			for (int i = 0; i < sitemapNodes.getLength(); i++) {
				String sitemapUrl = sitemapNodes.item(i).getTextContent();
				if (callback == null) {
					parseXml(sitemapUrl);
				} else if (callback.ignoreSitemap(sitemapUrl)) {
					callback.sitemap(sitemapUrl);
				} else {
					parseXml(sitemapUrl);
					callback.sitemap(sitemapUrl);
				}
			}

			NodeList urlNodes = (NodeList) xpath.evaluate("/urlset/url/loc", document, XPathConstants.NODESET);
			for (int i = 0; i < urlNodes.getLength(); i++) {
				String url = urlNodes.item(i).getTextContent();
				if (callback != null) {
					callback.url(url);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parseTxt(String sitemap) {

	}
}
