package example;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import object.Band;
import object.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Helpers.Trim;

public class Main {

	private static ArrayList<String> _urlBandList = new ArrayList<String>();
	private static ArrayList<String> _urlNewsList = new ArrayList<String>();
	private static ArrayList<Band> _bandList = new ArrayList<Band>();
	private static ArrayList<News> _newsList = new ArrayList<News>();

	public static void main(String[] args) throws IOException, ParseException {


		//GET URL FOR EVERY BAND
		try {
			Document bandDoc = Jsoup.connect("http://roskilde-festival.dk/dk/band/singleband/").userAgent("Chrome").timeout(30000).get();

			org.jsoup.select.Elements bands = bandDoc.select(".listdata");

			for(Element e : bands){

				Element link = e.select("a").first();
				String relHref = link.attr("href"); 

				_urlBandList.add(relHref);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//GET BANDS INFO FROM URL'S
		if (_urlBandList != null)
		{	
			for (String url : _urlBandList) {

				try {

					//------------------------------------------------------------------------------------------------
					Document bandDoc = Jsoup.connect(url).userAgent("Chrome").timeout(30000).get();

					//BAND NAME					
					String name = "";

					try {
						name = bandDoc.select(".data").first().select("h3").text();
						//name = Helpers.Filter.StringFilter(name);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}										

					//BAND DESCRIPTION					
					String description = "";

					try {
						description = bandDoc.select(".description").first().text();
						//description = Helpers.Filter.StringFilter(description);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}						

					//BAND DATE AND SCENE					
					String date = "";
					String scene = "";

					try {

						String info[] = bandDoc.select(".date").first().text().split("-");

						String strDate = info[0].replaceAll("[^0-9.]", "") + info[1];

						strDate = Trim.trimEnd(strDate);				

						date = strDate;

						scene = info[2];
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}					


					//------------------------------------------------------------------------------------------------

					Band newBand = new Band();

					newBand.setName(name);
					newBand.setDescription(description);
					newBand.setDate(date);			
					newBand.setScene(scene);

					//------------------------------------------------------------------------------------------------

					_bandList.add(newBand);


				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		//GET URL FOR EVERY NEWS
		try {
			Document newsDoc = Jsoup.connect("http://roskilde-festival.dk/dk/nyhed/").userAgent("Chrome").timeout(30000).get();

			org.jsoup.select.Elements news = newsDoc.select(".container");

			for(Element e : news){

				Element link = e.select("a").first();
				String relHref = link.attr("href"); 

				_urlNewsList.add(relHref);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// GET NEWS FROM URL
		if (_urlNewsList != null)
		{	
			for (String url : _urlNewsList) {

				try {

					//------------------------------------------------------------------------------------------------
					Document newsDoc = Jsoup.connect("http://roskilde-festival.dk/" + url).userAgent("Chrome").timeout(30000).get();

					//NEWS TITLE					
					String title = "";

					try {
						title = newsDoc.select(".datacontainer").first().select("h2").text();
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}										

					//NEWS CONTENTS
					String content = "";

					try {
						content = newsDoc.select(".bodytext").text();
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}														


					//------------------------------------------------------------------------------------------------

					News newNews= new News();

					newNews.setTitle(title);
					newNews.setContent(content);

					//------------------------------------------------------------------------------------------------

					_newsList.add(newNews);


				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// PRINT TO CONSOLE
		printNewsUrl();
		printBandUrl();

		SaveToDB();
	}


	private static void printBandUrl(){
		System.out.println("");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("BAND NAVNE");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("");

		for (int i = 0; i < _urlBandList.size(); i++) {

			System.out.println(_urlBandList.get(i).toString());
		}
	}

	private static void printNewsUrl(){
		System.out.println("");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("NYHEDER");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("");

		for (int i = 0; i < _urlNewsList.size(); i++) {

			System.out.println(_urlNewsList.get(i).toString());
		}
	}

	private static void SaveToDB(){

		//db.User.GetUser();
		//db.Band.GetBandNames();

		for (Band band : _bandList) {

			db.BandToDB.SaveBand(band);
		}
		
		for (News news : _newsList) {

			db.NewsToDB.SaveNews(news);
		}
	}	


}
