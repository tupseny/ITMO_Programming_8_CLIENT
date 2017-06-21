package com.dartin.project;

import com.daniily.util.Tree;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.dartin.project.util.UniversalConverter;


public class TestApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		VBox root = new VBox();

		String a = "root";
		String b = "root-leave1";
		String c = "root-leave2";
		String d = "root-leave1-a";
		String e = "root-leave1-b";

		Tree<String> luckyTree = new Tree<>(a);
		luckyTree.add(b);
		luckyTree.add(c);
		luckyTree.add(d, 0);
		luckyTree.add(e, 0);

		TreeView<String> treeView = UniversalConverter.treeToTreeView(luckyTree);

		root.getChildren().add(treeView);

		Scene scene = new Scene(root,400,400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("TreeView Example 1");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
