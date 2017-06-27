package com.dartin.project.gui;

import com.dartin.project.AppLauncher;
import com.dartin.project.net.CollectionWaiter;
import com.dartin.project.net.RequestManager;
import com.dartin.util.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Martin on 21.06.2017.
 */
public class CollectionOverviewModel {
    private AppLauncher app;
    private Item selectedItem;
    private String ip;

    public CollectionOverviewModel(AppLauncher app, String ip) {
        this.app = app;
        this.ip = ip;
    }

    public void showNewItem(TreeItem<?> selection) {
        app.showNewItem(selection);
    }

    public void showNewItem() {
        app.showNewItem();
    }

    public void setSelectedTreeItem(TreeItem<String> selectedTreeItem) {
        this.selectedItem = new Item(selectedTreeItem.getValue(),
                selectedTreeItem.getChildren().get(0).getValue(),
                Item.Size.valueOf(selectedTreeItem.getChildren().get(1).getValue().toUpperCase()),
                LocalDate.now());
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public Item convertTreeItem(TreeItem<?> item) {
        if (item != null) {
            return new Item(
                    (String) item.getValue(),
                    (String) item.getChildren().get(0).getValue(),
                    Item.Size.valueOf(item.getChildren().get(1).getValue().toString().toUpperCase()),
                    LocalDate.now()
            );
        }

        return new Item();
    }

    public ObservableList<TreeItem<Object>> filterList(ObservableList<TreeItem<Object>> list, String filter, String value) {
        Stream<TreeItem<Object>> stream = Stream.empty();

        System.out.println(list.toString());

        switch (filter) {
            case "name":
                stream = list.stream()
                        .filter(objectTreeItem -> objectTreeItem.getValue().equals(value));
                break;
            case "usage":
                stream = list.stream()
                        .filter(objectTreeItem -> objectTreeItem.getChildren().get(0).getValue().equals(value));
                break;
            case "size":
                stream = list.stream()
                        .filter(objectTreeItem -> objectTreeItem.getChildren().get(1).getValue().equals(value));
                break;
            case "date":
                stream = list.stream()
                        .filter(objectTreeItem -> objectTreeItem.getChildren().get(2).getValue().equals(value));
        }

        return stream.collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public void getNewRoot() {
        if (RequestManager.checkConnection(ip)) {
            try {
                app.setNewRoot(convertSetToRoot((Set<Item>) RequestManager.requestCollection(ip)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //TODO: Stopped here!

        }
        app.setNewRoot(null);
    }

    public static TreeItem<Object> convertSetToRoot(Set<Item> items) {
        TreeItem<Object> root = new TreeItem<>("Item's tree");
        if (items != null) {
            items.forEach(item -> {
                TreeItem<Object> treeItem = new TreeItem<>(item.name());
                treeItem.getChildren().add(new TreeItem<>(item.usage()));
                treeItem.getChildren().add(new TreeItem<>(item.size()));
                treeItem.getChildren().add(new TreeItem<>(item.date()));
                root.getChildren().add(treeItem);
            });
        }
        return root;
    }

    public void setNewCollection(Map<String, Object> items) {

        System.out.println(items);
//        app.setNewRoot(convertSetToRoot(items));
    }
}
