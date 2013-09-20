package com.home.naruto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ChapterList {

	private static final String NARUTO_PAGE = "http://www.mangapanda.com/93/naruto.html";
	private static final String SERIALIZED_HASHMAP = "HelperFiles_DND/Chapter_List.dat";
	private static final String UPDATE_LOG = "HelperFiles_DND/Update_Log.dat";
	private Map<Integer, String> chapterMap;

	public Vector<String> getChapterListFromWebsite() {

		Document doc;
		try {
			doc = Jsoup.connect(NARUTO_PAGE).timeout(7 * 1000).get();

			Element chapterListDiv = doc.getElementById("listing");
			Elements urls = chapterListDiv.select("a[href]");
			chapterMap = new TreeMap<Integer, String>();
			Vector<String> vector = new Vector<String>();
			int i = 1;

			for (Element e : urls) {

				chapterMap.put(i, e.attr("href"));
				vector.add("Naruto " + i);
				i++;

			}
			System.out.println(chapterMap);
			serializeHashMap();
			Downloader.setChapterMap(chapterMap);
			return vector;
		} catch (IOException e1) {
			
			e1.printStackTrace();
			return null;

		}

	}

	@SuppressWarnings("unchecked")
	public Vector<String> getChapterListFromSerializedObject()	throws IOException, ClassNotFoundException {
		
		Vector<String> vector = new Vector<String>();

		FileInputStream fis;
		fis = new FileInputStream(SERIALIZED_HASHMAP);

		ObjectInputStream ois = new ObjectInputStream(fis);
		Object o = ois.readObject();
		if (o instanceof TreeMap) {
			chapterMap = (TreeMap<Integer, String>) o;
			ArrayList<Integer> temp = new ArrayList<Integer>(chapterMap.keySet());
			int index = 0, lastElement = temp.size();
			for (index = 0; index < lastElement; index++) {
				vector.add(index, "Naruto " + temp.get(index));
			}
			Downloader.setChapterMap(chapterMap);
			System.out.println(chapterMap);
			System.out.println(vector);
			ois.close();
			fis.close();
			return vector;
		}
		ois.close();
		fis.close();
		return null;

	}

	public void serializeHashMap() throws IOException {
		

		File dir = new File("HelperFiles_DND");
		dir.mkdir();
		FileOutputStream fos = new FileOutputStream(SERIALIZED_HASHMAP);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(chapterMap);
		System.out.println("***********serializeHashMap****************");
		oos.close();
		fos.close();
		updateLog();
	}

	public void updateLog() {

		try {
			File updateLog = new File(UPDATE_LOG);
			FileWriter writer = new FileWriter(updateLog);
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String currentDate = df.format(new Date());
			writer.write(currentDate);
			writer.close();
			System.out.println("**************UpdateLog****************");

		} catch (IOException e) {
			System.out.println("Failed to Log Update !!");
			e.printStackTrace();
		}

	}

	@SuppressWarnings("resource")
	public Vector<String> getChapterList() {

		try {
			BufferedReader x = new BufferedReader(new FileReader(UPDATE_LOG));
			String dateStr = x.readLine();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date updateLogDate = df.parse(dateStr);
			System.out.println(updateLogDate.toString());
			Date currentDate = new Date();
			Calendar currentCalendar = Calendar.getInstance();
			Calendar logCalendar=Calendar.getInstance();
			logCalendar.setTime(updateLogDate);
			
			int diffDays = (int) ((currentDate.getTime() - updateLogDate.getTime()) / (1000 * 60 * 60 * 24));
			
			boolean isNoWednesday= diffDays<6 && (currentCalendar.get(Calendar.DAY_OF_WEEK)>Calendar.WEDNESDAY
									&& logCalendar.get(Calendar.DAY_OF_WEEK)<Calendar.WEDNESDAY);
			
									
					
			if ((currentCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) || diffDays > 6	|| isNoWednesday ) {
				System.out.println("getChapterListFromWebsite");
				return getChapterListFromWebsite();
			} else {
				System.out.println("getChapterListFromSerializedObject");
				return getChapterListFromSerializedObject();
			}

		} catch (FileNotFoundException e) {
			return getChapterListFromWebsite();
		} catch (IOException e) {
			return getChapterListFromWebsite();
		} catch (ParseException e) {
			return getChapterListFromWebsite();

		} catch (ClassNotFoundException e) {
			return getChapterListFromWebsite();
		}
	}

}
