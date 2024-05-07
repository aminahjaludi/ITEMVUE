package application.controller;

import java.util.Collection;

import application.Asset;
import application.CommonObjs;
import data_access_layer.DataAccessLayer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeController {
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML private TableView<Asset> favorites_table;
	@FXML private TableColumn<Asset,String> fave_asset;
	@FXML private TableColumn<Asset,String> fave_category;
	@FXML private TableColumn<Asset,String> fave_location;
	
	@FXML private TableView<Asset> recents_table;
	@FXML private TableColumn<Asset,String> recent_asset;
	@FXML private TableColumn<Asset,String> recent_location;
	@FXML private TableColumn<Asset,String> recent_category;
	
	@FXML public void initialize() 
	{
		recents_table.getItems().clear();
		
		//Set tables font size
		favorites_table.setStyle("-fx-font-size: 20;");
		recents_table.setStyle("-fx-font-size: 20;");
		
		//Set message for empty tables
		favorites_table.setPlaceholder(new Label("No favorite assets"));
		recents_table.setPlaceholder(new Label("No recently added assets"));

		DataAccessLayer DAL = new DataAccessLayer();
		DAL.storeAssetsFromFile();
		
		Collection<Asset> favorites = commonObjs.getFavoriteAssets();;
		Collection<Asset> recently_added = commonObjs.getRecentAssets();
		
		//Populate the favorite assets table
		PropertyValueFactory<Asset,String> pvf = 
				new PropertyValueFactory<Asset,String>("assetName");
		fave_asset.setCellValueFactory(pvf);
		
		pvf = new PropertyValueFactory<Asset,String>("location");
		fave_location.setCellValueFactory(pvf);
		
		pvf = new PropertyValueFactory<Asset,String>("category");
		fave_category.setCellValueFactory(pvf);
		
		favorites_table.getItems().setAll(favorites);
		
		//Populate the recently added assets table
		pvf = new PropertyValueFactory<Asset,String>("assetName");
		recent_asset.setCellValueFactory(pvf);

		pvf = new PropertyValueFactory<Asset,String>("location");
		recent_location.setCellValueFactory(pvf);
		
		pvf = new PropertyValueFactory<Asset,String>("category");
		recent_category.setCellValueFactory(pvf);
		
		recents_table.getItems().setAll(recently_added);
	}
	
}
