package com.dartin.project.net;

import com.dartin.net.ServerMessage;
import com.dartin.util.Item;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Martin on 25.06.2017.
 */
public class RequestManager{

    public static Object requestCollection(String ip, int port, int timeout) {
        MessageSender sender = new MessageSender(
                new ServerMessage(ServerMessage.CMD_RUN), ip, port);

        new Thread(sender).run();
        try {
            return listen(5554, timeout, ServerMessage.CONTENT_SET);
        } catch (IOException e) {
            System.out.println("Server is not responsing");
            return null;
        }
    }

    public static Object requestCollection(String ip){
        requestCollection(ip, 5555, 60000);
    }

    public static boolean checkConnection(String ip, int port, int timeout){
        ServerMessage checkMessage = new ServerMessage(ServerMessage.CMD_VERIFY);
        checkMessage.addContent(ServerMessage.CONTENT_VER, new AtomicInteger(Item.VERSION));

       new Thread(new MessageSender(checkMessage, ip, port)).run();

        try {
            if ((boolean) listen(5554, timeout, ServerMessage.CONTENT_LOG)){
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
        checkConnection(ip, 5555, 60000);
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
