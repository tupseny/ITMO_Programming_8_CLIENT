package com.dartin.project.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Arrays;

/**
 * Created by Asus Strix on 15.06.2017.
 */
public class MessageSender {

	public static void send(ServerMessage message) {

		try {
			SocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 5555);
			DatagramSocket datagramSocket = new DatagramSocket();
			datagramSocket.send(formDatagramPacket(message, socketAddress));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("sending failed");
		}
	}

	public static DatagramPacket formDatagramPacket(ServerMessage message, SocketAddress address)
	throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(message);
		byte[] serializedMessage = bos.toByteArray();
		System.out.println("length " + serializedMessage.length + ": " + Arrays.toString(serializedMessage));
		return new DatagramPacket(serializedMessage, serializedMessage.length, address);
	}

}
