package com.dartin.project;

import com.daniily.util.Tree;
import com.dartin.project.net.MessageSender;
import com.dartin.project.net.ServerMessage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.dartin.project.util.UniversalConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;


public class TestApp {
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i =0; i<10; i++){
			list.add(i);
		}
		System.out.println(list);

		Stream<Integer> newList = list.stream().filter(integer -> integer > 5);
		newList.forEach(integer -> System.out.println(integer));
	}
}
