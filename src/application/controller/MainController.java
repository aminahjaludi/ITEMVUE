package application.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController {

	@FXML public HBox mainBox;
	
	private FXMLLoader currentLoader;
	private FXMLLoader previousLoader;
	private AnchorPane currentPane;
	private AnchorPane previousPane;
	
	public FXMLLoader getCurrentLoader() {
		return currentLoader;
	}
	
	public void setCurrentLoader(FXMLLoader loader) {
		currentLoader = loader;
	}
	
	public AnchorPane getCurrentPane() {
		return currentPane;
	}
	
	public void setCurrentPane(AnchorPane pane) {
		currentPane = pane;
	}
	
	public FXMLLoader getPrevLoader() {
		return previousLoader;
	}
	
	public void setPrevLoader(FXMLLoader loader) {
		previousLoader = loader;
	}
	
	public AnchorPane getPrevPane() {
		return previousPane;
	}
	
	public void setPrevPane(AnchorPane pane) {
		previousPane = pane;
	}
	
	@FXML
	public void initialize() {
		showHomeOp();
	}
	
	@FXML public void showCategoryOp() {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/Category.fxml"));
		currentLoader = loader;
		
		try {
			AnchorPane pane2 = (AnchorPane) loader.load();
			
			if(mainBox.getChildren().size() > 1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(pane2);
			currentPane = pane2;
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@FXML public void showHomeOp() {		
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/Home.fxml"));
		currentLoader = loader;
		
		try {
			AnchorPane pane2 = (AnchorPane) loader.load();
			
			if(mainBox.getChildren().size() > 1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(pane2);
			currentPane = pane2;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@FXML public void showLocationOp() {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/Location.fxml"));
		currentLoader = loader;
		
		try {
			AnchorPane pane2 = (AnchorPane) loader.load();
			
			if(mainBox.getChildren().size() > 1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(pane2);
			currentPane = pane2;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@FXML public void showCreateAssetOp() {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/CreateAsset.fxml"));
		currentLoader = loader;
		
		try {
			AnchorPane pane2 = (AnchorPane) loader.load();
			
			if(mainBox.getChildren().size() > 1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(pane2);
			currentPane = pane2;
		} 
		catch (IOException e) 
		{

			e.printStackTrace();
		}
	}

	@FXML public void showManageAssetOp() {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/SearchAsset.fxml"));
		currentLoader = loader;
		
		try {
			AnchorPane pane2 = (AnchorPane) loader.load();
			
			if(mainBox.getChildren().size() > 1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(pane2);
			currentPane = pane2;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void showEditAssetOp() {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/EditAsset.fxml"));
		currentLoader = loader;
		try {
			AnchorPane pane2 = (AnchorPane) loader.load();
			
			if(mainBox.getChildren().size() > 1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(pane2);
			currentPane = pane2;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@FXML public void showReportOp() {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/Reports.fxml"));
		currentLoader = loader;
		
		try {
			AnchorPane pane2 = (AnchorPane) loader.load();
			
			if(mainBox.getChildren().size() > 1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(pane2);
			currentPane = pane2;
		} 
		catch (IOException e) 
		{

			e.printStackTrace();
		}
	}
	
	public void showExpiredAssetsOp() {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/ExpiredAssets.fxml"));
		currentLoader = loader;
		
		try {
			AnchorPane pane2 = (AnchorPane) loader.load();
			
			if(mainBox.getChildren().size() > 1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(pane2);
			currentPane = pane2;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void showAssetInfoOp() {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/AssetInfo.fxml"));
		currentLoader = loader;
		
		try {
			AnchorPane pane2 = (AnchorPane) loader.load();
			
			if(mainBox.getChildren().size() > 1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(pane2);
			currentPane = pane2;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void previousPage() {
		if(mainBox.getChildren().size() > 1)
			mainBox.getChildren().remove(1);
		
		mainBox.getChildren().add(previousPane);
		currentPane = previousPane;
		currentLoader = previousLoader;
		previousLoader = null;
		previousPane = null;
	}
	
}
