package com.dartin.project.gui;

import com.daniily.util.Tree;
import com.dartin.project.AppLauncher;
import com.dartin.project.exception.NotValidMessageException;
import com.dartin.project.net.CollectionWaiter;
import com.dartin.project.net.MessageManager;
import com.dartin.project.net.MessageReceiver;
import com.dartin.project.net.ServerMessage;
import com.dartin.project.util.UniversalConverter;
import com.dartin.util.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Martin on 21.06.2017.
 */
public class CollectionOverviewModel{
    private AppLauncher app;
    private Item selectedItem;

    public CollectionOverviewModel(AppLauncher app){
        this.app = app;
    }

    public void showNewItem(TreeItem<? extends Object> selection) {
        app.showNewItem(selection);
    }

    public void showNewItem(){
        app.showNewItem();
    }

    public void setSelectedTreeItem(TreeItem<String> selectedTreeItem) {
        this.selectedItem = new Item(selectedTreeItem.getValue(),
                selectedTreeItem.getChildren().get(0).getValue(),
                Item.Size.valueOf(selectedTreeItem.getChildren().get(1).getValue().toUpperCase()),
                LocalDate.now());
    }

    public Item getSelectedItem(){
        return selectedItem;
    }

    public Item convertTreeItem(TreeItem<? extends Object> item){
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

    public ObservableList<TreeItem<Object>> filterList(ObservableList<TreeItem<Object>> list, String filter, String value){
        Stream<TreeItem<Object>> stream = Stream.empty();

        System.out.println(list.toString());

        switch (filter){
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

        ObservableList<TreeItem<Object>> temp = stream.collect(Collectors.toCollection(FXCollections::observableArrayList));
        return temp;
    }

    public void getNewRoot(){
        new Thread(new CollectionWaiter(this)).start();
    }

    private TreeItem<Object> convertSetToRoot(Set<Item> items){
        TreeItem<Object> root = new TreeItem<>("Item's tree");
        if (items!=null) {
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

    public void setNewCollection(Set<Item> items) {
        app.setNewRoot(convertSetToRoot(items));
    }
}
