package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import application.Asset;
import application.CommonObjs;
import application.controller.*;
import data_access_layer.DataAccessLayer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class ExpiredAssetsController {
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	private MainController mainController = commonObjs.getMainController();

	@FXML private TableView<Asset> expired_table;
	@FXML private TableColumn<Asset,String> asset_name;
	@FXML private TableColumn<Asset,String> asset_warranty;
	@FXML private Button viewAssetButton;
	
	//create private variable for asset to be selected
	Asset selected_asset;
	
	@FXML public void initialize()
	{
		//Set tables font size
		expired_table.setStyle("-fx-font-size: 20;");
		
		viewAssetButton.setDisable(true);
		
		//Set message for empty table
		expired_table.setPlaceholder(new Label("No expired assets"));

		Collection<Asset> expired_assets = commonObjs.getExpiredAssets();
		
		//Populate the expired assets table
		PropertyValueFactory<Asset,String> pvf = 
				new PropertyValueFactory<Asset,String>("assetName");
		asset_name.setCellValueFactory(pvf);
		
		pvf = new PropertyValueFactory<Asset,String>("expDate");
		asset_warranty.setCellValueFactory(pvf);
		
		expired_table.getItems().setAll(expired_assets);
	}
	
	@FXML public void showExpiredAssetInfoOp()
	{
		// set the previous loader to go back after viewing
		mainController.setPrevLoader(mainController.getCurrentLoader());
		// set the previous pane to go back after viewing
		mainController.setPrevPane(mainController.getCurrentPane());
		
		// load the asset info page
		mainController.showAssetInfoOp();
		    
		//instantiate an AssetInfoController object to display the selected asset's attributes
		AssetInfoController assetInfoController = mainController.getCurrentLoader().getController();
		    
		//call the displayAssetInfo to show the asset information
		assetInfoController.displayAssetInfo(selected_asset);
	}
	
	@FXML public void selectAssetOp() {
		selected_asset = expired_table.getSelectionModel().getSelectedItem();
		
		if (selected_asset != null) {
			viewAssetButton.setDisable(false);
		}
	}
	
}
