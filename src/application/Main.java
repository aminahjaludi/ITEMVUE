package application;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import data_access_layer.DataAccessLayer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			DataAccessLayer DAL = new DataAccessLayer();
			DAL.storeAssetsFromFile();
			
			//Initialize commonObjs recentAssets
			LinkedList<Asset> recentAssets = new LinkedList<Asset>();
			commonObjs.setRecentAssets(recentAssets);
			
			//Initialize commonObjs favoriteAssets
			HashMap<String,Asset> assets = DAL.getAssetsMap();
			LinkedList<Asset> favoriteAssets = new LinkedList<Asset>();
			
			//Get favorites from csv file
			for(Asset asset : assets.values())
	    	{
	    		if(asset.getFavorited())
	    		{
	    			favoriteAssets.add(asset);
	    		}
	    	}
			
			commonObjs.setFavoriteAssets(favoriteAssets);
			
			FXMLLoader mainLoader = new FXMLLoader(getClass().getClassLoader().getResource("view/Main.fxml"));
			HBox mainBox = (HBox)mainLoader.load();
			Scene scene = new Scene(mainBox);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//Keep reference of mainBox inside the commonObjs object
			commonObjs.setMainBox(mainBox);
			commonObjs.setMainController(mainLoader.getController());
			
			//Make window non-resizable
			primaryStage.setResizable(false);
			
			//Check if any asset warranties have expired
			checkForExpired();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkForExpired()
	{
		DataAccessLayer DAL = new DataAccessLayer();
		DAL.storeAssetsFromFile();
		Collection<Asset> assets =  DAL.getAssetsMap().values();
		
		//Get current date
		LocalDate current_date = LocalDate.now(); 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
		
		LinkedList<Asset> expired_assets = new LinkedList<Asset>();
		
		for(Asset curr_asset : assets)
		{
			if(!curr_asset.getExpDate().equals("_EMPTY_")) //If warranty date isn't empty
			{
				//Get warranty date in correct format
				LocalDate asset_exp = LocalDate.parse(curr_asset.getExpDate(), formatter);
				
				//Check if warranty has expired
				if(asset_exp.isEqual(current_date) || asset_exp.isBefore(current_date))
				{
					System.out.println(curr_asset.getAssetName() + " is expired");
					expired_assets.add(curr_asset); //Add to expired list
				}
			}
		}
		
		if(expired_assets.size() > 0)
		{
			commonObjs.setExpiredAssets(expired_assets);
			showExpiredAlert(); //Show pop-up
		}
	}
	
	public void showExpiredAlert()
	{
		//Create new window to show expired assets alert
		Stage expiredPopUp = new Stage();
		FXMLLoader expiredLoader = new FXMLLoader(getClass().getClassLoader().getResource("view/ExpiredAlert.fxml"));
		AnchorPane anchorPane;
		
		try {
			anchorPane = (AnchorPane) expiredLoader.load();
			Scene scene = new Scene(anchorPane);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
			
			expiredPopUp.setScene(scene);
			expiredPopUp.show();
			expiredPopUp.setResizable(false);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
