package com.home.naruto;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author negi_s
 *
 */
public class DownloaderGUI {

	/**
	 * @param args
	 */
	JFrame jFrame;
	JLabel information;
	JLabel copyright;
	Downloader download;
	ImageThumbnail imagePreview;
	JButton getLatestChapterBtn;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DownloaderGUI ui = new DownloaderGUI();
		ui.go();
	}

	public void go() {
		jFrame = new JFrame("Naruto Chapter Downloader");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getLatestChapterBtn = new JButton("Get Latest Chapter!");
		Font font = new Font("Dialog", 1, 24);
		getLatestChapterBtn.setFont(font);

		String text = "<html><p>This is a simple application which downloads the latest manga<br> " +
				"chapter scans from mangapanda.com and stores them on your local drive.</p><ul>"+
				"<li>Click on 'Get Latest Chapter!' button and wait for the download to finish.<br>"+
				"<li>A new folder, Naruto_Chapter will be created with all the latest manga scanned pages !</li></ul></html>";
		information = new JLabel(text);
		copyright=new JLabel("Created By : Siddhartha Negi (siddhartha.code@gmail.com)");
		getLatestChapterBtn.addActionListener(new ButtonListener());
		// ImageThumbnail img=new ImageThumbnail("images/naruto_s.jpg");
		// imagePreview=img;
		jFrame.getContentPane().add(BorderLayout.CENTER, getLatestChapterBtn);
		jFrame.getContentPane().add(BorderLayout.NORTH,information);
		jFrame.getContentPane().add(BorderLayout.SOUTH,copyright);
		jFrame.setSize(375, 225);
		jFrame.setVisible(true);

	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			download = new Downloader();
			download.download();
			getLatestChapterBtn.setText("Done!");
			information.setText("Finished Downloading!");
			Font font = new Font("Dialog", 1, 24);
			information.setFont(font);
			information.repaint();
			getLatestChapterBtn.repaint();
			// imagePreview.setImage("images/naruto_e.jpg");
			// imagePreview.repaint();

		}

	}
};
