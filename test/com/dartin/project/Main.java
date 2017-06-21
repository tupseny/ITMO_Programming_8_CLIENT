package com.dartin.project;

import java.net.*;

public class Main {

	public static void main(String[] args) throws Exception {

		System.out.println(InetAddress.getLocalHost().getHostName());

		SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName("176.59.19.187"), 5555);
		DatagramSocket datagramSocket = new DatagramSocket();
		byte[] bytearray = {0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5};
		datagramSocket.send(new DatagramPacket(bytearray, 11, socketAddress));
		//CollectionOverviewWindow.main(args);
	}

}
