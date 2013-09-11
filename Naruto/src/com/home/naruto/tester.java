package com.home.naruto;

import java.io.IOException;

public class tester {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub

		ChapterList ch=new ChapterList();
		try {
			ch.getChapterListFromWebsite();
			ch.serializeHashMap();
			ch.getChapterListFromSerializedObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
