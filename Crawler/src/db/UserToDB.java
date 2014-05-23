package db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.parse.Parse;
import com.parse.ParseObject;

public class UserToDB {
	
	private static final String APPLICATION_ID = "1PZGZUbJ7AyTkIZTOwMfXxdFRpkbJwo0MoB4J6im";
	private static final String REST_API_KEY = "zRjZVs2vpNkuS7EjO31PXaxq5Jgrqf6VPPq0az8J";
	private static URL url = null;
	private static URLConnection conn = null;
	
	public static boolean GetUser(){
		
		try {
			url = new URL("https://api.parse.com/1/users/");
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
	
	
//	public static void SaveBands(ArrayList<String> bandNameList)
//	{
//		
//		var Bands = ParseObject .extend("Bands");
//	
//	
//	
//	
//		Parse.initialize("APPLICATION_ID", "REST_API_KEY");
////		
////		ParseObject bands = new ParseObject("Bands");
////		
////		for (String band : bandNameList) {
////			
////			bands.put("BandName", band);			
////		}		
////		
////		bands.saveInBackground();
//	}
}
