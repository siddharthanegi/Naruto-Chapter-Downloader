package com.home.naruto;

import java.io.IOException;

public class tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Downloader down=new Downloader();
		try {
			down.getChapterList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
