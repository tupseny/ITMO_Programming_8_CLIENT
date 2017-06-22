package com.dartin.project.gui.controller;

import com.daniily.util.Tree;
import com.dartin.project.exception.TreeDuplicateException;
import com.dartin.project.gui.CollectionOverviewModel;
import com.dartin.project.util.UniversalConverter;
import com.dartin.util.Item;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionOverviewController{

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

	private CollectionOverviewModel model;
	private TreeItem<Object> selectedTreeItem;
	private List<ObservableList<TreeItem<Object>>> stackList = new ArrayList<>();

	@FXML
	private void handleToggleButtonName(ActionEvent actionEvent) {
		if (toggleButtonName.isSelected())
		filterItems("name", textFieldName.getText());
		else unfilterItems();
	}

	@FXML
	private void handleToggleButtonUsage(ActionEvent actionEvent) {
		//TODO: complete filters
	}

	@FXML
	private void handleToggleButtonSize(ActionEvent actionEvent) {
	}

	@FXML
	private void handleButtonNew(ActionEvent actionEvent) {
		model.showNewItem();
	}

	@FXML
	private void handleButtonEdit(ActionEvent actionEvent) {
		model.showNewItem(selectedTreeItem);
	}

	@FXML
	private void handleButtonDelete(ActionEvent actionEvent) {
		deleteItem(selectedTreeItem);
	}

	@FXML
	private void handleStartStoryButton(ActionEvent actionEvent) {}

	@FXML
	private void initialize(){
		treeView.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> setSelectedItem((TreeItem<Object>) newValue));
		initTree();
		treeView.getSelectionModel().select(treeView.getRoot().getChildren().get(0));
		treeView.getRoot().setExpanded(true);
		treeView.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> handleSelection(newValue));
	}

	private void handleSelection(Object newValue) {
		if (treeView.getRoot().getChildren().contains(newValue)){
			setDisableButtons(false, false, false);
		}else{
			setDisableButtons(false, true, true);
		}
	}

	private void setSelectedItem(TreeItem<Object> selection){
		selectedTreeItem = selection;
	}

	public TreeItem<Object> getSelectedTreeItem(){
		return selectedTreeItem;
	}

	public void setTreeView(Tree<? extends Object> tree) {
		treeView.setRoot(UniversalConverter.treeToTreeView(tree).getRoot());
		treeView.refresh();
	}

	public void setModel(CollectionOverviewModel model){
		this.model = model;
	}

	public void initTree(){
		Set<Item> items = new HashSet<>();
		items.add(new Item("name1", "usage1", Item.Size.HUGE, LocalDate.now()));
		items.add(new Item("name2", "usage2", Item.Size.TINY, LocalDate.now()));


		Tree<String> tree = UniversalConverter.itemSetToTree(items);
		setTreeView(tree);
	}

	public void addTreeItem(TreeItem<Object> treeItem) throws TreeDuplicateException {
		System.out.println(treeItem);
		System.out.println(treeView.getRoot().getChildren().toString());
		if (!treeView.getRoot().getChildren().contains((Object) treeItem)){
			treeView.getRoot().getChildren().add(treeItem);
			System.out.println("not consists");
		}else{
			System.out.println("consists");
			throw new TreeDuplicateException("Tree consists " + treeItem.toString());
		}
	}

	public void setDisableButtons(boolean disableNew, boolean disableEdit, boolean disableDelete){
		buttonDelete.setDisable(disableDelete);
		buttonEdit.setDisable(disableEdit);
		buttonNew.setDisable(disableNew);
	}

	public void setDisableButtons(boolean disable){
		setDisableButtons(disable, disable, disable);
	}

	private void deleteItem(TreeItem<Object> item){
		int selIndex = treeView.getRoot().getChildren().indexOf(
				treeView.getSelectionModel().getSelectedItem());
		treeView.getRoot().getChildren().remove(item);
		if (treeView.getRoot().getChildren().isEmpty()){
			setDisableButtons(false, true, true);
		}else{
			treeView.getSelectionModel().select(selIndex+1);
		}

	}

	private void filterItems(String filter, String value){
		stackList.add(treeView.getRoot().getChildren());

		setTreeRoot(model.filterList((ObservableList) stackList.get(stackList.size() - 1), filter, value));
	}

	private void unfilterItems(){
		setTreeRoot(stackList.get(stackList.size()-1));
	}

	private void setTreeRoot(TreeItem<Object> treeItem){
		treeView.setRoot(treeItem);
		treeView.getRoot().setExpanded(true);
	}

	private void setTreeRoot(ObservableList<TreeItem<Object>> treeItemList){
		TreeItem<Object> root = new TreeItem<>("Item tree");
		root.getChildren().setAll(treeItemList);
		setTreeRoot(root);
	}
}