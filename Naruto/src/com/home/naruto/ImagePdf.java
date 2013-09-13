package com.home.naruto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class ImagePdf {
	
	private List<Image> images;
	 
	ImagePdf(){
		images=new ArrayList<Image>();
	}
	
	public void convertToPdf(List<String> imageNames,String fileName)
	{
		System.out.println("\n***********convertToPdf***************");
		setupImages(imageNames);
		Document document=new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(fileName+".pdf"));
			Rectangle imgDimensions;
			document.open();
			
			for(Image image : images){
			imgDimensions=new Rectangle(image.getWidth(),image.getHeight());
			System.out.println(imgDimensions);
					
			document.setPageSize(imgDimensions);
			document.setMargins(0,0,0,0);
			document.newPage();
			
			document.add(image);
			}
			document.close();
			
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
		
	}
	public void convertToPdf(String fileName)
	{
		System.out.println("\n***********convertToPdfDirect***************");
		convert(fileName);
		
	}

	public void setupImages(List<String> imageNames){
		for(String name : imageNames ){
			try {
				images.add(Image.getInstance(name));
			} catch (BadElementException e) {
				
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("File Not Found !!");
				e.printStackTrace();
			}
		}
		
	}
	
	private void convert(String fileName){
		Document document=new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(fileName+".pdf"));
			Rectangle imgDimensions;
			document.open();
			
			for(Image image : images){
			imgDimensions=new Rectangle(image.getWidth(),image.getHeight());
			System.out.println(imgDimensions);
					
			document.setPageSize(imgDimensions);
			document.setMargins(0,0,0,0);
			document.newPage();
			
			document.add(image);
			}
			document.close();
			
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
	}
	//Test method
	public void tp(URL url){
		
		try {
			images.add(Image.getInstance(url));
			convertToPdf(new ArrayList<String>(), "timepass");
			
			
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}

}
