package com.dartin.project.net;

import com.dartin.net.ServerMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

@SuppressWarnings("FieldCanBeLocal")
public class MessageReceiver{

    private volatile DatagramSocket socket;
    private static int messageLength = ServerMessage.MESSAGE_LENGTH;

    public void connect(int port, int timeout) throws SocketException {
        socket = new DatagramSocket(port);
        socket.setSoTimeout(timeout);
    }

    public void connect(int timeout) throws SocketException {
        connect(5555, timeout);
    }

    public DatagramPacket listen() throws IOException {
        byte[] receivedByteArray = new byte[messageLength];
        DatagramPacket receivedPacket = new DatagramPacket(receivedByteArray, messageLength);

        System.out.println("Listening...");
        try{
            socket.receive(receivedPacket);
        }finally {
            System.out.println("Socket closed.\n");
            socket.close();
        }
        return receivedPacket;
    }
}
