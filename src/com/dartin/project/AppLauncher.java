package com.dartin.project;

import com.dartin.util.Item;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AppLauncher extends Application{
    private BorderPane rootLayout;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Lab7");

        initRoot();

        showCollectionOverview();
    }

    private void initRoot(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AppLauncher.class.getResource(
                    "/com/dartin/project/gui/resources/view/rootLayout.fxml"));
            rootLayout = loader.load();

            Scene sc = new Scene(rootLayout);
            primaryStage.setScene(sc);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCollectionOverview(){
        AnchorPane stage;
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(AppLauncher.class.getResource(
                "/com/dartin/project/gui/resources/view/collectionOverview.fxml"));
            stage = loader.load();

            rootLayout.setCenter(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}