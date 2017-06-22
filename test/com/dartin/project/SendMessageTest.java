package com.dartin.project;

import com.dartin.project.net.MessageSender;
import com.dartin.project.net.ServerMessage;

/**
 * Created by Martin on 22.06.2017.
 */
public class SendMessageTest {
    public static void main(String[] args) {
        ServerMessage msg = new ServerMessage("Hello Danik!", 5);
        String ip = "176.59.10.20";
        int port = 5554;


        new Thread(new MessageSender(msg, ip, port)).start();
    }
}
