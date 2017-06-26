package com.dartin.project;

import com.dartin.net.ServerMessage;
import com.dartin.project.net.MessageReceiver;
import com.dartin.project.net.MessageSender;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.stream.Stream;


public class TestApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerMessage message = new ServerMessage(ServerMessage.CMD_RUN);
        message.lock();
        MessageSender sender = new MessageSender(message.toBytes(), "192.168.1.45", 5555);
        sender.run();

        MessageReceiver receiver = new MessageReceiver();
        receiver.connect(5555, 60000);
//        System.out.println(
//                (String) ServerMessage.recover(receiver.listen().getData()).getContent(ServerMessage.CONTENT_LOG)
//        );
    }
}
