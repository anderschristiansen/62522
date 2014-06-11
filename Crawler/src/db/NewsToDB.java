package db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import object.News;

public class NewsToDB {

	private static final String APPLICATION_ID = "1PZGZUbJ7AyTkIZTOwMfXxdFRpkbJwo0MoB4J6im";
	private static final String REST_API_KEY = "zRjZVs2vpNkuS7EjO31PXaxq5Jgrqf6VPPq0az8J";
	private static URL url = null;
	private static URLConnection conn = null;


	public static boolean SaveNews(News news){		

		try {
			url = new URL("https://api.parse.com/1/classes/news/");
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

			String temp = news.getContent();
			temp = temp.replace("\"", "\\\"");
			
			String body = "{\"title\": " + "\"" + news.getTitle() + "\", " + "\"content\": " + "\"" + temp + "\"" + "}";

			out.write(body);

			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String decodedString;
			while ((decodedString = in.readLine()) != null) {
				System.out.println(decodedString + " - " + news.getTitle() + ", " + news.getContent());
			}
			in.close();
			return true;
		} catch (IOException e) {
			System.out.println(e.getMessage() + " - " + news.getTitle());
			return false;
		}	
	}
}
