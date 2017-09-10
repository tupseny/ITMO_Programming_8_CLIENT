package com.dartin.project;

import com.dartin.project.client.UserPreferences;
import com.dartin.project.gui.CollectionOverviewModel;
import com.dartin.project.gui.NewItemModel;
import com.dartin.project.gui.controller.CollectionOverviewController;
import com.dartin.project.gui.controller.NewItemController;
import com.dartin.project.gui.controller.RootLayoutController;
import com.dartin.project.net.RequestManager;
import com.dartin.util.Item;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@SuppressWarnings("FieldCanBeLocal")
public class AppLauncher extends Application {

    private static Stage primaryStage;
    private static BorderPane rootLayout;
    private static CollectionOverviewController collectionOverviewController;
    private static RootLayoutController rootLayoutController;
    private static CollectionOverviewModel overviewModel;

    private final String WINDOW_TITLE = "ITMO BEST LAB EVER BY 666DEN4UK666 AND XXXAWESOMEMARTINXXX Client"; //lol
    private static final String ip = "178.248.140.168";

    public static String getIp(){
        return ip;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        System.out.println(UserPreferences.localeResources.getString("root_close"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setResizable(false);
        initLayout();
        loadCollectionOverview();
        collectionOverviewController.setWaitCollection(true);
        overviewModel.getNewRoot();

    }

    private void loadCollectionOverview() {
        try {
            overviewModel = new CollectionOverviewModel(this, ip);
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(UserPreferences.localeResources);
            loader.setLocation(AppLauncher.class.getResource("/com/dartin/project/gui/resources/view/collectionOverview.fxml"));
            AnchorPane collectionOverview = loader.load();

            collectionOverviewController = loader.getController();
            collectionOverviewController.setModel(overviewModel);
            rootLayout.setCenter(collectionOverview);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(UserPreferences.localeResources);
            loader.setLocation(AppLauncher.class.getResource("/com/dartin/project/gui/resources/view/rootLayout.fxml"));
            rootLayout = loader.load();
            rootLayoutController = loader.getController();

            Scene scene = new Scene(rootLayout, 600, 350);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNewItem(TreeItem<?> selection) {
        System.out.println("New Item");
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource("/com/dartin/project/gui/resources/view/newItem.fxml"));
            loader.setResources(UserPreferences.localeResources);
            AnchorPane layout = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(layout);

            NewItemController controller = loader.getController();
            NewItemModel model = new NewItemModel(this);
            controller.setModel(model);
            controller.loadFields(convertItem(overviewModel.convertTreeItem(selection)));

            stage.setScene(scene);
            controller.setStage(stage);

            stage.showAndWait();
            if (controller.isOkClicked()) {
                Item item = model.getItem();
                this.collectionOverviewController.setTreeRoot(CollectionOverviewModel.convertSetToRoot(RequestManager.sendRequest(item, ip, true)));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showNewItem() {
        showNewItem(null);
    }

    public Map<String, Object> convertItem(Item item) {
        Map<String, Object> map = new HashMap<>();

        map.put("name", item.name());
        map.put("usage", item.usage());
        map.put("size", item.size().toString());
        map.put("date", item.date());
        return map;
    }

    public Item convertMap(Map<String, Object> map) {
        return new Item(
                (String) map.get("name"),
                (String) map.get("usage"),
                Item.Size.valueOf(map.get("size").toString().toUpperCase()),
                LocalDate.now()
        );
    }

    public Item getSelectedItem() {
        return overviewModel.getSelectedItem();
    }

    public TreeItem<Object> convertTreeItem(Item item) {
        TreeItem<Object> treeItem = new TreeItem<>(item.name());
        treeItem.getChildren().add(new TreeItem<>(item.usage()));
        treeItem.getChildren().add(new TreeItem<>(item.size()));
        treeItem.getChildren().add(new TreeItem<>(item.date()));
        return treeItem;
    }

    public void setNewRoot(TreeItem<Object> items) {
        collectionOverviewController.setNewRoot(items);
    }

    public static void redraw() {
        initLayout();
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(UserPreferences.localeResources);
        loader.setLocation(AppLauncher.class.getResource("/com/dartin/project/gui/resources/view/collectionOverview.fxml"));
        AnchorPane collectionOverview = null;
        try {
            collectionOverview = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        collectionOverviewController = loader.getController();
        collectionOverviewController.setModel(overviewModel);
        rootLayout.setCenter(collectionOverview);

    }

}