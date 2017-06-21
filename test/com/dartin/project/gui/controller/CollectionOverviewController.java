package com.dartin.project.gui.controller;

import com.dartin.project.gui.CollectionOverviewWindow;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeView;

public class CollectionOverviewController {

	public TreeView treeView;
	public ToggleButton toggleButtonName;
	public ToggleButton toggleButtonSize;
	public ToggleButton toggleButtonUsage;
	public TextField textFieldName;
	public TextField textFieldUsage;
	public TextField textFieldSize;
	public Button buttonNew;
	public Button buttonEdit;
	public Button buttonDelete;

	public static final int SET_ADD = 0;
	public static final int SET_REMOVE = 1;
	public static final int SET_REMOVE_LOWER = 2;

	private CollectionOverviewWindow controllingApplication;

	public void handleToggleButtonName(ActionEvent actionEvent) {
	}

	public void handleToggleButtonUsage(ActionEvent actionEvent) {
	}

	public void handleToggleButtonSize(ActionEvent actionEvent) {
	}

	public void handleButtonNew(ActionEvent actionEvent) {
		controllingApplication.manageSetUpdRequest(SET_ADD);
	}

	public void handleButtonEdit(ActionEvent actionEvent) {
	}

	public void handleButtonDelete(ActionEvent actionEvent) {
	}

	public void handleStartStoryButton(ActionEvent actionEvent) {
	}

	public void setTreeView(TreeView newTreeView) {
		treeView = newTreeView;
		treeView.refresh();
	}

	public void setControllingApplication(CollectionOverviewWindow controllingApplication) {
		this.controllingApplication = controllingApplication;
	}
}