package application.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.Asset;
import application.Category;
import application.CommonObjs;
import application.Location;
import data_access_layer.DataAccessLayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AssetInfoController {
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	private MainController mainController = commonObjs.getMainController();
	private DataAccessLayer DAL = commonObjs.getDAL();
	
	@FXML private TextField asset_name;
	@FXML private TextField cost;
	@FXML private ComboBox<String> categoryType;
	@FXML private ComboBox<String> locationType;
	@FXML private DatePicker purchase_date;
	@FXML private DatePicker warranty_date;
	@FXML private TextArea asset_descr;
	@FXML private Label result_message;
	@FXML private CheckBox favorited;
	
	//initialize id for the EditAsset.fxml's AnchorPane //use for visibility
	@FXML private AnchorPane editContainer;
	
	private Asset selectedAsset;
	
	
	
	public void initialize()
	{
		//populate hashmaps with existing data from csv files
		DAL.storeAssetsFromFile();
		DAL.storeCategoriesFromFile();
		DAL.storeLocationsFromFile();
		
		//store Category and Location hashmaps into their own variables
		HashMap<String, Category> categoriesMap = DAL.getCategoriesMap();
		HashMap<String, Location> locationsMap = DAL.getLocationsMap();


		//initialize Lists and store the hashmaps into them to be used for comboboxes
		List<String> categoryNames = new ArrayList<>(categoriesMap.keySet());
		List<String> locationNames = new ArrayList<>(locationsMap.keySet());
		
		categoryType.setStyle("-fx-font-size: 24;");
		//locationType.setStyle("-fx-font-size: 24;");
		
		purchase_date.setStyle("-fx-font-size: 24;");
		warranty_date.setStyle("-fx-font-size: 24;");
		
		//populate comboboxes with Categories and Locations
		categoryType.getItems().addAll(categoryNames);
		locationType.getItems().addAll(locationNames);
	
	}
	
	public void displayAssetInfo(Asset selected)
	{
		//instantiate DateTimeFormatter object to format the warranty and purchase dates
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
		
		//set the opacity greater than 0 so the text is not faded for the assets attributes
		asset_name.setStyle("-fx-opacity: 1.0;");
		categoryType.setStyle("-fx-font-size: 24; -fx-opacity: 4.0;");
		locationType.setStyle("-fx-font-size: 24; -fx-opacity: 4.0;");
		
		
		//Prompt all established Asset values 
		asset_name.setText(selected.getAssetName());
		categoryType.setPromptText(selected.getCategory());
		locationType.setPromptText(selected.getLocation());
		favorited.setSelected(selected.getFavorited());
		
		
		if (selected.getPurchDate().equals("_EMPTY_")) {
			purchase_date.setValue(null);
		}
		else {
			purchase_date.setValue(LocalDate.parse(selected.getPurchDate(), formatter));
			purchase_date.setStyle("-fx-font-size: 24; -fx-opacity: 4.0;");
		}
		
		if (selected.getExpDate().equals("_EMPTY_")) {
			warranty_date.setValue(null);
		}
		else {
			warranty_date.setValue(LocalDate.parse(selected.getExpDate(), formatter));
			warranty_date.setStyle("-fx-font-size: 24; -fx-opacity: 4.0;");
		}
		
		if (selected.getDescription().equals("_EMPTY_")) 
			asset_descr.setText("");
		else {
			asset_descr.setText(selected.getDescription());
			asset_descr.setStyle("-fx-font-size: 24; -fx-opacity: 4.0;");
		}
		if (selected.getPurchVal().equals("_EMPTY_")) 
			cost.setText("");
		else {
			cost.setText(selected.getPurchVal());
			cost.setStyle("-fx-font-size: 24; -fx-opacity: 1.0;");
		}
		
	}
	
	//triggered when user presses the back button
	@FXML public void backOp()
	{
		//use instance of mainController to show ExpiredAssets page
		mainController.previousPage();
	}
	
	

}
