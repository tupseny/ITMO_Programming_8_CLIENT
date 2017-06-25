package com.dartin.project;

import com.dartin.project.net.MessageReceiver;

import java.io.IOException;

/**
 * Created by Martin on 22.06.2017.
 */
public class RecieveMessageTest {
    public static void main(String[] args) throws IOException {
//        byte[] b = new byte[1024];
//        DatagramPacket packet = new DatagramPacket(b, b.length);
//        DatagramSocket socket = new DatagramSocket(5555);
//
//        socket.receive(packet);
//        System.out.println(packet.getData());
        MessageReceiver receiver = new MessageReceiver();
        receiver.connect();
        System.out.println(receiver.listen().getData());
    }
}
