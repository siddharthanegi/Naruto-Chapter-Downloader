package com.home.naruto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Map;

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
	private static final String BASE_URL = "http://www.mangapanda.com";
	private  static String chapterLocation;
	private static int latestChapterNumber;
	private static Map<Integer,String> chapterMap;
	private String chapter;

	public Downloader(){
		chapterLocation="";
			}

	private void downloadPage(URL imgSrcUrl, int i) throws IOException {
		
		HttpURLConnection srcConnection = (HttpURLConnection) imgSrcUrl.openConnection();
		srcConnection.setConnectTimeout(15 * 1000);
		ReadableByteChannel rbc = Channels.newChannel(srcConnection.getInputStream());
		
		FileOutputStream fos = new FileOutputStream(chapterLocation+"/"+chapter+"/Naruto-" + i + ".jpg");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
	}

	public Integer getLatestChapterNumber() throws IOException {

		Document doc = Jsoup.connect(NARUTO_PAGE).timeout(15 * 1000).get();
		Elements latestChapterElement = doc.getElementById("latestchapters")
				.select("ul > li:first-child >a");
		
		String chapterNo = latestChapterElement.html().substring(7);
		latestChapterNumber = Integer.parseInt(chapterNo);
		System.out.println(latestChapterNumber);
		return latestChapterNumber;
		
	}

	public void downloadChapter(String chapterFromUI){
		
		chapter=chapterFromUI;
		String urlSuffix=chapterMap.get(Integer.parseInt(chapterFromUI.substring(7)));
		System.out.println(urlSuffix);
		int i=1;
	
		String url = BASE_URL + urlSuffix;
		Document doc;
		try {
			doc = Jsoup.connect(url).timeout(5 * 1000).get();
			Element img = doc.getElementById("img");
			Elements pageNo = doc.select("select > option:last-child");
			int maxPages = Integer.parseInt(pageNo.html());
			System.out.println(maxPages);
			URL imgSrcUrl = new URL(img.attr("src"));
//			System.out.println(imgSrcUrl);
			File dir=new File(chapterLocation+"/"+chapter);
			dir.mkdir();
			downloadPage(imgSrcUrl, i);
			
			for (i = 2; i <=maxPages; i++) {
				
				Element imgHolder=doc.getElementById("imgholder");
				String nextSuffix=imgHolder.select("a[href]").attr("href");
				url = BASE_URL + nextSuffix;
				doc = Jsoup.connect(url).timeout(5 * 1000).get();
				img = doc.getElementById("img");
    			System.out.print("*");
				imgSrcUrl = new URL(img.attr("src"));
				downloadPage(imgSrcUrl, i);
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	
		
	
	}

	public  void setChapterLocation(String chapterLocation) {
		Downloader.chapterLocation = chapterLocation;
	}

	public static void setChapterMap(Map<Integer, String> chapterMap) {
		Downloader.chapterMap = chapterMap;
	}	
}
