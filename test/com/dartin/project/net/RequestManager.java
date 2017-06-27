package com.dartin.project.net;

import com.dartin.net.ServerMessage;
import com.dartin.util.Item;

import java.io.IOException;
import java.net.*;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Martin on 25.06.2017.
 */
public class RequestManager{

    public static Set<Item> sendRequest(Item item, String ip, int port, int timeout, boolean signal){
        ServerMessage message;
        try {
            if (signal) {
                message = new ServerMessage(ServerMessage.CMD_ADD);
            }else{
                message = new ServerMessage(ServerMessage.CMD_REMOVE);
            }
            message.addContent(ServerMessage.CONTENT_SET, item);
            message.lock();
            MessageSender sender = new MessageSender(message.toBytes(), ip, port);
            new Thread(sender).run();
            return (Set<Item>) listen(5554, timeout, ServerMessage.CONTENT_SET);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Set<Item> sendRequest(Item item, String ip, boolean signal){
        return sendRequest(item, ip, 5555, 6000, signal);
    }

    public static Object requestCollection(String ip, int port, int timeout) throws IOException {
        System.out.println("Request collection from server");
        ServerMessage message = new ServerMessage(ServerMessage.CMD_RUN);
        message.addContent(ServerMessage.CONTENT_SET, "");
        message.lock();
        MessageSender sender = new MessageSender(message.toBytes(), ip, port);

        new Thread(sender).run();
        try {
            return listen(5554, timeout, ServerMessage.CONTENT_SET);
        } catch (IOException e) {
            System.out.println("Server is not responsing");
            return null;
        }
    }

    public static Object requestCollection(String ip) throws IOException {
        return requestCollection(ip, 5555, 60000);
    }

    public static boolean checkConnection(String ip, int port, int timeout) throws IOException {
        ServerMessage checkMessage = new ServerMessage(ServerMessage.CMD_VERIFY);
        checkMessage.addContent(ServerMessage.CONTENT_VER, new AtomicInteger(Item.VERSION));

       new Thread(new MessageSender(checkMessage.toBytes(), ip, port)).run();

        try {
            if (((AtomicBoolean) listen(5554, timeout, ServerMessage.CONTENT_VER)).get()){
                System.out.println("Server established\n");
                return true;
            }
        } catch (SocketTimeoutException e){
            System.out.println("Server is not responsing");
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkConnection(String ip){
        try {
            return checkConnection(ip, 5555, 60000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Object listen(int port, int timeout, String key) throws IOException {
        MessageReceiver receiver = new MessageReceiver();
        try {
            receiver.connect(port, timeout);
            return ServerMessage.recover(receiver.listen().getData())
                        .getContent(key);
        } catch (SocketException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;

        }
    }
}
