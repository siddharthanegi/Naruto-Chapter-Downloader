package com.home.naruto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private static final String SERIALIZED_HASHMAP = "ChapterList.dat";
	private Map<Integer, String> chapterMap;

	public Vector<String> getChapterListFromWebsite() throws IOException {

		Document doc = Jsoup.connect(NARUTO_PAGE).timeout(15 * 1000).get();
		Element chapterListDiv = doc.getElementById("listing");
		Elements urls = chapterListDiv.select("a[href]");
		chapterMap = new TreeMap<Integer, String>();
		Vector<String> vector = new Vector<String>();
		int i=1;
				
		for (Element e : urls) {
			
			chapterMap.put(i, e.attr("href"));
			i++;
			vector.add("Naruto "+i);

		}
		//System.out.println(chapterMap);
		//serializeHashMap();
		Downloader.setChapterMap(chapterMap);
		return vector;

	}

	@SuppressWarnings("unchecked")
	public Vector<String> getChapterListFromSerializedObject()
			throws IOException, ClassNotFoundException {
		Vector<String> vector=new Vector<String>();

		try {
			FileInputStream fis;
			fis = new FileInputStream(SERIALIZED_HASHMAP);
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object o=ois.readObject();
			if (o instanceof TreeMap) {
				chapterMap = (TreeMap<Integer, String>) o;
				ArrayList<Integer> temp=new ArrayList<Integer>(chapterMap.keySet());
				int index=0,lastElement=temp.size();
				for (index=0;index<lastElement;index++){
					vector.add(index, "Naruto "+temp.get(index));
				}
				Downloader.setChapterMap(chapterMap);
				System.out.println(chapterMap);
				System.out.println(vector);
				ois.close();
				fis.close();
				return vector;
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}

		return null;

	}
	
	public void serializeHashMap() throws IOException{
		
		 FileOutputStream fos = new FileOutputStream(SERIALIZED_HASHMAP);
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    oos.writeObject(chapterMap);
		    System.out.println("TADDAAAAA!!");
		    oos.close();
		    fos.close();
	}
	
	public void updateLog(){
				
		try {
			File updateLog=new File("Update_Log.dat");
			FileWriter writer=new FileWriter(updateLog);
			Calendar currentDate=Calendar.getInstance();
			writer.write(currentDate.get(Calendar.DAY_OF_MONTH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
