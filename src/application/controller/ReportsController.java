package application.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import application.Asset;
import application.Category;
import application.CommonObjs;
import application.Location;
import data_access_layer.DataAccessLayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReportsController {
	private CommonObjs commonObjs = CommonObjs.getInstance();
	private MainController mainController = commonObjs.getMainController();
	private DataAccessLayer DAL = commonObjs.getDAL();
	
	@FXML private ComboBox<String> categoryType;
	@FXML private ComboBox<String> locationType;
	@FXML private TableView<Asset> results_table;
	@FXML private TableColumn<Asset,String> asset_name_table;
	@FXML private TableColumn<Asset,String> asset_category_table;
	@FXML private TableColumn<Asset,String> asset_location_table;
	@FXML private Button viewAssetButton;
	@FXML private Label result_message;
	
	@FXML private Asset selected_asset;
	
	@FXML public void initialize() 
	{
		//store HashMaps with existing data from csv files
		DAL.storeCategoriesFromFile();
		DAL.storeLocationsFromFile();
		DAL.storeAssetsFromFile();
		
		//initialize Hashmaps for Categories and Locations
		HashMap<String, Category> categoriesMap = DAL.getCategoriesMap();
		HashMap<String, Location> locationsMap = DAL.getLocationsMap();
		
		//set the HashMaps as Lists to populate the combobox
		List<String> categoryNames = new ArrayList<>(categoriesMap.keySet());
		List<String> locationNames = new ArrayList<>(locationsMap.keySet());
		
		//prompt Categories and Locations combobox with message
		categoryType.setPromptText("Select a Category...");
		locationType.setPromptText("Select a Location..");
		
		// set styles
		categoryType.setStyle("-fx-font-size: 24;");
		locationType.setStyle("-fx-font-size: 24;");
		results_table.setStyle("-fx-font-size: 20;");
		
		results_table.setPlaceholder(new Label("No assets found."));
		
		viewAssetButton.setDisable(true);
		
		//populate Categories and Locations combobox with existing data
		categoryType.getItems().addAll(categoryNames);
		locationType.getItems().addAll(locationNames);
		
		PropertyValueFactory<Asset,String> pvf = 
				new PropertyValueFactory<Asset,String>("assetName");
		asset_name_table.setCellValueFactory(pvf);
		
		pvf = new PropertyValueFactory<Asset,String>("location");
		asset_location_table.setCellValueFactory(pvf);
		
		pvf = new PropertyValueFactory<Asset,String>("category");
		asset_category_table.setCellValueFactory(pvf);
		
	}
	
	@FXML public void selectAssetOp() {
		selected_asset = results_table.getSelectionModel().getSelectedItem();
		
		// if an asset is selected
		if (selected_asset != null) {
			viewAssetButton.setDisable(false);
		}
	}
	
	@FXML public void viewAssetOp() {
		// set the previous pane to go back after viewing
		mainController.setPrevPane(mainController.getCurrentPane());
		mainController.setPrevLoader(mainController.getCurrentLoader());
		
		// load the asset info page
		mainController.showAssetInfoOp();
		    
		//instantiate an AssetInfoController object to display the selected asset's attributes
		AssetInfoController assetInfoController = mainController.getCurrentLoader().getController();
		    
		//call the displayAssetInfo to show the asset information
		assetInfoController.displayAssetInfo(selected_asset);
	}	
	
	@FXML public void categorySelectedOp() {
		locationType.setValue(null);
		viewAssetButton.setDisable(true);
		String selectedCategory = categoryType.getValue();
		Collection<Asset> results = DAL.filterAssetByCategory(selectedCategory);
		
		result_message.setText(results.size() + " assets found under " + selectedCategory + "!");
		
		results_table.getItems().setAll(results);
	}
	
	@FXML public void locationSelectedOp() {
		categoryType.setValue(null);
		viewAssetButton.setDisable(true);
		String selectedLocation = locationType.getValue();
		Collection<Asset> results = DAL.filterAssetByLocation(selectedLocation);
		
		result_message.setText(results.size() + " assets found in " + selectedLocation + "!");
		
		results_table.getItems().setAll(results);
	}
}
