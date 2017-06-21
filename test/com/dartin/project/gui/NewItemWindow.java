package com.dartin.project.gui;

import com.dartin.project.AppLauncher;
import com.dartin.project.gui.controller.CollectionOverviewController;
import com.dartin.project.gui.controller.NewItemController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class NewItemWindow extends Application {

	private NewItemController controller;
	private Stage primaryStage;
	private AnchorPane rootLayout;
	private static final String WINDOW_TITLE = "Item editor";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(WINDOW_TITLE);
		primaryStage.setResizable(false);
		initLayout();
	}

	private void initLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(NewItemWindow.class.getResource("resources/view/newItem.fxml"));
			rootLayout = loader.load();
			controller = loader.getController();
			controller.setControllingApplication(this);
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void returnResults(String name, String usage, String size) {
		//sumbut to server
		AppLauncher.manageSetUpdate
				(name, usage, size, CollectionOverviewController.SET_ADD);
	}

	public void onCloseRequest(){
		Platform.exit();
	}
}
