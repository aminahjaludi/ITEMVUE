package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import application.Asset;
import application.CommonObjs;
import application.controller.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	
	//create private variable for asset to be selected
	Asset selected_asset;
	
	@FXML public void initialize()
	{
		//Set tables font size
		expired_table.setStyle("-fx-font-size: 20;");
		
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
		selected_asset = expired_table.getSelectionModel().getSelectedItem();
		//go to the AssetInfo fxml page
		mainController.showAssetInfoOp();
		
		//retrieve the AssetInfo loader 
		FXMLLoader infoLoader = mainController.getCurrentLoader();
		    
		//instantiate an AssetInfoController object to display the selected asset's attributes
		AssetInfoController assetInfoController = infoLoader.getController();
		    
		//call the displayAssetInfo to show the asset information
		assetInfoController.displayAssetInfo(selected_asset);

	}
	
}
