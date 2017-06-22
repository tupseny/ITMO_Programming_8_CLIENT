package com.dartin.project.gui;

import com.daniily.util.Tree;
import com.dartin.project.AppLauncher;
import com.dartin.project.util.UniversalConverter;
import com.dartin.util.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

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
}
