package com.dartin.project;

import com.dartin.project.gui.CollectionOverviewWindow;
import com.dartin.project.gui.NewItemWindow;
import com.dartin.util.Item;

public class AppLauncher {

	private static String[] argsCalled;

	public static void main(String[] args) {
		argsCalled = args;
		CollectionOverviewWindow.main(argsCalled);
	}

	public static void callNewItemWindow() {
		NewItemWindow.main(argsCalled);
	}

	public static void manageSetUpdate(String name, String usage, String size,
	                     int action) {


		Item itemToSend = new Item(name, usage, size);
//		ServerMessage updateSetMessage = new ServerMessage(
//				ClientData.getInstance().getClientId(),
//				ServerMessage.SIG_UPD_SET,
//
//		)
	}

	public static void manageServerData() {

	}

}