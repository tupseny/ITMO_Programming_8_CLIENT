package com.dartin.project;

import com.dartin.project.net.MessageSender;
import com.dartin.project.net.ServerMessage;
import com.dartin.util.Item;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Martin on 22.06.2017.
 */
public class SendMessageTest {
    public static void main(String[] args) {
        ServerMessage msg = new ServerMessage("Hello Danik!", 1);
        String ip = "178.248.140.168";
        int port = 5555;


        new Thread(new MessageSender(msg, ip, port)).start();
    }
}
