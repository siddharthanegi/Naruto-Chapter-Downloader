package com.home.naruto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DirectoryLocation {

	private static final String PREFS = "HelperFiles_DND/Prefs.dat";

	public void setLocation(String location) {
		try {
			System.out.println("Creating Preferences !");
			FileWriter fw = new FileWriter(new File(PREFS));
			fw.write(location);
			fw.close();
		} catch (IOException e) {

			System.out.println("Failed to update prefs !");
			e.printStackTrace();
		}

	}

	public String getLocation() {
		try {
			System.out.println("Get Location !");
			BufferedReader fr = new BufferedReader(new FileReader(new File(PREFS)));
			String location = fr.readLine();
			fr.close();
			return location;

		} catch (FileNotFoundException e) {

			System.out.println("First StartUp !!");
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}

	}
}
