package com.home.naruto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author negi_s
 *
 */
public class Downloader {

	private static final String NARUTO_PAGE = "http://www.mangapanda.com/93/naruto.html";
	private static final String BASE_URL = "http://www.mangapanda.com/naruto/";
	private static int latestChapterNumber;

	/**
	 * @param args
	 */
	public void download() {
		// TODO Auto-generated method stub

		int i = 1;

		try {
			getLatestChapterNumber();
			String url = BASE_URL + latestChapterNumber + "/" + i;
			Document doc = Jsoup.connect(url).timeout(5 * 1000).get();
			Element img = doc.getElementById("img");
			Elements pageNo = doc.select("select > option:last-child");
			int maxPages = Integer.parseInt(pageNo.html());

			URL imgSrcUrl = new URL(img.attr("src"));
			File dir=new File("Naruto_Chapter");
			dir.mkdir();
			downloadPage(imgSrcUrl, i);

			for (i = 2; i <= maxPages; i++) {
				url = BASE_URL + latestChapterNumber + "/" + i;
				doc = Jsoup.connect(url).timeout(5 * 1000).get();

				img = doc.getElementById("img");
				System.out.print("*");
				imgSrcUrl = new URL(img.attr("src"));
				downloadPage(imgSrcUrl, i);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void downloadPage(URL imgSrcUrl, int i) throws IOException {
		// TODO Auto-generated method stub
		HttpURLConnection srcConnection = (HttpURLConnection) imgSrcUrl
				.openConnection();
		srcConnection.setConnectTimeout(15 * 1000);
		ReadableByteChannel rbc = Channels.newChannel(srcConnection
				.getInputStream());
		
		FileOutputStream fos = new FileOutputStream("Naruto_Chapter/Naruto-" + i + ".jpg");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
	}

	private static void getLatestChapterNumber() throws IOException {

		Document doc = Jsoup.connect(NARUTO_PAGE).timeout(15 * 1000).get();
		Elements latestChapterElement = doc.getElementById("latestchapters")
				.select("ul > li:first-child >a");
		// .select("ul > li:first-child");
		String chapterNo = latestChapterElement.html().substring(7);
		System.out.println(chapterNo);
		latestChapterNumber = Integer.parseInt(chapterNo);

	}
}
