package com.dartin.project.gui.controller;

import com.daniily.util.Tree;
import com.dartin.project.AppLauncher;
import com.dartin.project.exception.TreeDuplicateException;
import com.dartin.project.gui.CollectionOverviewModel;
import com.dartin.project.net.RequestManager;
import com.dartin.project.util.UniversalConverter;
import com.dartin.util.Item;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionOverviewController {

    @FXML
    private TreeView treeView;
    @FXML
    private ToggleButton toggleButtonName;
    @FXML
    private ToggleButton toggleButtonSize;
    @FXML
    private ToggleButton toggleButtonUsage;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldUsage;
    @FXML
    private TextField textFieldSize;
    @FXML
    private Button buttonNew;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;
    @FXML
    private Text statusText;

    private CollectionOverviewModel model;
    private TreeItem<Object> selectedTreeItem;
    private List<ObservableList<TreeItem<Object>>> stackList = new ArrayList<>();
    private boolean waitCollection = false;

    @FXML
    private void handleToggleButtonName() {
        if (toggleButtonName.isSelected())
            filterItems("name", textFieldName.getText());
        else unfilterItems();
    }

    @FXML
    private void handleToggleButtonUsage() {
        //TODO: complete filters
    }

    @FXML
    private void handleToggleButtonSize() {
    }

    @FXML
    private void statusTextClicked() {
        System.out.println("\n------------------------\nStatus Text clicked");
        if (!waitCollection) {
            System.out.println("Collection requested!");
            setStatus("Collection requested!\n Please wait...", "yellow");
            model.getNewRoot();
            setWaitCollection(false);
            return;
        }
        System.out.println("Wait collection after is " + waitCollection);
        System.out.println("!!!Request sent! Please wait...");
        setStatus("Request sent!\n Please wait...", "red");
    }

    @FXML
    private void handleButtonNew() {
        model.showNewItem();
    }

    @FXML
    private void handleButtonEdit() {
        model.showNewItem(selectedTreeItem);
    }

    @FXML
    private void handleButtonDelete() {
        deleteItem(selectedTreeItem);
    }

    @FXML
    private void handleStartStoryButton() {
        model.startStory();
    }

    @FXML
    private void initialize() {
        treeView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setSelectedItem((TreeItem<Object>) newValue));
        initTree();
        treeView.getSelectionModel().select(treeView.getRoot().getChildren().get(0));
        treeView.getRoot().setExpanded(true);
        treeView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleSelection(newValue));

        statusText.setFont(Font.font("Arial", 24));
        setStatus("Wait for collection...", "yellow");
    }

    private void handleSelection(Object newValue) {
        if (treeView.getRoot().getChildren().contains(newValue)) {
            setDisableButtons(false, false, false);
        } else {
            setDisableButtons(false, true, true);
        }
    }

    private void setSelectedItem(TreeItem<Object> selection) {
        selectedTreeItem = selection;
    }

    public TreeItem<Object> getSelectedTreeItem() {
        return selectedTreeItem;
    }

    public void setTreeView(Tree<?> tree) {
        treeView.setRoot(UniversalConverter.treeToTreeView(tree).getRoot());
        treeView.refresh();
    }

    public void setModel(CollectionOverviewModel model) {
        this.model = model;
    }

    public void initTree() {
        Set<Item> items = new HashSet<>();
        items.add(new Item("name1", "usage1", Item.Size.HUGE, LocalDate.now()));
        items.add(new Item("name2", "usage2", Item.Size.TINY, LocalDate.now()));


        Tree<String> tree = UniversalConverter.itemSetToTree(items);
        setTreeView(tree);
    }

    public void addTreeItem(TreeItem<Object> treeItem) throws TreeDuplicateException {
        System.out.println(treeItem);
        System.out.println(treeView.getRoot().getChildren().toString());
        if (!treeView.getRoot().getChildren().contains((Object) treeItem)) {
            treeView.getRoot().getChildren().add(treeItem);
        } else {
            throw new TreeDuplicateException("Tree consists " + treeItem.toString());
        }
    }

    public void setDisableButtons(boolean disableNew, boolean disableEdit, boolean disableDelete) {
        buttonDelete.setDisable(disableDelete);
        buttonEdit.setDisable(disableEdit);
        buttonNew.setDisable(disableNew);
    }

    public void setDisableButtons(boolean disable) {
        setDisableButtons(disable, disable, disable);
    }

    private void deleteItem(TreeItem<Object> item) {
        int selIndex = treeView.getRoot().getChildren().indexOf(
                treeView.getSelectionModel().getSelectedItem());
//        treeView.getRoot().getChildren().remove(item);
        setTreeRoot(CollectionOverviewModel.convertSetToRoot(
                RequestManager.sendRequest(model.convertTreeItem(item), AppLauncher.getIp(), false)
        ));
        if (treeView.getRoot().getChildren().isEmpty()) {
            setDisableButtons(false, true, true);
        } else {
            treeView.getSelectionModel().select(selIndex + 1);
        }

    }

    private void filterItems(String filter, String value) {
        stackList.add(treeView.getRoot().getChildren());

        setTreeRoot(model.filterList((ObservableList) stackList.get(stackList.size() - 1), filter, value));
    }

    private void unfilterItems() {
        setTreeRoot(stackList.get(stackList.size() - 1));
    }

    public void setTreeRoot(TreeItem<Object> treeItem) {
        System.out.println("Setting new Root: " + treeItem.getChildren().toString());
        if (treeItem != null) {
            treeView.setRoot(treeItem);
            treeView.getRoot().setExpanded(true);
        } else {
            treeView.setRoot(new TreeItem<Object>("null"));
        }

    }

    public void setTreeRoot(ObservableList<TreeItem<Object>> treeItemList) {
        TreeItem<Object> root = new TreeItem<>("Item tree");
        root.getChildren().setAll(treeItemList);
        setTreeRoot(root);
    }

    public void setStatus(String text, String color) {
        statusText.setFill(Paint.valueOf(color));
        statusText.setText(text);
    }

    public void setNewRoot(TreeItem<Object> treeItem) {
        if (treeItem == null) {
            setStatus("Collection loading error!\n" +
                    "RESEND REQUEST", "red");
        } else {
            System.out.println("Change status to succeded");
            setStatus("Collection load succeeded\n" +
                    "UPDATE COLLECTION", "green");
            setTreeRoot(treeItem);
        }
        setWaitCollection(false);
    }

    public void setWaitCollection(boolean value) {
        this.waitCollection = value;
    }

    public boolean getWaitCollection() {
        return this.waitCollection;
    }
}