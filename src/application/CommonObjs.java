package application;
import java.util.LinkedList;

import application.controller.MainController;
import javafx.scene.layout.HBox;

public class CommonObjs 
{
	private static CommonObjs commonObjs = new CommonObjs();
	
	private HBox mainBox;
	private MainController mainController;
	
	private LinkedList<Asset> recentAssets;
	private LinkedList<Asset> favoriteAssets;
	private LinkedList<Asset> expiredAssets;
	
	private CommonObjs()
	{
		
	}
	
	public static CommonObjs getInstance()
	{
		return commonObjs;
	}

	public HBox getMainBox() {
		return mainBox;
	}

	public void setMainBox(HBox mainBox) {
		this.mainBox = mainBox;
	}
	
	public MainController getMainController() {
		return mainController;
	}
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public LinkedList<Asset> getRecentAssets() {
		return recentAssets;
	}

	public void setRecentAssets(LinkedList<Asset> recentAssets) {
		this.recentAssets = recentAssets;
	}

	public LinkedList<Asset> getFavoriteAssets() {
		return favoriteAssets;
	}

	public void setFavoriteAssets(LinkedList<Asset> favoriteAssets) {
		this.favoriteAssets = favoriteAssets;
	}

	public LinkedList<Asset> getExpiredAssets() {
		return expiredAssets;
	}

	public void setExpiredAssets(LinkedList<Asset> expiredAssets) {
		this.expiredAssets = expiredAssets;
	}
	
}
