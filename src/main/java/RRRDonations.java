import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
//imports for google directions http request
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject; // added additional dependency in pom file

import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.python.modules._jythonlib.set_builder;
import org.python.util.PythonInterpreter;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.*;


public class RRRDonations extends Application{
	
	// Note: Not all data is accurate
	// This data will be helpful when creating the matching algorithm
	String nonShelterName1 =  "A Safe Haven";
	String nonShelterPerson1 = "Jane Doe";
	String nonShelterEmail1 = "safeHaven@gmail.com";
	String nonShelterPhone1 = "773-435-8374";
	String nonShelterSlogan1 = "Ending Homelessness, One Person at a Time!";
	String nonShelterCity1 = "Chicago";
	String nonShelterState1 = "Illinois";
	//Volunteering Needs - Unnecessary, Necessary, Critical
	String nonShelterVolLevel1 = "Unnecessary";
	Integer nonShelter1ID = 100001; // 6 digits for non profit ID numbers
	
	
	//Below 3 non profits are the current donor options to choose from in the survey
	// Hard coded First Aid Non-profit
	String nonAidName1 = "American Red Cross of Greater Chicago";
	String nonAidPerson1 = "John Doe";
	String nonAidEmail1 = "volunteerillinois@redcross.org";
	String nonAidPhone1 = "312-729-6111";
	String nonAidSlogan1 = "With Humanity Towards Peace";
	String nonAidCity1 = "Chicago";
	String nonAidState1 = "Illinois";
	String nonAidVolLevel1 = "Necessary";
	Integer nonAid1ID = 200001; // first number can indicates category of non-profit
	
	// Hard coded Clothing Non-profit
	String nonClothingName1 = "Goodwill Store and Donation Center";
	String nonClothingPerson1 = "Charlie Smith";
	String nonClothingEmail1 = "goodwill@Goodwill.com";
	String nonClothingPhone1 = "3125631187";
	String nonClothingSlogan1 = "Uncommonly Good";
	String nonClothingCity1 = "Chicago";
	String nonClothingState1 = "Illinois";
	String nonClothingVolLevel1 = "Critical";
	Integer nonClothing1ID = 300001;
	
	// Hard Coded Food Non-Profit
	String nonFoodName1 = "Greater Chicago Food Depository";
	String nonFoodPerson1 = "John Appleseed";
	String nonFoodEmail1 = "gcfd@FoodDepository.com";
	String nonFoodPhone1 = "7732473663";
	String nonFoodSlogan1 = "Our Mission Is To End Hunger";
	String nonFoodCity1 = "Chicago";
	String nonFoodState1 = "Illinois";
	String nonFoodVolLevel1 = "Critical";
	Integer nonFood1ID = 400001;

	private static final String JDBC_URL = "jdbc:h2:~/test;MODE=MySQL";
   	private static final String USER = "sa";
   	private static final String PASSWORD = "";

	/* enter google static maps api key here */
	private static final String googleAPIKey = "";
   	
	Integer navigationUser = 0; // this means that the navigation is coming from beneficiary
	
	VBox homeBox;
	Scene startScene;
	Scene scene;
	//Scene donorStart;
	
	//Accessor Survey UI elements
	CheckBox foods;
	CheckBox firstAid;
	CheckBox clothing;
	String beneCity = "";
	String beneState = "";
	TextField benSurveyCity;
	TextField benSurveyState;
	Boolean benFood = false;
	Boolean benClothes = false;
	Boolean benAid = false;
	
	//DATAMANAGER
	DataManager dataManager = new DataManager();
	
	//DONOR ELEMENTS
	HBox donorWelcomeBox;
	VBox beneWelcomeBox;
	BorderPane startPane;
	BorderPane volunteerPane;
	Button DonorButton;
	Button submitDonorSurvey;
	Button volunteerTime;
	Button donateItems;
	Button submitVolunteerForm;
	//TabPane donorTabs;
	Button donorNavigate;
	HBox donorTopButtons;
	TextField Donation;
	ChoiceBox<String> categories;
	// temporary storage for donation items because
	// Maps don't allow for duplicates
	Map<String, String> donationMap = new HashMap<>();
	ListView<String> listview; 

	//BENEFICIARY ELEMENETS
	HBox beneTopButtons;
	Button beneItems;
	Button beneSettings;
	Button beneNavigate;
	Button beneLogout;
	Button beneUpdate;
	

	CheckBox donorfood;
	CheckBox donorfirstAid;
	CheckBox donorclothing;
	CheckBox donortime;
	
	
	TextField donorNameFirst;
	TextField donorNameLast;
	TextField donorEmail;
	TextField donorPhoneNumber;
	
	TextField donorCity;
	TextField donorState;
	String donorRecs = "";
	

	TextField beneNameFirst;
	TextField beneNameLast;
	TextField beneEmail;
	TextField benePhoneNumber;
	TextField loginfield;
	String fullName = "";
	String donorSurveyCity = "";
	String donorSurveyState = "";
	
	
	Text welcomeMessage;
	Text welcomeMessage2;
	String chosenLocations;
	
	Button donorSettings;
	Button donorLogout;
	
	
	Boolean donorFood = false;
	Boolean donorAid = false;
	Boolean donorClothing = false;
	Boolean donorShelter = true; //for testing purposes this will be true for the time being
	Boolean donorTime = false;
	
	Integer donorID;
	Integer beneID;
	Integer runRecs = 0;
	Integer runBenRecs = 0;
	String benrecs = "";
	String BenRecs;
	String recs = ""; // Hold the recommendations
	
	
	
	// Pre-created donor
	String dFirstName = "Alexa";
	String dLastName = "Amazon";
	Integer dAge = 22;
	Integer dIDNum = 501234;
	String dEmail = "jane@Email.com";
	String dPhoneNum = "3120001234";
	// NOTE: Set the donor boolean values to be able to test the matching algorithm
	static Image imgMap;
	TextField availability = new TextField();
	TextField userAddress;
	
	private EventHandler<ActionEvent> donorSwitch;
	
	private EventHandler<ActionEvent> handleFood;
	private EventHandler<ActionEvent> handleAid;
	private EventHandler<ActionEvent> handleDonorName;
	private EventHandler<ActionEvent> handleClothing;
	
	private EventHandler<ActionEvent> handleBenFood;
	private EventHandler<ActionEvent> handleBenAid;
	private EventHandler<ActionEvent> handleBenClothes;
	
	private EventHandler<ActionEvent> handleTime;
	private EventHandler<ActionEvent> benefitSwitch;
	private EventHandler<ActionEvent> handleBenificiaryName;
	private EventHandler<ActionEvent> handleVolunteering;
	private EventHandler<ActionEvent> handleUpdateSurvey;
	private EventHandler<ActionEvent> handleBeneLogOut;
	private EventHandler<ActionEvent> handleDonorLogOut;
	private EventHandler<ActionEvent> handleVolunteerForm;
	private EventHandler<ActionEvent> handleDonating;
	private EventHandler<ActionEvent> handleSubmit;
	private EventHandler<ActionEvent> handleBack;
	private EventHandler<ActionEvent> handleNav;


	static String finalCity;
	static String finalState;
	static String destAddress;
	static String startAddress;
	static String totalTravelTime;
	
	
//	ObservableList<String> list ;
	
	
	// (*) main()
	// The main function of the program allows us to launch the program
	public static void main(String[] args) throws Exception{
				
		DatabaseUtil.initializeDatabase();
		//DatabaseUtil.Insert_all_nonprofits();
		// DatabaseUtil.Insert_from_api(filteredList);
		// DatabaseUtil.PrintAllNonprofits();
		//DatabaseUtil.GetOneNonprofitaddress("Global Help");
		// googleDirectionsAPICall();
		//PrintAllNonprofits
		//DataDonor donor = DatabaseUtil.GetOneDonor("111111");
		//System.out.println(donor.street);
		//DataBeneficiary person1 = DatabaseUtil.GetOneBeneficiary("222222");
		//System.out.println(person1.street);
		
		launch(args);
	}
	
	// (*) stringCleanForGoogle()
	// This function will add the required special characters into the variable so that the google API can process the element in the request
	public String stringCleanForGoogle(String element) {
		String cleanedString = element.replaceAll(" ", "+");
		System.out.println("This is the address which will go into the api call" + cleanedString);
		return cleanedString;
		
	}
	
	// (*) cleanRouteInstr()
	// Function that cleans junk characters from API response
	public static String cleanRouteInstr(String routes) {
		String rmHeader = routes.replaceAll("html_instructions\" : \"", " ");
		System.out.println(rmHeader);
		String rmJunk1 = rmHeader.replaceAll("u003cb", " ");
		String rmJunk2 = rmJunk1.replaceAll("u003e", " ");
		String rmJunk3 = rmJunk2.replaceAll("u003c/b?", " ");
		String rmJunk4 = rmJunk3.replace("\\ ", "");
		String rmJunk5 = rmJunk4.replace("\"", "");
		String rmJunk6 = rmJunk5.replace("div", "");
		String rmJunk7 = rmJunk6.replace("\\u003c", "");
		String rmJunk8 = rmJunk7.replace("style=\\font-size:0.9em\\", "");
		String rmJunk9 = rmJunk8.replace("wbr", "");
		String rmJunk10 = rmJunk9.replace("/", " ");
		
		//System.out.println("Cleaned: " + rmJunk10);
		return rmJunk10;
	}
	
	
	// (*) googleStaticMapAPICall()
	// This function will make a call to the google maps static API to get a map view of the route
	public static void googleStaticMapAPICall() throws IOException{
		String googleStaticURL = "https://maps.googleapis.com/maps/api/staticmap?center="+ startAddress + finalCity+ "," +finalState
				+"&format=jpg&zoom=15&size=500x500&markers=color:purple%7Clabel:D%7C"+
				destAddress+ "&markers=color:red%7Clabel:S%7C" + startAddress +"," +finalCity + "," + finalState + "&key=";
		// Path code: +"&path=color:green|weight:5"+startAddress+"," +finalCity+ ","+finalState + "|" + destAddress
		// Add markers to the origin and destination
		// Origin variables = finalCity + finalState
		// Destination variables come from databaseUtil.java get address function

		String finalMapString = googleStaticURL + googleAPIKey;
		try {
			//URL googleMapsURL = new URL(finalMapString);
			//HttpURLConnection googleMapsCon = (HttpURLConnection)googleMapsURL.openConnection();
			//googleMapsCon.setRequestMethod("GET");
//			System.out.println("Response Code:"+ googleMapsCon.getResponseCode());
//			System.out.println("Response Message:"+ googleMapsCon.getResponseMessage());
//			System.out.println(googleMapsCon.getContent());
//			
//			InputStream inputStream = (InputStream)googleMapsCon.getContent();
//			BufferedImage imBuff = Imag3eIO.read(inputStream);
//			String filePath = "/Users/sunilarumilli/Desktop/maps.jpg";
//			File file = new File(filePath);
//			ImageIO.write(imBuff, "jpg", file);
			
	        File img = new File("resources/img/image.jpg");
			String destFileName = "image.jpg";
			URL staticMapURL = new URL(finalMapString);
           InputStream mapInStream = staticMapURL.openStream();
           OutputStream mapOutStream = new FileOutputStream(img);

           byte[] bytes = new byte[2048];
           int len;
           while ((len = mapInStream.read(bytes)) != -1) {
           	mapOutStream.write(bytes, 0, len);
           }
           
           mapInStream.close();
           mapOutStream.close();
           imgMap = new Image("image.jpg");
			
		}
		catch (Exception mapError) {
			System.out.println(mapError.getMessage());
		}
	}
	
	
	// (*) googleDirectionsAPICall()
	// Function makes call to the google directions api to get route information
	public static String googleDirectionsAPICall() throws IOException{

		    // code for making an API call to google directions api
				String google_host = "maps.googleapis.com";
				String googleapi_key = "AIzaSyDGWDZoGm8WjvNAIdO2v5r1Sg9YXlETdOo";
				String gFullUrl = "https://maps.googleapis.com/maps/api/directions/json?origin=Disneyland&destination=Universal+Studios+Hollywood&key=";
				
				// Build URL based on users inputs
				String finalURL = "https://maps.googleapis.com/maps/api/directions/json?departure_time=now&origin="+ startAddress + finalCity + "+" + finalState + "&destination="+ destAddress + "&key=";;
				String fullURL = gFullUrl + googleapi_key;
				String finalFull = finalURL + googleapi_key;
				String route = "";
				String travelTime = "";
				try {
					//URL googleURL = new URL(fullURL);
					URL googleURL = new URL(finalFull); // The URL with users inputs injected in the origin parameter
					
					HttpURLConnection googleCon = (HttpURLConnection)googleURL.openConnection();
					googleCon.setRequestMethod("GET");
					
					//JSONObject jsonObj = new JSONObject(googleCon.getContent());
					
					System.out.println("Response Code:"+ googleCon.getResponseCode());
					System.out.println("Response Message:"+ googleCon.getResponseMessage());
					System.out.println("The Response: " + googleCon.getContent()); // should print out the JSON response
					
					BufferedReader buff = null;
					buff = new BufferedReader(new InputStreamReader(googleCon.getInputStream()));
					StringBuilder strBuild = new StringBuilder();
					String responseLine;
					while ((responseLine = buff.readLine()) != null) {
						strBuild.append(responseLine+"\n");
					}
					buff.close();
					System.out.println("THIS IS THE RESPONSE " + strBuild.toString());
					//googleCon.setRequestProperty("outputFormat", "json");
					//googleCon.setRequestProperty("key", googleapi_key);
					
					int startidx = 0;
					int startdur = strBuild.toString().indexOf("duration");
					// Extract first Duration Metric from API Response
					for (int dur = 0; dur < 1; dur++) {
						int durIdx = strBuild.toString().indexOf("text", startdur);
						if (durIdx != -1) {
							int endingidx = strBuild.toString().indexOf("\n", durIdx);
							travelTime += (strBuild.toString().substring(durIdx+9, endingidx-2) + "\n");
						}
					}
					totalTravelTime = travelTime;
					//Extracts first 20 HTML route instructions from API response
					for (int i = 0; i < 20; i++) {
						//print one instruction - response tag html_instruction
						int index1 = strBuild.toString().indexOf("html_instructions", startidx);
						if (index1 != -1) {
							//System.out.println("Index of first route instruction: " + index1);
							int endingidx = strBuild.toString().indexOf("\n", index1);
							//System.out.println(strBuild.toString().substring(index1, endingidx));
							startidx = endingidx++;
							route+= (strBuild.toString().substring(index1, endingidx) + "\n");
						}
						else {
							break;
						}
					}
					
				}
				catch (Exception e) {
					System.out.println("CONNECTION ERROR MESSAGE");
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
		  //route = "\"html_instructions\" : \"Head \\u003cb\\u003enortheast\\u003c/b\\u003e toward \\u003cb\\u003eDisneyland Dr\\u003c/b\\u003e\",";
		  //route = " Turn right onto Coral Dr\\u003cdiv style=\\font-size:0.9em\\Destination will be on the leftdiv,";
		  //route = " Keep left at the fork to continue on US-101 N, follow signs for Los Angeles N/wbr/Civic Ctr,\n" + "Turn right at the fork and take the ramp\n";
		  String routeInfo = cleanRouteInstr(route);
		  System.out.println("Duration Time: "+ travelTime);
		  System.out.println("Clean Starting Address " + startAddress);
		  System.out.println("START ADDRESS: "+ startAddress);
		  System.out.println("route: "+ routeInfo);
		  System.out.println("Current code will produce Response Code: 200 and Response Message: OK");
		  return routeInfo;
		}
	
	
	// (*) start()
	// Starting method of program
	@Override
	public void start(Stage primaryStage) throws Exception {
		//BorderPane startPane = new BorderPane();
		startScene = makeHomeScene(primaryStage);

		String cssPath = new File("src/main/java/survey.css").toURI().toString();
		startScene.getStylesheets().add(cssPath);
		// TODO Auto-generated method stub
		primaryStage.setTitle("RRR");
		primaryStage.setScene(startScene);
		primaryStage.show();

	}
	
	
	// (*) viewMapPage
	// This function shows the static map from the google API call to the user in a separate window
	public Scene viewMapPage(Stage primaryStage) {
		VBox mapBox = new VBox(30);
		mapBox.setPadding(new Insets(50));
		Text googleAcknowledgment = new Text("Map Provided By Google");
		googleAcknowledgment.getStyleClass().add("title");
		Image profileImage = new Image(new File("resources/img/image.jpg").toURI().toString()); // Adjust with your image path
        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setFitWidth(600);
        profileImageView.setFitHeight(600);
		Text travelTimeInfo = new Text("Estimated Travel Time: " + totalTravelTime);
		travelTimeInfo.getStyleClass().add("text");
		ImageView mapView = new ImageView();
		mapView.setImage(imgMap);
		Button backToNavPage = new Button("Back to Navigation");
		mapBox.getChildren().addAll(googleAcknowledgment, profileImageView, travelTimeInfo, backToNavPage);
		mapBox.setAlignment(Pos.BASELINE_CENTER);
		
		
		backToNavPage.setOnAction(e -> {
			primaryStage.setScene(NavigationPage(primaryStage));
			primaryStage.show();
		});
		
		mapBox.setStyle("-fx-background-color: #D5DCE0;");
		Scene viewScene = new Scene(mapBox, 850, 900);
		String cssPath = new File("src/main/java/survey.css").toURI().toString();
		viewScene.getStylesheets().add(cssPath);
		return viewScene;
	}
	
	
	// (*) NavigationPage()
	// A page dedicated to generating route to desired destination and showing other forms of navigation
	public Scene NavigationPage(Stage primaryStage) {
		VBox navBox = new VBox(30);
		Button backButton = new Button("Back");
		backButton.getStyleClass().add("button");
		Button getRoute = new Button("Get Route");
		getRoute.getStyleClass().add("button");
		TextField routeInfo = new TextField();
		routeInfo.getStyleClass().add("text");
		TextArea routeInfo1 = new TextArea();
		routeInfo1.getStyleClass().add("text");
		Text curRouteInfo = new Text("Your Current Route Information");		
		curRouteInfo.getStyleClass().add("text");

		String[]nonprofitArray = DatabaseUtil.GetAllNonprofits();
        ObservableList<String>nonprofitList = FXCollections.observableArrayList(nonprofitArray);

		Text destination = new Text("Which nonprofit are you looking for:");		
		destination.getStyleClass().add("text");
        ComboBox<String> nonProfitSelect = new ComboBox<String>(nonprofitList);
		nonProfitSelect.getStyleClass().add("text");

		Text visitCountText = new Text();
		visitCountText.getStyleClass().add("text");

        nonProfitSelect.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String selectedOption = nonProfitSelect.getValue();
				String nonprofitName = selectedOption.split(" \\(Visits: ")[0];
				DatabaseUtil.GetOneNonprofit(selectedOption);
				destAddress = DatabaseUtil.GetOneNonprofitaddress(selectedOption);
				System.out.println("THIS IS THE ADDRESS: " + destAddress);
				// primaryStage.setScene(makeNonprofitScene(primaryStage, selectedOption));
				// primaryStage.show();

				Nonprofit selectedNonprofit = DatabaseUtil.GetOneNonprofit(selectedOption);
           		DatabaseUtil.recordVisit("nonprofit",selectedNonprofit.id);

            	
            	int updatedVisitCount = DatabaseUtil.getVisitCount("nonprofit",selectedNonprofit.id);
            	visitCountText.setText("Visits: " + updatedVisitCount);
            }
        });
		
		Button routeMapButton = new Button("View Route Map"); // Enable this once you can get a static map from google maps API
		routeMapButton.getStyleClass().add("button");
		backButton.setOnAction(e -> {
			System.out.println("BOOLEAN VALUE: " + navigationUser);
			if (navigationUser == 1) {
				
				primaryStage.setScene(donorWelcome(primaryStage, fullName, donorTime, donorFood, donorAid, donorClothing, donorSurveyCity, donorSurveyState));
				navigationUser = 0; // set back to a beneficiary
			}
			else if (navigationUser == 2) {
				primaryStage.setScene(makeHomeScene(primaryStage));
				navigationUser = 0;
			}
			else {
				primaryStage.setScene(benificiaryWelcome(primaryStage, fullName));
			}
			
			primaryStage.show();
			
		});
		
		routeMapButton.setOnAction(e -> {
			primaryStage.setScene(viewMapPage(primaryStage));
			primaryStage.show();
			
		});
		
		getRoute.setOnAction(e -> {
			// Now we run the google directions API call function to get the route information.
			try {
				// This will tell us if the API call is being made properly
				String googleRoute = googleDirectionsAPICall();
				googleStaticMapAPICall();
				System.out.println("GOOGLE STRING: "+ googleRoute.isEmpty());
				routeInfo.setText(googleRoute);
				routeInfo1.setText(googleRoute);
			}
			catch (Exception googleError) {
				System.out.println("GOOGLE ERROR MESSAGE");
				System.out.println(googleError.getMessage());
			}
			
		});
		BorderPane navigationPane = new BorderPane();
		VBox topNavigation = new VBox(20);
		HBox getRouteAndMap = new HBox(400);
		//getRouteAndMap.getChildren().addAll(getRoute, routeMapButton);
		topNavigation.getChildren().addAll(destination, nonProfitSelect, visitCountText, getRoute, routeMapButton, curRouteInfo, routeInfo1);
		topNavigation.setAlignment(Pos.TOP_CENTER);
		routeInfo1.setPrefHeight(400);
		routeInfo1.setPrefWidth(800);
		//navBox.getChildren().addAll(curRouteInfo, routeInfo1);
		//navBox.setAlignment(Pos.BASELINE_CENTER);
		navigationPane.setTop(topNavigation);
		//navigationPane.getChildren().add(navBox);
		navigationPane.setCenter(navBox);
		//navigationPane.setLeft(routeInfo1);
		
		navigationPane.setBottom(backButton);
		navigationPane.setStyle("-fx-background-color: #D5DCE0;");
		Scene donorNavScene = new Scene(navigationPane, 850, 900);
		String cssPath = new File("src/main/java/survey.css").toURI().toString();
		donorNavScene.getStylesheets().add(cssPath);
		return donorNavScene;
	}
	
	
	// (*) matchBenficiary()
	// Function to match beneficiary using the city and state entered from the survey
	public String matchBeneficiary() {
		// count number of check boxes selected
		Integer benCheckCount = 0;
		Integer benNumRecs = 0;
		Integer benNoMatch = 0;
		Boolean bFood = false;
		Boolean bAid = false;
		Boolean bClothes = false;
		
		if (benFood) {
			benCheckCount++;
		}
		if (benClothes) {
			benCheckCount++;
		}
		if (benAid) {
			benCheckCount++;
		}
		//System.out.println("Ben Checks: " + benCheckCount);
		
		// If non are selected give them non profits based on where they are located
		if (benCheckCount == 0) {
			if (beneCity.equals(nonAidCity1) && beneState.equals(nonAidState1) && bAid == false) {
				//output aid option
				benrecs += ("\n\t" + nonAidName1);
				bAid = true;
			}
			else {
				benrecs += ("\n\tNo Aid Match Found");
				benNoMatch++;
			}
			if (beneCity.equals(nonFoodCity1) && beneState.equals(nonFoodState1) && bFood == false) {
				benrecs += ("\n\t" + nonFoodName1);
				bFood = true;
			}
			else {
				benrecs += ("\n\tNo Food Match Found");
				benNoMatch++;
			}
			if (beneCity.equals(nonClothingCity1) && beneState.equals(nonClothingState1) && bClothes == false) {
				benrecs += ("\n\t" + nonClothingName1);
				bClothes = true;
			}
			else {
				benrecs += ("\n\tNo Clothing Match Found");
				benNoMatch++;
			}
			if (benNoMatch == 3) {
				benrecs = "";
				benrecs += "\n\tThere are no available matches for your location";
			}
		}
		
		// If one is selected give them non profit based on where they are located
		if (benCheckCount == 1) {
			// Identify which box was selected
			if (benAid) {
				if (beneCity.equals(nonAidCity1) && beneState.equals(nonAidState1)) {
					benrecs += ("\n\tPersonalized Aid Match: " + nonAidName1); 
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tPersonalized Match: No Aid Match Found");
					benNoMatch++;
				}
				if (beneCity.equals(nonFoodCity1) && beneState.equals(nonFoodState1)) {
					benrecs += ("\n\t" + nonFoodName1);
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tNo Food Match Found");
					benNoMatch++;
				}
				if (beneCity.equals(nonClothingCity1) && beneState.equals(nonClothingState1)) {
					benrecs += ("\n\t" + nonClothingName1);
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tNo Clothing Match Found");
					benNoMatch++;
				}
				if (benNoMatch == 3) {
					benrecs = "";
					benrecs += "\n\tThere are no available matches for your location";
				}
			}
			if (benFood) {
				if (beneCity.equals(nonFoodCity1) && beneState.equals(nonFoodState1)) {
					benrecs += ("\n\tPersonalized Food Match: " + nonFoodName1);
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tPersonalized Match: No Food Match Found");
					benNoMatch++;
				}
				if (beneCity.equals(nonAidCity1) && beneState.equals(nonAidState1)) {
					benrecs += ("\n\t" + nonAidName1); 
					
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tNo Aid Match Found");
					benNoMatch++;
				}
				if (beneCity.equals(nonClothingCity1) && beneState.equals(nonClothingState1)) {
					benrecs += ("\n\t" + nonClothingName1);
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tNo Clothing Match Found");
					benNoMatch++;
				}
				if (benNoMatch == 3) {
					benrecs = "";
					benrecs += "\n\tThere are no available matches for your location";
				}
			}
			if (benClothes) {
				if (beneCity.equals(nonClothingCity1) && beneState.equals(nonClothingState1)) {
					benrecs += ("\n\tPersonalized Clothing Match: " + nonClothingName1);
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tPersonalized Match: No Clothing Match Found");
					benNoMatch++;
				}
				if (beneCity.equals(nonAidCity1) && beneState.equals(nonAidState1)) {
					benrecs += ("\n\t" + nonAidName1); 
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tNo Aid Match Found");
					benNoMatch++;
				}
				if (beneCity.equals(nonFoodCity1) && beneState.equals(nonFoodState1)) {
					benrecs += ("\n\t" + nonFoodName1);
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tNo Food Match Found");
					benNoMatch++;
				}
				if (benNoMatch == 3) {
					benrecs = "";
					benrecs += "\n\tThere are no available matches for your location";
				}
			}
		}
		
		// If 2 are selected given non profits based on location fill remaining spots with generated recommendations
		if (benCheckCount == 2) {
			if (benAid && benFood) {
				if (beneCity.equals(nonAidCity1) && beneState.equals(nonAidState1)) {
					benrecs += ("\n\tPersonalized Aid Match: " + nonAidName1); 
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tPersonalized Match: No Aid Match Found");
					benNoMatch++;
				}
				if (beneCity.equals(nonFoodCity1) && beneState.equals(nonFoodState1)) {
					benrecs += ("\n\tPersonalized Food Match: " + nonFoodName1);
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tPersonalized Match: No Food Match Found");
					benNoMatch++;
				}
				if (beneCity.equals(nonClothingCity1) && beneState.equals(nonClothingState1)) {
					benrecs += ("\n\t" + nonClothingName1);
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tNo Clothing Match Found");
					benNoMatch++;
				}
				if (benNoMatch == 3) {
					benrecs = "";
					benrecs += "\n\tThere are no available matches for your location";
				}	
			}
			else if (benAid && benClothes) {
				if (beneCity.equals(nonAidCity1) && beneState.equals(nonAidState1)) {
					benrecs += ("\n\tPersonalized Aid Match: " + nonAidName1); 
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tPersonalized Match: No Aid Match Found");
					benNoMatch++;
				}
				if (beneCity.equals(nonClothingCity1) && beneState.equals(nonClothingState1)) {
					benrecs += ("\n\tPersonalized Clothing Match: " + nonClothingName1);
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tPersonalized Match: No Clothing Match Found");
					benNoMatch++;
				}
				if (beneCity.equals(nonFoodCity1) && beneState.equals(nonFoodState1)) {
					benrecs += ("\n\t" + nonFoodName1);
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tNo Food Match Found");
					benNoMatch++;
				}
				if (benNoMatch == 3) {
					benrecs = "";
					benrecs += "\n\tThere are no available matches for your location";
				}
			}
			else if (benFood && benClothes) {
				if (beneCity.equals(nonFoodCity1) && beneState.equals(nonFoodState1)) {
					benrecs += ("\n\tPersonalized Food Match: " + nonFoodName1);
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tPersonalized Match: No Food Match Found");
					benNoMatch++;
				}
				if (beneCity.equals(nonClothingCity1) && beneState.equals(nonClothingState1)) {
					benrecs += ("\n\tPersonalized Clothing Match: " + nonClothingName1);
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tPersonalized Match: No Clothing Match Found");
					benNoMatch++;
				}
				if (beneCity.equals(nonAidCity1) && beneState.equals(nonAidState1)) {
					benrecs += ("\n\t" + nonAidName1); 
					bAid = true;
					benNumRecs++;
				}
				else {
					benrecs += ("\n\tNo Aid Match Found");
					benNoMatch++;
				}
				if (benNoMatch == 3) {
					benrecs = "";
					benrecs += "\n\tThere are no available matches for your location";
				}
			}
		}
		
		// if 3 are selected no other generated recommendations
		if (benCheckCount == 3) {
			if (beneCity.equals(nonAidCity1) && beneState.equals(nonAidState1) && bAid == false) {
				//output aid option
				benrecs += ("\n\tPersonalized Aid Match: " + nonAidName1);
				bAid = true;
			}
			else {
				benrecs += ("\n\tPersonalized Match: No Aid Match Found");
				benNoMatch++;
			}
			if (beneCity.equals(nonFoodCity1) && beneState.equals(nonFoodState1) && bFood == false) {
				benrecs += ("\n\tPersonalized Food Match: " + nonFoodName1);
				bFood = true;
			}
			else {
				benrecs += ("\n\tPersonalized Match: No Food Match Found");
				benNoMatch++;
			}
			if (beneCity.equals(nonClothingCity1) && beneState.equals(nonClothingState1) && bClothes == false) {
				benrecs += ("\n\tPersonalized Match: " + nonClothingName1);
				bClothes = true;
			}
			else {
				benrecs += ("\n\tPersonalized Clothing Match: No Clothing Match Found");
				benNoMatch++;
			}
			if (benNoMatch == 3) {
				benrecs = "";
				benrecs += "\n\tThere are no available matches for your location";
			}
		}
		return benrecs;
	}

	
	// (*) BenificiaryWelcome()
	// The main home screen for beneficiaries where they can view their non-profit matches
	public Scene benificiaryWelcome (Stage primaryStage, String fullName){
		
		// handles update survey button but its done by reusing benefitStartScene page
		handleUpdateSurvey = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// Re run the matching algorithm
				runBenRecs = 0;
				benrecs = "";
				primaryStage.setScene(benefitStartScene(primaryStage));
				primaryStage.show();
			}
		};

		handleBeneLogOut = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				beneCity = "";
				beneState = "";
				benFood = false;
				benClothes = false;
				benAid = false;   
				benrecs = ""; // beneficiary Recommendations string
				primaryStage.setScene(makeHomeScene(primaryStage));
				primaryStage.show();
				
			}
		};

		handleNav = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				try {
//				String googleRoute = googleDirectionsAPICall();
//				System.out.println("GOOGLE STRING: "+ googleRoute.isEmpty());
				navigationUser = 0;
				// Change screen to navigate
				primaryStage.setScene(NavigationPage(primaryStage));
				primaryStage.show();
				
				}
				catch (Exception errors) {
					System.out.println(errors.getMessage());
				}
			}
		};
		
		// Random randNum = new Random();
		
		BorderPane beneWelcomePane = new BorderPane();
		beneWelcomePane.setStyle("-fx-background-color: #00000000;");
	
		// name text
		// beneID = randNum.nextInt(99999);
		welcomeMessage2 = new Text("Welcome, Benificiary " + fullName + "\nYour BenificiaryID: " + beneID);
		welcomeMessage2.getStyleClass().add("text");

		DatabaseUtil.recordVisit("Benificiary", beneID);

		// Retrieve the visit count for the donor
		int visitCount = DatabaseUtil.getVisitCount("Benificiary", beneID);
	
		// Create a text to display the visit count
		Text visitCountText = new Text("Benificiary " + beneID + ", you have visited this page " + visitCount + " times.");
		visitCountText.getStyleClass().add("text");
	
		// navbar
		beneTopButtons = new HBox();
		beneItems = new Button("Wishlist");
		beneItems.setOnAction(e -> primaryStage.setScene(createWishlistPage(primaryStage)));
		beneUpdate  = new Button("Update Survey");
		beneSettings = new Button ("Settings");
		beneNavigate = new Button("Navigation");
		beneLogout = new Button("Log Out");
		beneTopButtons.getChildren().addAll(beneItems, beneUpdate, beneNavigate, beneLogout);
		beneTopButtons.setAlignment(Pos.CENTER);
		beneUpdate.setOnAction(handleUpdateSurvey);
		beneLogout.setOnAction(handleBeneLogOut);
		beneNavigate.setOnAction(handleNav);

		// Profile Image
		Image profileImage = new Image(new File("resources/img/user-icon.png").toURI().toString()); // Adjust with your image path
        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setFitWidth(200);
        profileImageView.setFitHeight(200);


        // Create a circular clipping for the profile image
        Circle clip = new Circle(100, 100, 100);
        profileImageView.setClip(clip);

		// container for image and name
		VBox profileBox = new VBox(0, profileImageView, welcomeMessage2,visitCountText);
        profileBox.setPadding(new Insets(20));
		
		Text nonprofitText = new Text("Your Non Matches:");
		nonprofitText.getStyleClass().add("text");
		
//		Rectangle rectangle = new Rectangle(300, 100);
//		Rectangle rectangle2 = new Rectangle(300, 100);
//		Rectangle rectangle3 = new Rectangle(300, 100);
//        rectangle.setFill(Color.LIGHTGREEN);
//        rectangle2.setFill(Color.LIGHTBLUE);
		//rectangle3.setFill(Color.LIGHTPINK);
		
		Text BenRecMessage = new Text("Here are your top 3 Nonprofit Matches"); // Add a place to display top 3 Matched donation locations
		BenRecMessage.getStyleClass().add("text");
		Text benrecsField = new Text();
		
		// Makes sure recommendations are only run once
		if (runBenRecs == 0) {
			BenRecs = matchBeneficiary(); // return as a string
			runBenRecs = 1;
		}
		benrecsField.setText(BenRecs);
		benrecsField.getStyleClass().add("text");
		System.out.println(BenRecs);

		VBox nonprofitDisplayBox = new VBox(nonprofitText, benrecsField);

		//VBox temp = new VBox(nonprofitText, rectangle, rectangle2, rectangle3);
		HBox profileTopLevel = new HBox(profileBox);
		HBox benButtons = new HBox(beneTopButtons);
		profileTopLevel.setSpacing(375);

		nonprofitDisplayBox.setSpacing(10);
		nonprofitDisplayBox.setAlignment(Pos.CENTER);
		//BorderPane.setAlignment(, Pos.BOTTOM_LEFT);
	
		// image
		profileBox.setAlignment(Pos.TOP_LEFT);
		benButtons.setAlignment(Pos.TOP_RIGHT);
		//beneTopButtons.setAlignment(Pos.TOP_RIGHT); 
		// beneWelcomePane.setBottom(profileBox);
		//BorderPane.setAlignment(nonprofitDisplayBox, Pos.CENTER);
		VBox finalBenBox = new VBox();
		finalBenBox.getChildren().addAll(benButtons, profileTopLevel, nonprofitDisplayBox);
		beneWelcomePane.setCenter(finalBenBox);
		//beneWelcomePane.getChildren().addAll(benButtons, profileTopLevel, nonprofitDisplayBox);
//		beneWelcomePane.setCenter(nonprofitDisplayBox);
//		beneWelcomePane.setTop(profileTopLevel);
		//beneWelcomePane.setBottom(benButtons);
		
		beneWelcomePane.setStyle("-fx-background-color: #D5DCE0;");
		Scene blankScreen = new Scene(beneWelcomePane,850, 900);
		String cssPath = new File("src/main/java/survey.css").toURI().toString();
		blankScreen.getStylesheets().add(cssPath);
		return blankScreen;
	}

	
	// (*) BenefitStartScene()
	// Survey page for beneficiaries to enter personal information + what areas of service they are looking for
	public Scene benefitStartScene(Stage primaryStage) {
		
		handleBenFood = new EventHandler<ActionEvent> () {
			public void handle(ActionEvent e) {
				if (foods.isSelected()) { benFood = true; }
				else { benFood = false; }
			}
		};
		

		handleBenAid= new EventHandler<ActionEvent> () {
			public void handle(ActionEvent e) {
				if (firstAid.isSelected()) {
					benAid = true;
				}
				else {
					benAid = false;
				}
			}
		};
		

		handleBenClothes= new EventHandler<ActionEvent> () {
			public void handle(ActionEvent e) {
				if (clothing.isSelected()) {
					benClothes = true;
				}
				else {
					benClothes = false;
				}
			}
		};
				
		handleBenificiaryName = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String loginid = loginfield.getText();
				// System.out.println(loginid);
				// System.out.println(!loginid.isEmpty());
				if(!loginid.isEmpty()){
					DataBeneficiary person = DatabaseUtil.GetOneBeneficiary(loginid);
					beneID = Integer.parseInt(loginid);
					fullName = person.firstName + " " + person.lastName;
					beneCity = person.city;
					beneState = person.state;
					startAddress = stringCleanForGoogle(person.street);
					finalState = beneState;
					finalCity = beneCity;
					benAid = person.firstAid;
					benClothes = person.clothing;
					benFood = person.food;
					System.out.println(fullName);
					
					dataManager.addBeneficiary(new Beneficiary(
						person.firstName,
						person.lastName,
						person.email, 
						person.phoneNumber,
						person.food,
						person.firstAid, 
						person.clothing
					));
					primaryStage.setScene(benificiaryWelcome(primaryStage, fullName));
					primaryStage.show();

				} else {
					Random randNum = new Random();
					beneID = randNum.nextInt(99999);
					fullName = beneNameFirst.getText() + " " + beneNameLast.getText();
					
					//Update location information 
					beneCity = benSurveyCity.getText();
					beneState = benSurveyState.getText();
					//Static variables needed to make the google API url
					startAddress = stringCleanForGoogle(userAddress.getText()); // Adds the '+' sign where there are spaces
					finalState = beneState;
					finalCity = beneCity;
					//System.out.println(fullName + beneCity + beneState);
					System.out.println(fullName);
	//				if (log in with ID then ) {
	//					manual
	//				}
					dataManager.addBeneficiary(new Beneficiary(
						beneNameFirst.getText(), 
						beneNameLast.getText(), 
						beneEmail.getText(), 
						benePhoneNumber.getText(), 
						benFood, 
						benAid, 
						benClothes
					));
					primaryStage.setScene(benificiaryWelcome(primaryStage, fullName));
					primaryStage.show();
				}
				
			}
		};

		
		Text benefitText = new Text("Welcome beneficiary please put in your info below");
		benefitText.getStyleClass().add("text");

		Text login = new Text("Login Id:");
		login.getStyleClass().add("text");
		loginfield = new TextField();
		loginfield.setPromptText("enter login id");
		loginfield.setText("222222");

		Text firstName = new Text("First name:");
		firstName.getStyleClass().add("text");
		beneNameFirst = new TextField();
		beneNameFirst.setPromptText("Enter your first name, ex. Charles");
		
		Text lastName = new Text("Last name:");
		lastName.getStyleClass().add("text");
		beneNameLast = new TextField();
		beneNameLast.setPromptText("Enter your last name, ex. Leclerc");
		
		Text email = new Text("Email:");
		email.getStyleClass().add("text");
		beneEmail = new TextField();
		beneEmail.setPromptText("Enter your email address, ex. charles16@gmail.com");
		
		Text phone = new Text("Phone Number:");
		phone.getStyleClass().add("text");
		benePhoneNumber = new TextField();
		benePhoneNumber.setPromptText("Enter your phone number, ex. 000-000-0000");
		
		Text streetAddress = new Text("Street Address:");
		streetAddress.getStyleClass().add("text");
		userAddress = new TextField();
		userAddress.setPromptText("Please enter your street address, ex. 750 S Halsted St");
		
		
		Text bSurveyCity = new Text("City:");
		bSurveyCity.getStyleClass().add("text");
		benSurveyCity = new TextField();
		benSurveyCity.setPromptText("Please enter the city in which you reside in, ex. Chicago");
		
		
		Text bSurveyState = new Text("State:");
		bSurveyState.getStyleClass().add("text");
		benSurveyState = new TextField();
		benSurveyState.setPromptText("Please enter the state in which you reside in, ex. Illinois");
		
		
		Text categories = new Text("Please select to which categories you need assisstance with");
		categories.getStyleClass().add("text");
		foods = new CheckBox("Food");
		firstAid = new CheckBox("First Aid Materials");
		clothing = new CheckBox("Clothing");
		Button submitBenificiarySurvey = new Button("Submit Form");
		
		foods.setOnAction(handleBenFood);
		firstAid.setOnAction(handleBenAid);
		clothing.setOnAction(handleBenClothes);
		
		submitBenificiarySurvey.setOnAction(handleBenificiaryName);
		
		VBox benefitStartBox = new VBox(10,
			login, loginfield, firstName, beneNameFirst, lastName, beneNameLast,
            email, beneEmail, phone, benePhoneNumber, streetAddress, userAddress, 
            bSurveyCity, benSurveyCity, bSurveyState, benSurveyState,
            categories, foods, firstAid, clothing);

		BorderPane benefitPane = new BorderPane();
		benefitPane.setTop(benefitText);
		benefitPane.setCenter(benefitStartBox);
		benefitPane.setBottom(submitBenificiarySurvey);

		// code below aligns all items to the center of the screen
		BorderPane.setAlignment(benefitText, Pos.TOP_CENTER); 
		benefitStartBox.setAlignment(Pos.CENTER); 
		BorderPane.setAlignment(submitBenificiarySurvey, Pos.BOTTOM_CENTER);
		benefitPane.setStyle("-fx-background-color: #D5DCE0;");
		Scene benefitStart = new Scene(benefitPane, 850, 900);
		String cssPath = new File("src/main/java/survey.css").toURI().toString();
		benefitStart.getStylesheets().add(cssPath);
		
		return benefitStart;
	}
	
	
	// (*) noMatches()
	// A method made to re-factor - to reduce redundant code
	public String noMatches(String donorRecStr) {
		donorRecStr = "";
		donorRecStr = "\tNo Matches: No Nonprofits Near You";
		return donorRecStr;
	}
	
	
	// (*) MatchingDonors()
	// Matching Algorithm - take the preferences and give them top 3 matches based on their location
	public String matchingDonors(String cityDonor, String stateDonor, Boolean donorTimeCheck, Boolean foodDonor, Boolean aidDonor, Boolean clothingDonor) {
		// Matching Algorithm Variables
		Integer countChecks = 0;
		Integer numRecs = 0;
		Integer noMatch = 0;
		Boolean food = false;
		Boolean aid = false;
		Boolean clothes = false;
		
		//count number of selected boxes - 3 current options: food, aid, clothing
		if (foodDonor) countChecks++;
		if (aidDonor) countChecks++;
		if (clothingDonor) countChecks++;
		
		
		//Logic
		//If no boxes are selected and only volunteering is selected pick most critical locations in their location
		if (countChecks == 0) {
			// Give them 3 selections based on critical or necessary volunteer and location
				if (cityDonor.equals(nonAidCity1) && aid == false) {
					donorRecs += ("\n\t" + nonAidName1);
					aid = true;
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Aid Matches Found");
					noMatch++;
				}
				if (cityDonor.equals(nonFoodCity1) && food == false) {
					donorRecs += ("\n\t" + nonFoodName1);
					food = true;
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Food Matches Found");
					noMatch++;
				}
				if (cityDonor.equals(nonClothingCity1) && clothes == false) {
					donorRecs += ("\n\t" + nonClothingName1);
					clothes = true;
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Clothing Matches Found");
					noMatch++;
				}
				if (noMatch == 3) {
					numRecs += 3;
					donorRecs = noMatches(donorRecs);
				}
		} // End of all system recommended
		
		//if one selected, Identify which one then populate based on selected, and must all be in same location
		if (countChecks == 1) {
			if (aidDonor == true) {
				if (cityDonor.equals(nonAidCity1)) {
					donorRecs += ("\n\tPersonalized Match: " + nonAidName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tPersonalized Aid Matches: None Found Near You");
					noMatch++;
				}
				if (cityDonor.equals(nonFoodCity1)) {
					donorRecs += ("\n\t" + nonFoodName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Food Matches Found");
					noMatch++;
				}
				if (cityDonor.equals(nonClothingCity1)) {
					donorRecs += ("\n\t" + nonClothingName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Clothing Matches Found");
					noMatch++;
				}
				if (noMatch == 3) {
					numRecs += 3;
					donorRecs = noMatches(donorRecs);
				}
			}
			else if (foodDonor == true) {
				if (cityDonor.equals(nonFoodCity1)) {
					donorRecs += ("\n\tPersonalized Match: " + nonFoodName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tPersonalized Food Matches: None Found Near You");
					noMatch++;
				}
				if (cityDonor.equals(nonAidCity1)) {
					donorRecs += ("\n\t" + nonAidName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Aid Matches Found");
					noMatch++;
				}
				if (cityDonor.equals(nonClothingCity1)) {
					donorRecs += ("\n\t" + nonClothingName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Clothing Matches Found");
					noMatch++;
				}
				if (noMatch == 3) {
					numRecs += 3;
					donorRecs = noMatches(donorRecs);
				}
			}
			else {
				if (cityDonor.equals(nonClothingCity1)) {
					donorRecs += ("\n\tPersonalized Match: " + nonClothingName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tPersonalized Clothing Matches: None Found Near You");
					noMatch++;
				}
				if (cityDonor.equals(nonAidCity1)) {
					donorRecs += ("\n\t" + nonAidName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Aid Matches Found");
					noMatch++;
				}
				if (cityDonor.equals(nonFoodCity1)) {
					donorRecs += ("\n\t" + nonFoodName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Food Matches Found");
					noMatch++;
				}
				if (noMatch == 3) {
					numRecs += 3;
					donorRecs = noMatches(donorRecs);
				}
			}
		}
		
		
		// if 2 selected then top 2 is matched location other based on critical and necessary status
		if (countChecks == 2) {
			//6 variations 3 choose 2 = 3
			if (foodDonor == true && aidDonor == true) {
				if (cityDonor.equals(nonAidCity1)) {
					donorRecs += ("\n\tPersonalized Aid Match: " + nonAidName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tPersonalized Aid Matches: None Found Near You");
					noMatch++;
				}
				if (cityDonor.equals(nonFoodCity1)) {
					donorRecs += ("\n\tPersonalized Food Match: " + nonFoodName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Food Matches Found");
					noMatch++;
				}
				if (cityDonor.equals(nonClothingCity1)) {
					donorRecs += ("\n\t" + nonClothingName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Clothing Matches Found");
					noMatch++;
				}
				if (noMatch == 3) {
					numRecs += 3;
					donorRecs = noMatches(donorRecs);
				}
				
				
			}
			else if (foodDonor == true && clothingDonor == true) {
				if (cityDonor.equals(nonFoodCity1)) {
					donorRecs += ("\n\tPersonalized Food Match: " + nonFoodName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Food Matches Found");
					noMatch++;
				}
				if (cityDonor.equals(nonClothingCity1)) {
					donorRecs += ("\n\tPersonalized Clothing Match: " + nonClothingName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Clothing Matches Found");
					noMatch++;
				}
				if (cityDonor.equals(nonAidCity1)) {
					donorRecs += ("\n\t" + nonAidName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Aid Matches Found");
					noMatch++;
				}
				if (noMatch == 3) {
					numRecs += 3;
					donorRecs = noMatches(donorRecs);
				}
			}
			else if (aidDonor == true && clothingDonor == true) {
				
				if (cityDonor.equals(nonAidCity1)) {
					donorRecs += ("\n\tPersonalized Aid Match: " + nonAidName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tPersonalized Aid Matches: None Found Near You");
					noMatch++;
				}
				if (cityDonor.equals(nonClothingCity1)) {
					donorRecs += ("\n\tPersonalized Clothing Match: " + nonClothingName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Clothing Matches Found");
					noMatch++;
				}
				if (cityDonor.equals(nonFoodCity1)) {
					donorRecs += ("\n\t" + nonFoodName1);
					numRecs++;
				}
				else {
					donorRecs += ("\n\tNo Food Matches Found");
					noMatch++;
				}
				if (noMatch == 3) {
					numRecs += 3;
					donorRecs = noMatches(donorRecs);
				}	
			}
			
		}// End of 2 check boxes selected
		
		
		// if 3 are selected then match based on three no other critical and necessary status
		if (countChecks == 3) {
			if (cityDonor.equals(nonAidCity1) && aid == false) {
				donorRecs += ("\n\tPersonalized Aid Match: " + nonAidName1);
				aid = true;
				numRecs++;
			}
			else {
				donorRecs += ("\n\tNo Aid Matches Found");
				noMatch++;
			}
			if (cityDonor.equals(nonFoodCity1) && food == false) {
				donorRecs += ("\n\tPersonalized Food Match: " + nonFoodName1);
				food = true;
				numRecs++;
			}
			else {
				donorRecs += ("\n\tNo Food Matches Found");
				noMatch++;
			}
			if (cityDonor.equals(nonClothingCity1) && clothes == false) {
				donorRecs += ("\n\tPersonalized Clothing Match: " + nonClothingName1);
				clothes = true;
				numRecs++;
			}
			else {
				donorRecs += ("\n\tNo Clothing Matches Found");
				noMatch++;
			}
			if (noMatch == 3) {
				numRecs += 3;
				donorRecs = noMatches(donorRecs);
			}
		}
		
		
		return donorRecs;
		
	}
	
	
	// (*) donorVolunteeringPage()
	// This page will allow the donor to sign up to volunteer
	public Scene donorVolunteeringPage (Stage primaryStage, Boolean donorTimeCheck, Boolean donorFood, Boolean donorAid, Boolean donorClothing, String donorCityLocation, String donorStateLocation ) {
		ComboBox<String> selectVolOption = new ComboBox<String>(); // used in the donor volunteer form page
		handleVolunteerForm = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				chosenLocations = "";
				//reset the ComboBox
				selectVolOption.setItems(null);
				primaryStage.setScene(
					donorWelcome(
						primaryStage, 
						fullName,  
						donorTimeCheck,  
						donorFood,  
						donorAid,  
						donorClothing, 
						donorCityLocation, 
						donorStateLocation
				));
				String emailMessage = fullName + " would like to volunteer\nTheir phone number is " + dPhoneNum + " and their email is " + dEmail + "\nthey are available " + availability.getText();
				new Thread(() -> {
					try {
						EmailUtil.sendEmail("donationsrrr@gmail.com", emailMessage);
					} catch (MessagingException e1) {
						e1.printStackTrace();
					}
				}).start();
				
				primaryStage.show();
			}
		};
		
		volunteerPane = new BorderPane();
		
		// First Part Recommended Volunteering Locations based on initial survey
		VBox donorTimeOfDay = new VBox();
		Text whatTimeOfDay = new Text("What time of day would you like to volunteer");
		whatTimeOfDay.getStyleClass().add("text");
		Text volunteerDisclaimer = new Text("Please type in your availability and we'll email the non-profit with your contact information");
		volunteerDisclaimer.getStyleClass().add("text");
		donorTimeOfDay.getChildren().addAll(whatTimeOfDay, volunteerDisclaimer, availability);
		donorTimeOfDay.setAlignment(Pos.TOP_CENTER);
		volunteerPane.setTop(donorTimeOfDay);
		
		// Now we will show the donor the first two options in their matched algorithm
		VBox personalizedLocations = new VBox();
		Text locBasedOnChoice = new Text("Here are a few options based on your initial survey\nSelect on the drop down the one you wish to submit the form for");
		locBasedOnChoice.getStyleClass().add("text");
		chosenLocations = "";
		
		// ****** There needs to be a way to select the recommended volunteering options
		//use the donorRecs string to grab the different donation options
		
		//String optionsVol [];
		String aidVolOp = "";
		String shelterVolOp = "";
		System.out.println("In Volunteer page " + recs);
		
		String [] recArray = recs.split("\n\t");
		for (int i = 0; i < recArray.length; i++) {
			System.out.println("RecArray: " + recArray[i]);
			chosenLocations += ("\n\t" + recArray[i]);
			selectVolOption.getItems().add(recArray[i]);
		}

		// Shelter match will always show up if the city is the same 
		if (donorShelter == true && donorCityLocation.equals(nonShelterCity1)) {
			shelterVolOp = (nonShelterName1 +"\n\tID: " + nonShelter1ID + "\n\t email: " + nonShelterEmail1 + "\n");
			chosenLocations += ("\n\t"+nonShelterName1 +"\n\tID: " + nonShelter1ID + "\n\t email: " + nonShelterEmail1 + "\n");
		}
		selectVolOption.getItems().addAll(shelterVolOp);
		
		Text locations = new Text(chosenLocations);
		locations.getStyleClass().add("text");
		
		submitVolunteerForm = new Button("Submit to Volunteer");
		personalizedLocations.getChildren().addAll(locBasedOnChoice, locations, selectVolOption, submitVolunteerForm);
		personalizedLocations.setAlignment(Pos.CENTER);
		volunteerPane.setCenter(personalizedLocations);
		submitVolunteerForm.setOnAction(handleVolunteerForm);
		volunteerPane.setStyle("-fx-background-color: #D5DCE0;");
		Scene donorVolunteerScreen = new Scene(volunteerPane, 850, 900);
		String cssPath = new File("src/main/java/survey.css").toURI().toString();
		donorVolunteerScreen.getStylesheets().add(cssPath);
		return donorVolunteerScreen;
	}
	
	
	// (*) donoDonationPage()
	// Page for donors to enter what products they have to give to non profits
	public Scene donoDonationPage(Stage primaryStage) {

		handleSubmit = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String textValue = Donation.getText();
            	String choiceValue = categories.getValue();
				if(textValue != null && !textValue.isEmpty() && choiceValue != null) {
					String inpuString = textValue + " - " + choiceValue;
					listview.getItems().add(inpuString);
					Donation.clear();
					categories.getSelectionModel().clearSelection();
					donationMap.put(textValue, choiceValue);
					System.out.println("Input values saved to map: " + donationMap);
				} else {
					System.out.println("Please enter values in both text field and choice box.");
				}
			}
		};

		handleBack = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Donation.clear();
				listview.getItems().clear();
				primaryStage.setScene(donorWelcome(primaryStage, fullName, donorTime, donorFood, donorAid, donorClothing, donorSurveyCity, donorSurveyState));
				primaryStage.show();
			}
		};

		Text pleaseDonate = new Text("Add what you can donate one item at a time and select a category the item belongs too");
		pleaseDonate.getStyleClass().add("text");

		Donation = new TextField();
		Donation.setPromptText("Enter one item at a time");

		categories = new ChoiceBox<>(FXCollections.observableArrayList("Clothes", "Food", "First-Aid", "Other"));
		Button submit = new Button("Submit");
		Button back = new Button("Back");

		HBox inputBox = new HBox(10);
		inputBox.getChildren().addAll(categories, Donation, submit);

		listview = new ListView<>();
		VBox donationBox = new VBox(10);
		BorderPane donorWishListPane = new BorderPane();
		donorWishListPane.setTop(pleaseDonate);
		BorderPane.setAlignment(pleaseDonate, Pos.TOP_CENTER);
		donationBox.getChildren().addAll(inputBox, listview, back);
		donorWishListPane.setCenter(donationBox);
		
		donationBox.setPadding(new Insets(80));
		submit.setOnAction(handleSubmit);
		back.setOnAction(handleBack);
		
		donationBox.setStyle("-fx-background-color: #D5DCE0;");
		donorWishListPane.setStyle("-fx-background-color: #D5DCE0;");
		Scene donorDonationScreen = new Scene(donorWishListPane, 850, 900);
		String cssPath = new File("src/main/java/survey.css").toURI().toString();
		donorDonationScreen.getStylesheets().add(cssPath);
		return donorDonationScreen;
	}
	
	
	// (*) donorWelcome()
	// Donors home page where they can view their non profit matches and perform other donor related activities
	public Scene donorWelcome (Stage primaryStage, String fullNames, Boolean donorTimeCheck1, Boolean donorFood1, Boolean donorAid1, Boolean donorClothing1, String donorCityLoc, String donorStateLoc) {
		
		// This handler is for when the donor clicks on the volunteering button on their profile page
		handleVolunteering = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				primaryStage.setScene(donorVolunteeringPage(primaryStage, donorTimeCheck1, 
					donorFood1, donorAid1, donorClothing1, donorCityLoc, donorStateLoc));
				primaryStage.show();
			}
		};

		handleDonating = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				primaryStage.setScene(donoDonationPage(primaryStage));
				primaryStage.show();

				if (listview != null) {
					listview.getItems().clear();
            		for (Map.Entry<String, String> entry : donationMap.entrySet()) {
               			String inputString = entry.getKey() + " - " + entry.getValue();
                		listview.getItems().add(inputString);
            		}	
				}
			}
		};

		handleDonorLogOut = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				primaryStage.setScene(makeHomeScene(primaryStage));
				primaryStage.show();
				donorTime = false;
				donorFood = false;
				donorAid = false;
				donorClothing = false;
				donorRecs = "";
				fullName = "";
				donorSurveyCity = "";
				donorSurveyState = "";
				runRecs = 0;
				dFirstName = "";
				dLastName = "";
				dEmail = "";
				dPhoneNum = "";
				donationMap.clear();
			}
		};
		
		handleNav = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				navigationUser = 1;
				// Change screen to navigate
				primaryStage.setScene(NavigationPage(primaryStage));
				primaryStage.show();
			}
		};
		
		// Random randNum = new Random();
		// donorID = randNum.nextInt(99999);
		
		
		welcomeMessage = new Text("Welcome, Donor " + fullNames);
		Text donorIDNumber = new Text("Your DonorID: " + donorID);
		donorIDNumber.getStyleClass().add("text");
		welcomeMessage.getStyleClass().add("text");

		DatabaseUtil.recordVisit("donor", donorID);

		int visitCount = DatabaseUtil.getVisitCount("donor", donorID);
	
		Text visitCountText = new Text("Donor " + donorID + ", you have visited this page " + visitCount + " times.");
		visitCountText.getStyleClass().add("text");
		
		Image profileImage = new Image(new File("resources/img/user-icon.png").toURI().toString()); // Adjust with your image path
		ImageView profileImageView = new ImageView(profileImage);
		profileImageView.setFitWidth(200);
		profileImageView.setFitHeight(200);

		// Create a circular clipping for the profile image
		Circle clip = new Circle(100, 100, 100);
		profileImageView.setClip(clip);

		// container for image and name
		VBox profileBox = new VBox(10, profileImageView, welcomeMessage, donorIDNumber,visitCountText);
		profileBox.setPadding(new Insets(10));

		donateItems = new Button("Donate");
		donorSettings = new Button ("Settings");
		donorNavigate = new Button("Navigate");
		donorLogout = new Button("Log Out");
		donateItems.setOnAction(handleDonating);
		donorLogout.setOnAction(handleDonorLogOut);
		donorNavigate.setOnAction(handleNav);
		volunteerTime = new Button("Volunteer");
		volunteerTime.setOnAction(handleVolunteering);
		donorTopButtons = new HBox(donateItems, volunteerTime, donorNavigate, donorLogout);
		donorWelcomeBox = new HBox();


		
		VBox donorRec = new VBox();
		Text donorRecMessage = new Text("Here are your top 3 Nonprofit Matches");
		donorRecMessage.getStyleClass().add("text");
		// Add a place to display top 3 Matched donation locations
		//The matching donors function runs every time you return back to donors main page
		Text recsField = new Text();
		
		// Makes sure recommendations are only run once
		if (runRecs == 0) {
			recs = matchingDonors(donorCityLoc, donorStateLoc, donorTimeCheck1, donorFood1, donorAid1, donorClothing1);
			runRecs = 1;
		}
		recsField.setText(recs);
		recsField.getStyleClass().add("text");
		HBox profileTopLevel = new HBox(profileBox);
		
		System.out.println(recs);
		donorTopButtons.setAlignment(Pos.TOP_RIGHT);
		profileBox.setAlignment(Pos.TOP_LEFT);
		donorRec.getChildren().addAll(donorRecMessage, recsField);
		
		donorWelcomeBox.getChildren().addAll(donorTopButtons, profileBox);
		donorWelcomeBox.setSpacing(500);
		donorRec.setAlignment(Pos.CENTER);
		
		VBox finalDonorBox = new VBox();
		finalDonorBox.getChildren().addAll(donorTopButtons, profileBox, donorRec);

		BorderPane donorWelcomePane = new BorderPane();
		//donorWelcomePane.setTop(donorWelcomeBox);
		donorWelcomePane.setCenter(finalDonorBox);
		
		donorWelcomePane.setStyle("-fx-background-color: #D5DCE0;");
		Scene blankScreen = new Scene(donorWelcomePane,850, 900);
		String cssPath = new File("src/main/java/survey.css").toURI().toString();
		blankScreen.getStylesheets().add(cssPath);
		return blankScreen;
	}// End of Donor Welcome Page
	
	
	// (*) donorStartScene()
	// The donor's initial survey that takes their information (name, email, phone number, )
	public Scene donorStartScene(Stage primaryStage) {
		
		// Donor Handlers
		// Food handler will be able to tell us if the donor is able to donate food
		handleFood = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (donorfood.isSelected()) {
					donorFood = true;
					System.out.println(donorFood);
				} else {
					donorFood = false;
					System.out.println(donorFood);
				}
			}
		};
		
		// firstAid handler will be able to tell us if the donor can donate first aid supplies
		handleAid = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (donorfirstAid.isSelected()) {
					donorAid = true;
					//System.out.println("TRUE");
				} else {
					donorAid = false;
					//System.out.print("FALSE");
				}
			}
		};
		
		// Clothing Handler will be able to tell us if the donor can donate clothing items
		handleClothing = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (donorclothing.isSelected()) {
					donorClothing = true;
					//System.out.println("TRUE");
				} else {
					donorClothing = false;
					//System.out.print("FALSE");
				}
			}
		};
		
		// Time Handler will tell us if the donor is offering to donate their time by volunteering
		handleTime = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (donortime.isSelected()) {
					donorTime = true;
					System.out.println(donorTime);
				} else {
					donorTime = false;
					System.out.print(donorTime);
				}
			}
		};
		
		// Link together all parts of the donors name from different fields
		handleDonorName = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String loginid = loginfield.getText();
				// System.out.println(loginid);
				// System.out.println(!loginid.isEmpty());
				if(!loginid.isEmpty()){
					DataDonor person = DatabaseUtil.GetOneDonor(loginid);
					donorID = Integer.parseInt(loginid);
					fullName = person.firstName + " " + person.lastName;
					donorSurveyCity = person.city;
					donorSurveyState = person.state;
					
					startAddress = stringCleanForGoogle(person.street);
					finalState = donorSurveyState;
					finalCity = donorSurveyCity;
					
					donorAid = person.firstAid;
					donorClothing = person.clothing;
					donorFood = person.food;
					donorTime = person.time;
					System.out.println(fullName);
					
					dataManager.addDonor(new Donor(
						person.firstName,
						person.lastName,
						person.email, 
						person.phoneNumber,
						person.food,
						person.firstAid, 
						person.clothing,
						person.time
					));
					
					dFirstName = person.firstName;
					dLastName = person.lastName;
					dEmail = person.email;
					dPhoneNum = person.phoneNumber; 

					primaryStage.setScene(donorWelcome(primaryStage, fullName, donorTime, donorFood, donorAid, donorClothing, donorSurveyCity, donorSurveyState));
					primaryStage.show();

				} else {
					Random randNum = new Random();
					donorID = randNum.nextInt(99999);
					fullName = donorNameFirst.getText() + " " + donorNameLast.getText();
					
					System.out.println(fullName + donorTime);
					donorSurveyCity = donorCity.getText();
					System.out.println("City: " + donorSurveyCity);
					donorSurveyState = donorState.getText();
					
					//Variables needed to make the call to the API
					startAddress = stringCleanForGoogle(userAddress.getText());
					finalState = donorSurveyState;
					finalCity = donorSurveyCity;
					
					
					dataManager.addDonor(new Donor(
						donorNameFirst.getText(), 
						donorNameLast.getText(), 
						donorEmail.getText(), 
						donorPhoneNumber.getText(), 
						donorFood, 
						donorClothing,
						donorAid, 
						donorTime
					));
					dFirstName = donorNameFirst.getText();
					dLastName = donorNameLast.getText();
					dEmail = donorEmail.getText();
					dPhoneNum = donorPhoneNumber.getText(); 
					primaryStage.setScene(donorWelcome(primaryStage, fullName, donorTime, donorFood, donorAid, donorClothing, donorSurveyCity, donorSurveyState));
					primaryStage.show();
				}
				
			}
		};
		
		// Donor Personal Information
		Text donorText = new Text("Welcome Donor, Please Enter the Info Below");
		
		//Add this line for all text so it follows the CSS file 
		donorText.getStyleClass().add("text");
		
		Text login = new Text("Login Id:");
		login.getStyleClass().add("text");
		loginfield = new TextField();
		loginfield.setPromptText("enter login id");
		loginfield.setText("111111");
		
		Text firstName = new Text("First name:");
		firstName.getStyleClass().add("text");
		donorNameFirst = new TextField();
		donorNameFirst.setPromptText("Enter your first name, ex. Charles");

		Text lastName = new Text("Last name:");
		lastName.getStyleClass().add("text");
		donorNameLast = new TextField();
		donorNameLast.setPromptText("Enter your last name, ex. Leclerc");

		Text email = new Text("Email:");
		email.getStyleClass().add("text");
		donorEmail = new TextField();
		donorEmail.setPromptText("Enter your email address, ex. charles16@gmail.com");

		Text phone = new Text("Phone Number:");
		phone.getStyleClass().add("text");
		donorPhoneNumber = new TextField();
		donorPhoneNumber.setPromptText("Enter your phone number, ex. 000-000-0000");
		
		Text streetAddress = new Text("Street Address:");
		streetAddress.getStyleClass().add("text");
		userAddress = new TextField();
		userAddress.setPromptText("Please enter your street address, ex 750 S Halsted St");

		Text dSurveyCity = new Text("City:");
		dSurveyCity.getStyleClass().add("text");
		donorCity = new TextField();
		donorCity.setPromptText("Please enter the city in which you reside in ex. Chicago");

		Text dSurveyState = new Text("State:");
		dSurveyState.getStyleClass().add("text");
		donorState = new TextField();
		donorState.setPromptText("Please enter the state in which you reside in ex. Illinois");
		
		// What the donor can donate
		Text categories = new Text("Please select to which categories you want to donate to below");
		categories.getStyleClass().add("text");
		
		donorfood = new CheckBox("Food");
		donorfirstAid = new CheckBox("First Aid Materials");
		donorclothing = new CheckBox("Clothing");
		donortime = new CheckBox("Volunteer your time");

		
		donorfood.setOnAction(handleFood);
		donorfirstAid.setOnAction(handleAid);
		donorclothing.setOnAction(handleClothing);
		donortime.setOnAction(handleTime);
		submitDonorSurvey = new Button("Submit Form");
		submitDonorSurvey.setOnAction(handleDonorName);
		
		//donorStartBox.getChildren().addAll(donorNameFirst, donorNameLast, donorEmail, donorPhoneNumber, cateories, donorfood, donorfirstAid, donorclothing, donortime);
		VBox donorStartBox = new VBox(10, 
		login, loginfield, firstName, donorNameFirst, lastName, donorNameLast, 
		email, donorEmail, phone, donorPhoneNumber, streetAddress, userAddress, dSurveyCity, 
		donorCity, dSurveyState, donorState , categories,
		donorfood, donorfirstAid, donorclothing, donortime);

		BorderPane donorPane = new BorderPane();
		donorPane.setTop(donorText);
		donorPane.setCenter(donorStartBox);
		donorPane.setBottom(submitDonorSurvey);
		donorStartBox.setPadding(new Insets(20)); //used for adding minor padding
        
		// code below aligns all items to the center of the screen
		BorderPane.setAlignment(donorText, Pos.TOP_CENTER); 
		donorStartBox.setAlignment(Pos.CENTER); 
		BorderPane.setAlignment(submitDonorSurvey, Pos.BOTTOM_CENTER);
		donorPane.setStyle("-fx-background-color: #D5DCE0;");
		// Color Codes that might work: DDDDE2, DDDDE2
		Scene donorStart = new Scene(donorPane, 850, 900);
		String cssPath = new File("src/main/java/survey.css").toURI().toString();
		donorStart.getStylesheets().add(cssPath);
		
		return donorStart;
		
	} // End of Donor Start Scene

	
	// (*) createWishlistPage()
	// Wish list page for beneficiaries
	public Scene createWishlistPage(Stage primaryStage) {
		VBox layout = new VBox(10); 
	
		// Creating the choice bar for different types
		ChoiceBox<String> typeChoiceBox = new ChoiceBox<>();
		typeChoiceBox.getItems().addAll("Food", "Clothes", "First-Aid", "Other"); 
		typeChoiceBox.setValue("Food"); 

		
		// Creating the text input for what they need
		TextField inputField = new TextField();
		inputField.setPromptText("Enter one item at a time");
		
		// Creating the ListView to show inputs
		ListView<String> wishlistView = new ListView<>();
		
		// Add button to submit the input
		Button addButton = new Button("Add to Wishlist");
		addButton.setFont(Font.font("Arial"));
		addButton.setOnAction(e -> {
			String selectedItem = typeChoiceBox.getValue();
			String inputText = inputField.getText();
			if (!inputText.isEmpty()) {
				wishlistView.getItems().add(selectedItem + ": " + inputText);
				inputField.clear(); // Clear input field after adding
			}
		});
		Text benWishMessage = new Text("Welcome to the Beneficiary Wishlist");
		benWishMessage.getStyleClass().add("text");
		Button backButton = new Button("Back");
		backButton.setOnAction(e -> {
			primaryStage.setScene(benificiaryWelcome(primaryStage, fullName));
		});
		HBox choiceInputs = new HBox(20);
		choiceInputs.getChildren().addAll(typeChoiceBox,inputField);
		
		BorderPane benWishPane = new BorderPane();
		
		layout.getChildren().addAll(choiceInputs, addButton, wishlistView, backButton);
		layout.setPadding(new Insets(80));
		//layout.setAlignment(Pos.CENTER);
		//benWishPane.getChildren().add(benWishMessage);
		
		benWishPane.setTop(benWishMessage);
		BorderPane.setAlignment(benWishMessage, Pos.TOP_CENTER);
		benWishPane.setCenter(layout);
		//BorderPane.setAlignment(layout,Pos.BASELINE_CENTER);
		benWishPane.setStyle("-fx-background-color: #D5DCE0;");
		Scene scene = new Scene(benWishPane, 850, 900);
		String cssPath = new File("src/main/java/survey.css").toURI().toString();
		scene.getStylesheets().add(cssPath);
		return scene;
	}
	
	
	// (*) makeNonprofitScene
	// Code that creates the non profit pages
	public Scene makeNonprofitScene(Stage primaryStage, String name) {
		//BorderPane root = new BorderPane();
		startAddress = stringCleanForGoogle("750 S Halsted St"); // Default start at UIC Student Center East
		finalCity = "Chicago";
		finalState = "Illinois";
		System.out.println(name);
		BorderPane finalProf = new BorderPane();
		// welcomeMessage2 = new Text("Welcome, Benificiary " + fullName + "\nYour BenificiaryID: " + beneID);
		// welcomeMessage2.getStyleClass().add("text");
		Text title = new Text(name);
        title.getStyleClass().add("title");
//        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        
        //root.setTop(title);

		Nonprofit nonprofit = DatabaseUtil.GetOneNonprofit(name);
		
		DatabaseUtil.recordVisit("nonprofit",nonprofit.id);
		int visitCount = DatabaseUtil.getVisitCount("nonprofit",nonprofit.id);
	
		Text visitCountText = new Text("Visits: " + visitCount);
		visitCountText.getStyleClass().add("text");

		VBox nonprofitInfo = new VBox();
        nonprofitInfo.setSpacing(20);  // Spacing between text elements
        nonprofitInfo.setPadding(new Insets(150,20,20,40));  // Padding for the VBox

        // Create Text nodes for each attribute and add them to the VBox
        Text nameText = new Text("Name: " + nonprofit.name);
		nameText.getStyleClass().add("text");
        Text einText = new Text("EIN: " + nonprofit.ein);
		einText.getStyleClass().add("text");
        Text emailText = new Text("Email: " + nonprofit.email);
		emailText.getStyleClass().add("text");
        Text phoneText = new Text("Phone Number: " + nonprofit.phoneNumber);
		phoneText.getStyleClass().add("text");
        Text addressText = new Text("Address: " + nonprofit.address);
		addressText.getStyleClass().add("text");

        // Add the Text nodes to the VBox
        nonprofitInfo.getChildren().addAll(visitCountText,nameText, einText, emailText, phoneText, addressText);
        nonprofitInfo.setAlignment(Pos.CENTER);
        // Set the right side of the BorderPane to display the nonprofitInfo VBox
        //root.setCenter(nonprofitInfo);
		//BorderPane.setAlignment(nonprofitInfo, Pos.CENTER);

		HBox buttonBox = new HBox(10);
		buttonBox.setPadding(new Insets(20,20,20,20));
		Button backButton = new Button("Back");
		backButton.setOnAction(e -> {
			
			primaryStage.setScene(makeHomeScene(primaryStage));
			primaryStage.show();
		});
		Button routeButton = new Button("Navigation");
		routeButton.setOnAction(e -> {
			navigationUser = 2;
			primaryStage.setScene(NavigationPage(primaryStage));
			primaryStage.show();
		});

		buttonBox.getChildren().addAll(routeButton, backButton);
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		//BorderPane.setAlignment(buttonBox, Pos.BOTTOM_CENTER);
		VBox nonprofV = new VBox();
		
		//root.setRight(buttonBox);
		// back button and route button
		// route button needs function call to navigation page
		
		// // navbar
		// beneTopButtons = new HBox();
		// beneItems = new Button("Wishlist");
		// beneItems.setOnAction(e -> primaryStage.setScene(createWishlistPage(primaryStage)));
		// beneUpdate  = new Button("Update Survey");
		// beneSettings = new Button ("Settings");
		// beneNavigate = new Button("Navigation");
		// beneLogout = new Button("Log Out");
		// beneTopButtons.getChildren().addAll(beneItems, beneUpdate, beneNavigate, beneLogout);
		// beneTopButtons.setAlignment(Pos.CENTER);
		// beneUpdate.setOnAction(handleUpdateSurvey);
		// beneLogout.setOnAction(handleBeneLogOut);
		// beneNavigate.setOnAction(handleNav);

		// // Profile Image
		Image profileImage = new Image(new File(nonprofit.imgPath).toURI().toString()); // Adjust with your image path
        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setFitWidth(200);
        profileImageView.setFitHeight(200);
        
//        BorderPane.setAlignment(profileImageView, Pos.TOP_CENTER);
        VBox topTitleImg = new VBox(50);
        topTitleImg.getChildren().addAll(title, profileImageView);
        topTitleImg.setAlignment(Pos.TOP_CENTER);
        // Create a circular clipping for the profile image
        //Circle clip = new Circle(100, 100, 100);
        //profileImageView.setClip(clip);
		//root.setLeft(profileImageView);
		//buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		nonprofV.getChildren().addAll(topTitleImg, nonprofitInfo);
		//nonprofV.setAlignment(Pos.BASELINE_CENTER);
		//nonprofitInfo.setAlignment(Pos.CENTER);
		//nonprofV.setAlignment(Pos.BOTTOM_LEFT);
		// // container for image and name
		// VBox profileBox = new VBox(0, profileImageView, welcomeMessage2);
        // profileBox.setPadding(new Insets(20));
		//nonprofV.setStyle("-fx-background-color: #DDDDE2;");
		//root.getChildren().add(nonprofV);
		//root.setAlignment(nonprofV, Pos.BASELINE_CENTER);
		
		
		//finalProf.getChildren().addAll(nonprofV, buttonBox);
		finalProf.setTop(topTitleImg);
		finalProf.setCenter(nonprofV);
		finalProf.setBottom(buttonBox);
//		BorderPane.setAlignment(topTitleImg, Pos.TOP_CENTER);
//		BorderPane.setAlignment(buttonBox, Pos.BOTTOM_CENTER);
//		BorderPane.setAlignment(nonprofV, Pos.CENTER);
		//finalProf.setAlignment(buttonBox, Pos.BOTTOM_CENTER);
		//finalProf.setBottom(buttonBox);
//		finalProf.setAlignment(buttonBox, Pos.BOTTOM_CENTER);
//		finalProf.setAlignment(nonprofV, Pos.BASELINE_CENTER);
		//Scene scene = new Scene(finalProf, 850, 900);
		finalProf.setStyle("-fx-background-color: #D5DCE0;");
		Scene scene = new Scene(finalProf, 850,900);
		String cssPath = new File("src/main/java/survey.css").toURI().toString();
		scene.getStylesheets().add(cssPath);
        return scene;
	}
	
	
	// (*) makeHomeScene()
	// Code that creates the home screen/ opening screen of the app
	public Scene makeHomeScene(Stage primaryStage) {
		donorSwitch = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				primaryStage.setScene(donorStartScene(primaryStage));
				primaryStage.show();
			}
		};
		
		benefitSwitch = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				primaryStage.setScene(benefitStartScene(primaryStage));
				primaryStage.show();
			}
		};

		// nonprofitSwitch = new EventHandler<ActionEvent>() {
		// 	public void handle(ActionEvent e) {
		// 		String option = nonProfitSelect
		// 		primaryStage.setScene(makeNonprofitScene(primaryStage, ));
		// 		primaryStage.show();
		// 	}
		// };
		// Use StackPane so buttons can be put over home screen 
		StackPane root = new StackPane();

        // Creating the hero image
        Image heroImage = new Image(new File("resources/img/logo.png").toURI().toString()); // Replace with the path to your hero image
        ImageView imageView = new ImageView(heroImage);

        // Setting the image to cover the entire screen
        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());
    

        // Creating two buttons
        Button DonorButton = new Button("DONOR");
        DonorButton.setFont(Font.font("Arial"));
        DonorButton.setOnAction(donorSwitch);
        String[]nonprofitArray = DatabaseUtil.GetAllNonprofits();
        ObservableList<String>nonprofitList = FXCollections.observableArrayList(nonprofitArray);
        ComboBox<String> nonProfitSelect = new ComboBox<String>(nonprofitList);
		nonProfitSelect.getStyleClass().add("text");
        nonProfitSelect.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String selectedOption = nonProfitSelect.getValue();
				DatabaseUtil.GetOneNonprofit(selectedOption);
				primaryStage.setScene(makeNonprofitScene(primaryStage, selectedOption));
				primaryStage.show();
            }
        });

        Button BeneficiaryButton = new Button("BENIFICIARY");
        BeneficiaryButton.setFont(Font.font("Arial"));
        BeneficiaryButton.setOnAction(benefitSwitch);
        
        // Creating an HBox to hold the buttons
        HBox buttonBox = new HBox(10); // spacing between buttons
        buttonBox.getChildren().addAll(DonorButton, nonProfitSelect, BeneficiaryButton);
        buttonBox.setPadding(new Insets(20)); // padding around the buttons
        buttonBox.setAlignment(Pos.BOTTOM_CENTER); // position the buttons at bottom center

        // add elements into StackPane
        root.getChildren().addAll(imageView, buttonBox);

        Scene scene = new Scene(root, 850, 900);
		String cssPath = new File("src/main/java/survey.css").toURI().toString();
		scene.getStylesheets().add(cssPath);
        return scene;
		
	}	

}
