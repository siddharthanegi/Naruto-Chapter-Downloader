package com.home.naruto;

import java.net.MalformedURLException;
import java.net.URL;


public class tester {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args)  {
		// TODO Auto-generated method stub

		ImagePdf ch=new ImagePdf();
		try {
			ch.tp(new URL("http://i999.mangapanda.com/naruto/646/naruto-4446381.jpg"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
}
