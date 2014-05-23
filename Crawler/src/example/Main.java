package example;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import object.Band;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Helpers.Trim;

public class Main {

	private static ArrayList<String> _urlList = new ArrayList<String>();
	private static ArrayList<Band> _bandList = new ArrayList<Band>();


	public static void main(String[] args) throws IOException, ParseException {


		//GET URL FOR EVERY BAND
		try {
			Document bandDoc = Jsoup.connect("http://roskilde-festival.dk/dk/band/singleband/").userAgent("Chrome").timeout(30000).get();

			org.jsoup.select.Elements bands = bandDoc.select(".listdata");

			for(Element e : bands){

				Element link = e.select("a").first();
				String relHref = link.attr("href"); 

				_urlList.add(relHref);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//GET BANDS INFO FROM URL'S
		if (_urlList != null)
		{	
			for (String url : _urlList) {

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


		// PRINT TO CONSOLE

		//BandUrl();
		//BandNames();

		SaveToDB();
	}


	private static void BandUrl(){
		System.out.println("");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("BAND NAVNE");
		System.out.println("-----------------------------------------------------------------");
		System.out.println("");

		for (int i = 0; i < _urlList.size(); i++) {

			System.out.println(_urlList.get(i).toString());
		}
	}

	private static void SaveToDB(){

		//db.User.GetUser();

		//db.Band.GetBandNames();

		for (Band band : _bandList) {

			db.BandToDB.SaveBandNames(band);
		}
	}	


}
