package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ExpiredAssetInfoController {
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML private Button backBtn;
	
	@FXML public void returnToExpiredAssetsOp()
	{
		//Return to the page that displays all expired assets
		URL url = getClass().getClassLoader().getResource("view/ExpiredAssets.fxml");
		
		try {
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			HBox mainBox = commonObjs.getMainBox();
			
			if(mainBox.getChildren().size() > 1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(pane1);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
