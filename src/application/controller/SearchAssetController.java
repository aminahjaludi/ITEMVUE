package application.controller;

import application.Asset;
import application.Category;
import application.controller.EditAssetController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;

import application.CommonObjs;
import application.Location;
import data_access_layer.DataAccessLayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SearchAssetController
{
	private CommonObjs commonObjs = CommonObjs.getInstance();
	private MainController mainController = commonObjs.getMainController();
	private DataAccessLayer DAL = commonObjs.getDAL();
	
	@FXML private TextField search_term;
	@FXML private Label result_message;
	@FXML private Label asset_name;
	@FXML private Label asset_category;
	@FXML private Label asset_location;
	@FXML private Button edit_button;
	@FXML private Button delete_button;
	@FXML private Button prev_button;
	@FXML private Label result_number;
	@FXML private Button next_button;
	@FXML private AnchorPane searchContainer;

	private ArrayList<Asset> results;
	private int resultsCount;
	private Asset currentAsset;
	private int currentAssetIndex;

	public void initialize() {
		//populate Asset Hashmap with existing assets in csv file
		DAL.storeAssetsFromFile();
		//disable buttons until search button is pressed and text by calling displayNoResults
		displayNoResults();
		
		//populate Category and Location hashmaps with previous data
		DAL.storeCategoriesFromFile();
		DAL.storeLocationsFromFile();
		
		HashMap<String, Category> categoriesMap = DAL.getCategoriesMap();
		HashMap<String, Location> locationsMap = DAL.getLocationsMap();
	}
	
	public void setResultMessage(String text) {
		result_message.setText(text);
	}

	//changes the visibility of buttons, shows the results of the keyword search
	private void loadPageData() {
		//Retrieve the currentAsset
		currentAsset = results.get(currentAssetIndex);
		//show the results of the Asset's attributes after searching
		asset_name.setText(currentAsset.getAssetName());
		asset_category.setText(currentAsset.getCategory());
		asset_location.setText(currentAsset.getLocation());
		//display the number of assets that match the searched keyword
		result_number.setText(Integer.toString(currentAssetIndex + 1));

		if (currentAssetIndex == 0)
		{
			prev_button.setDisable(true);
		}
		else
		{
			prev_button.setDisable(false);
		}

		if (resultsCount == currentAssetIndex + 1)
		{
			next_button.setDisable(true);
		}
		else
		{
			next_button.setDisable(false);
		}
	}
	
	//reset the Results section of the Search/Manage asset Page for assets not found and first opening page
	private void displayNoResults()
	{
		//show the results of the Asset's attributes as blank after no results found
		asset_name.setText("");
		asset_category.setText("");
		asset_location.setText("");
		//disable buttons
		edit_button.setDisable(true);
		delete_button.setDisable(true);
		prev_button.setDisable(true);
		next_button.setDisable(true);
	}

	//when search button is pressed
	@FXML public void searchAssetOp() 
	{	
		//retrieve ArrayList of all Assets that contain the searched keyword
		results = DAL.searchAsset(search_term.getText().toLowerCase());

		//display the number of Asset results
		resultsCount = results.size();
		//if the search text field does not contain any keywords, prompt message
		if (search_term.getText().length() == 0)
		{
			result_message.setText("Please type in a keyword.");
		}
		//if no Assets are found, display message
		else if (resultsCount == 0)
		{
			result_message.setText("No results found!");
			displayNoResults();
		}
		//else if here, Asset found
		else {
			//display found message
			result_message.setText(resultsCount + " results found!");
			currentAssetIndex = 0;
			//display buttons and found Assets by calling loadPageData 
			loadPageData();
			//disable buttons
			edit_button.setDisable(false);
			delete_button.setDisable(false);
		}
	}

	@FXML public void prevAssetOp() 
	{
		currentAssetIndex -= 1;
		loadPageData();
	}
	
	@FXML public void nextAssetOp() 
	{
		currentAssetIndex += 1;
		loadPageData();
	}

	//triggered when user presses edit button after Asset is found
	@FXML public void editAssetOp() 
	{
		// set the previous loader to go back after viewing
		mainController.setPrevLoader(mainController.getCurrentLoader());
		// set the previous pane to go back after viewing
		mainController.setPrevPane(mainController.getCurrentPane());
		
		// go to edit asset page
		mainController.showEditAssetOp();

		// get the EditAssetController object from mainController
		EditAssetController editController = mainController.getCurrentLoader().getController();
		
		// store the Asset to be edited into the EditAssetController class, and show all existing data of Asset
		editController.storeAssetToEdit(currentAsset);
	}

	//triggered when user presses delete
	@FXML public void deleteAssetOp() 
	{
		//reload the page 
		loadPageData();
		
		//save the current Asset object into currentAsset
		currentAsset = results.get(currentAssetIndex);
	
		//save the old name of the asset to be displayed later in the result_message
		String name = currentAsset.getAssetName();

		LinkedList<Asset> recents = commonObjs.getRecentAssets();

		for (Iterator<Asset> iterator = recents.iterator(); iterator.hasNext(); ) 
		{
		    Asset value = iterator.next();
		    
		    if (currentAsset.getAssetName().equals(value.getAssetName())) 
		    {
		        iterator.remove();
		    }
		}
		
		LinkedList<Asset> favorites = commonObjs.getFavoriteAssets();

		for (Iterator<Asset> iterator = favorites.iterator(); iterator.hasNext(); ) 
		{
		    Asset value = iterator.next();
		    
		    if (currentAsset.getAssetName().equals(value.getAssetName())) 
		    {
		        iterator.remove();
		    }
		}
		
		//call the deleteAssetData to remove the asset from hashmap and csv file
		DAL.deleteAssetData(currentAsset);
		//prompt delete message
		
		// update assetsMap in DAL
		DAL.storeAssetsFromFile();
		
		// redo search so results are updated
		searchAssetOp();
		
		// set result message
		result_message.setText(name + " is now deleted!");
	}
}
