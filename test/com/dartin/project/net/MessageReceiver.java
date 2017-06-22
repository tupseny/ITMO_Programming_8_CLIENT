package com.dartin.project.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Arrays;

public class MessageReceiver {

	private volatile DatagramSocket socket;
	private static int messageLength = 1024;
	private static int stopByte = 13;

	public void connect() throws SocketException {
		SocketAddress address = new InetSocketAddress(5555);
		DatagramSocket datagramSocket = new DatagramSocket();
		datagramSocket.connect(address);
	}

	public void listen() throws IOException {
		byte[] receivedByteArray = new byte[messageLength];
		DatagramPacket receivedPacket =
				new DatagramPacket(receivedByteArray, messageLength);

		socket.receive(receivedPacket);
	}

	private ServerMessage deserialize(DatagramPacket packet) throws IOException, ClassNotFoundException{
		byte[] data = packet.getData();
		for (int i = messageLength - 1; i >= 0; i--) {
			if (i == stopByte) {
				data = Arrays.copyOf(data, i);
				break;
			}
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(bis);
		return (ServerMessage) ois.readObject();
	}
}
