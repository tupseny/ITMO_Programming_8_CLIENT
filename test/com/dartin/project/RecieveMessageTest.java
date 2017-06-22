package com.dartin.project;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Martin on 22.06.2017.
 */
public class RecieveMessageTest {
    public static void main(String[] args) throws IOException {
        byte[] b = new byte[1024];
        DatagramPacket packet = new DatagramPacket(b, b.length);
        DatagramSocket socket = new DatagramSocket(5554);

        socket.receive(packet);
        System.out.println(packet.getData());
    }
}
