package com.home.naruto;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;


public class tester {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub

		ImagePdf ch=new ImagePdf();
		List<Image> input=new ArrayList<Image>();
		try {
			input.add(Image.getInstance("src/images/naruto_e.jpg"));
			input.add(Image.getInstance("src/images/naruto_s.jpg"));
			ch.setImages(input);
		//	ch.convertToPdf();
		} catch (BadElementException e) {
			
			e.printStackTrace();
		} catch (MalformedURLException e) {
		
			e.printStackTrace();
		} catch (IOException e) {
			
			System.out.println("File not found !");
			e.printStackTrace();
		}
				
	}

}
