package com.home.naruto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

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
	private List<String> pageNameLocaction;
	private String chapter;
	private List<Image> images;
	

	public Downloader(){
		chapterLocation="";
		pageNameLocaction=new ArrayList<String>();
		images=new ArrayList<Image>();
			}

	private void downloadPage(URL imgSrcUrl, int i) throws IOException {
		
		HttpURLConnection srcConnection = (HttpURLConnection) imgSrcUrl.openConnection();
		srcConnection.setConnectTimeout(15 * 1000);
		ReadableByteChannel rbc = Channels.newChannel(srcConnection.getInputStream());
		String pageNameLoc=chapterLocation+"/"+chapter+"/Naruto-" + i + ".jpg";
		pageNameLocaction.add(pageNameLoc);
		FileOutputStream fos = new FileOutputStream(chapterLocation+"/"+chapter+"/Naruto-" + i + ".jpg");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
	}

	public Integer getLatestChapterNumber() throws IOException {

		Document doc = Jsoup.connect(NARUTO_PAGE).timeout(15 * 1000).get();
		Elements latestChapterElement = doc.getElementById("latestchapters").select("ul > li:first-child >a");
		
		String chapterNo = latestChapterElement.html().substring(7);
		latestChapterNumber = Integer.parseInt(chapterNo);
		System.out.println(latestChapterNumber);
		return latestChapterNumber;
		
	}

	public void downloadChapter(String chapterFromUI){
		System.out.println("Before: "+pageNameLocaction.size());
		pageNameLocaction.clear();
		//images.clear();
		System.out.println("After :" +pageNameLocaction.size());
		chapter=chapterFromUI;
		String urlSuffix=chapterMap.get(Integer.parseInt(chapterFromUI.substring(7)));
		System.out.println(urlSuffix);
		int i=1;
	
		String url = BASE_URL + urlSuffix;
		Document doc;
		try {
			doc = Jsoup.connect(url).timeout(7 * 1000).get();
			Element img = doc.getElementById("img");
			Elements pageNo = doc.select("select > option:last-child");
			int maxPages = Integer.parseInt(pageNo.html());
			System.out.println(maxPages);
			URL imgSrcUrl = new URL(img.attr("src"));
//			System.out.println(imgSrcUrl);
			File dir=new File(chapterLocation+"/"+chapter);
			dir.mkdir();
			
			//downloadPage(imgSrcUrl, i);
			addImages(imgSrcUrl);
			
			for (i = 2; i <=maxPages; i++) {
				
				Element imgHolder=doc.getElementById("imgholder");
				String nextSuffix=imgHolder.select("a[href]").attr("href");
				url = BASE_URL + nextSuffix;
				doc = Jsoup.connect(url).timeout(7 * 1000).get();
				img = doc.getElementById("img");
    			System.out.print("* ");
				imgSrcUrl = new URL(img.attr("src"));
				//downloadPage(imgSrcUrl, i);
				addImages(imgSrcUrl);
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	
		
	
	}
	public void addImages(URL imageUrl)
	{
		try {
			System.out.println(imageUrl);
			images.add(Image.getInstance(imageUrl));
		} catch (BadElementException e) {
			
			e.printStackTrace();
		} catch (MalformedURLException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
			
			System.out.println("URL not found !");
			e.printStackTrace();
		}
	}
	

	public  void setChapterLocation(String chapterLocation) {
		Downloader.chapterLocation = chapterLocation;
	}

	public static void setChapterMap(Map<Integer, String> chapterMap) {
		Downloader.chapterMap = chapterMap;
	}

	public List<String> getPageNameLocation() {
		return pageNameLocaction;
	}

	public String getChapter() {
		return chapter;
	}

	public List<Image> getImages() {
		return images;
	}	
}
