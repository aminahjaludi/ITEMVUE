package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;

import application.Asset;
import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ExpiredAlertController {

	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML private Button OKBtn;
	@FXML private Button showMoreBtn;
	@FXML private Label expiredAsset;
	
	@FXML public void initialize()
	{
		//Get expired assets
		LinkedList<Asset> expired_assets = commonObjs.getExpiredAssets();
		
		int numOfExpired = expired_assets.size() - 1;
		
		expiredAsset.setWrapText(true);
		
		//Display different message based on number of expired assets
		if(numOfExpired == 1)
		{
			expiredAsset.setText(expired_assets.getFirst().getAssetName() + 
					" and " + numOfExpired + " other asset have expired warranties.");
		}
		else if(numOfExpired > 0)
		{
			expiredAsset.setText(expired_assets.getFirst().getAssetName() + 
					" and " + numOfExpired + " other assets have expired warranties.");
		}
		else
		{
			expiredAsset.setText(expired_assets.getFirst().getAssetName() + 
					" has an expired warranty.");
		}
	}
	
	@FXML public void showExpiredOp()
	{
		//Close pop-up window
		closeWindowOp();
	    
	    //Show expired assets page
	    HBox mainBox = commonObjs.getMainBox();
	    URL url = getClass().getClassLoader().getResource("view/ExpiredAssets.fxml");
		
		try {
			AnchorPane pane2 = (AnchorPane) FXMLLoader.load(url);
			
			if(mainBox.getChildren().size() > 1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(pane2);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	    
	}
	
	@FXML public void closeWindowOp()
	{
		//Close pop-up window
		Stage stage = (Stage) OKBtn.getScene().getWindow();
	    stage.close();
	}
}
