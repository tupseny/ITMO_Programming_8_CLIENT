package com.dartin.project.gui;

import com.daniily.util.Tree;
import com.dartin.project.AppLauncher;
import com.dartin.project.client.ClientData;
import com.dartin.project.gui.controller.CollectionOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.dartin.project.util.UniversalConverter;

import java.io.IOException;

public class CollectionOverviewWindow extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private CollectionOverviewController controller;
	private static final String WINDOW_TITLE = "ITMO BEST LAB EVER BY 666DEN4UK666 AND XXXAWESOMEMARTINXXX Client";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(WINDOW_TITLE);
		primaryStage.setResizable(false);
		initLayout();
		loadCollectionOverview();
	}

	private void initLayout(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CollectionOverviewWindow.class.getResource("resources/view/rootLayout.fxml"));
			rootLayout = loader.load();


			Scene scene = new Scene(rootLayout, 800, 600);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadCollectionOverview() {
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CollectionOverviewWindow.class.getResource("resources/view/collectionOverview.fxml"));
			AnchorPane collectionOverview = loader.load();

			rootLayout.setCenter(collectionOverview);

			controller = loader.getController();
			controller.setControllingApplication(this);

			refreshTreeView(ClientData.getInstance().getTreeCollectionCopy());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void refreshTreeView(Tree<?> newTree) {
		controller.setTreeView(UniversalConverter.treeToTreeView(newTree));
	}

	public void manageSetUpdRequest(int cmd) {
		switch (cmd) {
			case CollectionOverviewController.SET_ADD:
				AppLauncher.callNewItemWindow();
				break;
		}
	}

}
