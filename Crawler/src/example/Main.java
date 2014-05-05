package example;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Main {

	static String url = "http://roskilde-festival.dk/dk/musik/bands/";

	private static ArrayList<String> bandNameList = new ArrayList<String>();
	private static ArrayList<String> bandDescriptionList = new ArrayList<String>();
	private static ArrayList<String> sceneList = new ArrayList<String>();
	private static ArrayList<String> newsList = new ArrayList<String>();

	public static void main(String[] args) throws IOException {

		//GET BANDS NAMES
		try {
			Document bandDoc = Jsoup.connect("http://roskilde-festival.dk/dk/musik/bands/bands_alfabetisk/").userAgent("Chrome").timeout(30000).get();

			org.jsoup.select.Elements bands = bandDoc.select(".listdata");

			for(Element e : bands){
				bandNameList.add(e.getElementsByTag("a").first().text());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//GET BANDS DESCRIPTION
		try {
			Document bandDoc = Jsoup.connect("http://roskilde-festival.dk/dk/musik/bands/bands_alfabetisk/").userAgent("Chrome").timeout(30000).get();

			org.jsoup.select.Elements bands = bandDoc.select(".listteaser");

			for(Element e : bands){
				bandDescriptionList.add(e.getElementsByTag("div").first().text());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//GET SCENE		
		try {
			Document sceneDoc = Jsoup.connect("http://roskilde-festival.dk/dk/musik/scener/").userAgent("Chrome").timeout(30000).get();

			org.jsoup.select.Elements scenes = sceneDoc.select("#c5736");

			String temp = null;

			for(Element e : scenes){
				temp = e.getElementsByTag("a").text(); 
			}			

			String[] strList = temp.split("\\s");

			for (String string : strList) {
				sceneList.add(string);
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//GET NEW HEADER
		try {
			Document newsDoc = Jsoup.connect("http://roskilde-festival.dk/dk/nyhed/").userAgent("Chrome").timeout(30000).get();

			org.jsoup.select.Elements news = newsDoc.select(".container");					

			//System.out.println(news);

			for(Element e : news){
				newsList.add(e.getElementsByTag("a").first().attr("title"));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		//GET NEW TEXT
//		try {
//			Document newsDoc = Jsoup.connect("http://roskilde-festival.dk/dk/nyhed/singlenews/the-rolling-stones-indtager-roskilde-festival-2014/").userAgent("Chrome").timeout(30000).get();
//
//			//Header
//			org.jsoup.select.Elements newsHeader = newsDoc.select(".createSingleView_teaser");					
//
//			for(Element e : newsHeader){
//				System.out.println(e.getElementsByTag("div").first().text());
//			}
//
//			//Text
//			org.jsoup.select.Elements newsText = newsDoc.select(".createSingleView_description");					
//
//			for(Element e : newsText){
//				System.out.println(e.getElementsByTag("p").text());
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
		// PRINT TO CONSOLE
		BandNames();
		BandDescriptions();
		Scenes();
		News();
	}

	private static void BandNames(){
		System.out.println("");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("BAND NAVNE");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("");

		for (int i = 0; i < bandNameList.size(); i++) {

			System.out.println(bandNameList.get(i).toString());
		}
	}

	private static void BandDescriptions(){
		System.out.println("");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("BAND BESKRIVELSER");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("");
		
		for (int i = 0; i < bandDescriptionList.size(); i++) {

			System.out.println(bandDescriptionList.get(i).toString());
		}
	}

	private static void Scenes(){
		System.out.println("");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("FESTIVAL SCENER");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("");
		
		for (int i = 0; i < sceneList.size(); i++) {

			System.out.println(sceneList.get(i).toString());
		}

	}

	private static void News(){
		System.out.println("");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("FESTIVAL NYHEDER OVERSKRIFTER");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("");
		
		for (int i = 0; i < newsList.size(); i++) {

			System.out.println(newsList.get(i).toString());
		}
	}

	private void SaveToDB(){


	}
}
