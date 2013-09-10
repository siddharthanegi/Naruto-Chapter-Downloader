package com.home.naruto;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author negi_s
 */
public class NewGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private Downloader downloader;
	private String selectedChapter;
	private String latestChapter;

	public NewGui() {
		downloader = new Downloader();
		selectedChapter = "Naruto 1";
		initUI();
	}

	private void initUI() {

		JPanel pane = new JPanel(new GridBagLayout());
		final JFileChooser directoryChooser = new JFileChooser();
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		add(pane);
		GridBagConstraints chapterCons = new GridBagConstraints();
		chapterCons.gridx = 0;
		chapterCons.gridy = 0;
		chapterCons.insets = new Insets(0, 5, 0, 0);
		JLabel chapterLabel = new JLabel("Chapter : ");
		pane.add(chapterLabel, chapterCons);

		Vector<String> chapterList = null;

		try {
			chapterList = downloader.getChapterList();
			latestChapter = chapterList.lastElement();
		} catch (IOException e) {

			System.out.println("Could not get chapter list !");
			e.printStackTrace();
		}
		JComboBox chapterListComboBox = new JComboBox(chapterList);
		chapterListComboBox.addActionListener(new ComboBoxListener());
		GridBagConstraints chapterListCons = new GridBagConstraints();
		chapterListCons.fill = GridBagConstraints.HORIZONTAL;
		chapterListCons.weightx = 0.5;
		chapterListCons.gridx = 1;
		chapterListCons.gridwidth = 2;
		chapterListCons.gridy = 0;
		pane.add(chapterListComboBox, chapterListCons);

		JButton downloadBtn = new JButton("Download");
		downloadBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				buttonAction(directoryChooser, selectedChapter);

			}

		});
		GridBagConstraints downloadBtnCons = new GridBagConstraints();
		downloadBtnCons.insets = new Insets(0, 5, 0, 5);
		downloadBtnCons.gridx = 3;
		downloadBtnCons.gridy = 0;
		downloadBtnCons.gridwidth = 1;

		pane.add(downloadBtn, downloadBtnCons);

		JLabel copyright = new JLabel("© Siddhartha Negi");
		GridBagConstraints copyCons = new GridBagConstraints();
		copyCons.fill = GridBagConstraints.HORIZONTAL;
		copyCons.insets = new Insets(5, 5, 0, 0);
		copyCons.gridx = 0;
		copyCons.gridy = 2;
		copyCons.gridwidth = 3;
		pane.add(copyright, copyCons);

		JButton latestChapterBtn = new JButton("Latest Chapter "
				+ latestChapter);
		latestChapterBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				buttonAction(directoryChooser, latestChapter);
			}
		});
		GridBagConstraints latestCons = new GridBagConstraints();
		latestCons.insets = new Insets(5, 5, 0, 5);
		latestCons.gridx = 0;
		latestCons.gridy = 1;
		latestCons.gridwidth = 4;
		latestCons.fill = GridBagConstraints.HORIZONTAL;
		pane.add(latestChapterBtn, latestCons);

		setTitle("Naruto Chapter Downloader");
		setSize(new Dimension(300, 125));
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(getToolkit()
				.getImage(getClass().getResource("218987.png")));

	}

	protected void buttonAction(JFileChooser directoryChooser, String chapter) {

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		int returnVal = directoryChooser.showSaveDialog(NewGui.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			String location = directoryChooser.getSelectedFile()
					.getAbsolutePath();
			downloader.setChapterLocation(location.replace('\\', '/'));
			downloader.downloadChapter(chapter);
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			JOptionPane.showMessageDialog(null, "Download Complete !");

		}
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

	}

	class ComboBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			JComboBox cb = (JComboBox) e.getSource();
			selectedChapter = (String) cb.getSelectedItem();
			System.out.println(selectedChapter);
		}

	}

	public static void main(String[] args) throws IOException {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				NewGui ex = new NewGui();

				ex.setVisible(true);
			}
		});
	}

}
