package com.home.naruto;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @author negi_s
 *
 */
public class ImageThumbnail extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3939555437450053421L;
	private Image image;
	
	ImageThumbnail()
	{
	this.image =new ImageIcon("test.jpg").getImage(); 
	}
	ImageThumbnail(String imgSrc)
	{
	this.image =new ImageIcon(imgSrc).getImage(); 
	}
	
	public void setImage(String imgSrc) {
		this.image = new ImageIcon(imgSrc).getImage();
	}
	public void paintComponent(Graphics g){
				
		g.drawImage(image, 1, 1,500,500, this);
	}
	

}
