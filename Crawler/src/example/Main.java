package example;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Main {

	static String url = "http://roskilde-festival.dk/dk/musik/bands/";

	public static void main(String[] args) throws IOException {

		//GET BANDS NAMES
		try {
			System.out.println("-----------------------------------------------------------------");
			System.out.println("BAND NAVNE");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("");

			Document bandDoc = Jsoup.connect("http://roskilde-festival.dk/dk/musik/bands/").userAgent("Chrome").timeout(30000).get();

			org.jsoup.select.Elements bands = bandDoc.select(".listdata");

			for(Element e : bands){
				System.out.println(e.getElementsByTag("a").first().text());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//GET BANDS DESCRIPTION
		try {
			System.out.println("");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("BAND BESKRIVELSER");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("");

			Document bandDoc = Jsoup.connect("http://roskilde-festival.dk/dk/musik/bands/").userAgent("Chrome").timeout(30000).get();

			org.jsoup.select.Elements bands = bandDoc.select(".listteaser");

			for(Element e : bands){
				System.out.println(e.getElementsByTag("div").first().text());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//GET SCENE		
		try {
			System.out.println("");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("FESTIVAL SCENER");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("");

			Document sceneDoc = Jsoup.connect("http://roskilde-festival.dk/dk/musik/scener/").userAgent("Chrome").timeout(30000).get();

			org.jsoup.select.Elements scenes = sceneDoc.select("#c5736");

			String temp = null;

			for(Element e : scenes){
				temp = e.getElementsByTag("a").text(); 
			}			

			String[] strList = temp.split("\\s");

			for (String string : strList) {
				System.out.println(string);
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//GET NEW HEADER
		try {
			System.out.println("");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("FESTIVAL NYHEDER OVERSKRIFTER");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("");

			Document newsDoc = Jsoup.connect("http://roskilde-festival.dk/dk/nyhed/").userAgent("Chrome").timeout(30000).get();

			org.jsoup.select.Elements news = newsDoc.select(".container");					

			//System.out.println(news);

			for(Element e : news){
				System.out.println(e.getElementsByTag("a").first().attr("title"));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//GET NEW TEXT
		try {
			System.out.println("");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("FESTIVAL NYHEDER 1");
			System.out.println("-----------------------------------------------------------------");
			System.out.println("");

			Document newsDoc = Jsoup.connect("http://roskilde-festival.dk/dk/nyhed/singlenews/the-rolling-stones-indtager-roskilde-festival-2014/").userAgent("Chrome").timeout(30000).get();

			//Header
			org.jsoup.select.Elements newsHeader = newsDoc.select(".createSingleView_teaser");					

			for(Element e : newsHeader){
				System.out.println(e.getElementsByTag("div").first().text());
			}

			//Text
			org.jsoup.select.Elements newsText = newsDoc.select(".createSingleView_description");					

			for(Element e : newsText){
				System.out.println(e.getElementsByTag("p").text());
			}



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}