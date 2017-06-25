package com.dartin.project.net;

import javafx.application.Application;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Arrays;

public class MessageReceiver{

	private volatile DatagramSocket socket;
	private static int messageLength = 1024;
	private static int stopByte = 13;

	public void connect(int port) throws SocketException {
		socket = new DatagramSocket(port);
	}

	public void connect() throws SocketException {
		connect(5555);
	}

	public DatagramPacket listen() throws IOException {
		byte[] receivedByteArray = new byte[messageLength];
		DatagramPacket receivedPacket =
				new DatagramPacket(receivedByteArray, messageLength);

        System.out.println("Listening... on " +
                InetAddress.getLocalHost().getHostAddress());
        socket.receive(receivedPacket);
        System.out.println("Got message");
        socket.close();
        return receivedPacket;
	}

    public ServerMessage deserialize(DatagramPacket packet) throws IOException, ClassNotFoundException{
		byte[] data = packet.getData();
        System.out.println(data);
//        for (int i = messageLength - 1; i >= 0; i--) {
//			if (i == stopByte) {
//				data = Arrays.copyOf(data, i);
//				break;
//			}
//		}
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(bis);
		return (ServerMessage) ois.readObject();
	}
}
