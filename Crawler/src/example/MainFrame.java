package example;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import object.Band;
import object.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Helpers.Trim;

public class MainFrame extends JFrame {

	private static ArrayList<String> _urlBandList = new ArrayList<String>();
	private static ArrayList<String> _urlNewsList = new ArrayList<String>();
	private static ArrayList<Band> _bandList = new ArrayList<Band>();
	private static ArrayList<News> _newsList = new ArrayList<News>();


	public MainFrame(String title){
		super(title);

		// Display result
		final JTextArea textArea = new JTextArea();
		JScrollPane scroll = new JScrollPane (textArea, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);	

		// Buttons
		JButton button1 = new JButton("Hent bands");
		JButton button2 = new JButton("Hent nyheder");

		// Button size
		button1.setPreferredSize(new Dimension(50, 50));
		button2.setPreferredSize(new Dimension(50, 50));

		// Font of the button
		button1.setFont(new Font("Courier", Font.BOLD, 20));
		button2.setFont(new Font("Courier", Font.BOLD, 20));

		// Textarea panel
		JPanel txtPanel = new JPanel();
		txtPanel.setLayout(new GridLayout(1, 1));
		txtPanel.add(scroll);

		// Button panel
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(1, 2));
		btnPanel.add(button1);
		btnPanel.add(button2);

		// Add Swing components to content panel
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		pane.add(txtPanel, BorderLayout.CENTER);
		pane.add(btnPanel,  BorderLayout.SOUTH);

		// Get Bands
		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				
				_urlBandList = new ArrayList<String>();
				_bandList = new ArrayList<Band>();

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

								System.out.println("Der findes ikke spilletid eller scene for: " + name);
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

				// Saves the bands			
				for (Band band : _bandList) {

					db.BandToDB.SaveBand(band);
					textArea.append("Band er gemt til Parse.com: " + band.getName() + "\n");
				}

			}
		});

		// Get News
		button2.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent event) {
				
				_urlNewsList = new ArrayList<String>();
				_newsList = new ArrayList<News>();

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

				//Saves every news
				for (News news : _newsList) {

					db.NewsToDB.SaveNews(news);
					textArea.append("Nyhed er gemt til Parse.com: " + news.getTitle() + "\n");
				}
			}
		});
	}
}
