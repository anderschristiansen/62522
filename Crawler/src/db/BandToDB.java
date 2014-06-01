package db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import object.Band;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class BandToDB {

	private static final String APPLICATION_ID = "1PZGZUbJ7AyTkIZTOwMfXxdFRpkbJwo0MoB4J6im";
	private static final String REST_API_KEY = "zRjZVs2vpNkuS7EjO31PXaxq5Jgrqf6VPPq0az8J";
	private static URL url = null;
	private static URLConnection conn = null;

	public static boolean GetBandNames(){

		try {
			url = new URL("https://api.parse.com/1/classes/bands/");
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}

		try {       
			conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("X-Parse-Application-Id", APPLICATION_ID);
			conn.setRequestProperty("X-Parse-REST-API-Key", REST_API_KEY);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) 
				System.out.println(inputLine);
			in.close();
			return true;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}		
	}

	public static boolean SaveBand(Band band){		
		
		try {
			url = new URL("https://api.parse.com/1/classes/bands/");
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}

		try {
			conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("X-Parse-Application-Id", APPLICATION_ID);
			conn.setRequestProperty("X-Parse-REST-API-Key", REST_API_KEY);
			conn.setRequestProperty("Content-type", "application/json");			
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");						
			
			String body = "{\"name\": " + "\"" + band.getName() + "\", " + "\"description\": " + "\"" + band.getDescription() + "\", " + "\"scene\": " + "\"" + band.getScene() + "\", " + "\"date\": " + "\"" + band.getDate() + "\"" + "}";
			
			out.write(body);
			
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String decodedString;
			while ((decodedString = in.readLine()) != null) {
				System.out.println(decodedString + " - " + band.getName() + ", " + band.getDescription() + ", " + band.getScene() + ", " + band.getDate());
			}
			in.close();
			return true;
		} catch (IOException e) {
			System.out.println(e.getMessage() + " - " + band.getName());
			return false;
		}	
	}
}
