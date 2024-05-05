package application;

import java.util.HashMap;
import java.util.LinkedList;

import data_access_layer.DataAccessLayer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			CommonObjs commonObjs = CommonObjs.getInstance();
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
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
