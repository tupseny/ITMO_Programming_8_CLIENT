package com.dartin.project.net;

import com.dartin.net.ServerMessage;
import com.dartin.project.exception.NotValidMessageException;
import com.dartin.project.gui.CollectionOverviewModel;

import java.io.IOException;

/**
 * Created by Martin on 25.06.2017.
 */
public class CollectionWaiter implements Runnable {
    private CollectionOverviewModel model;

    public CollectionWaiter(CollectionOverviewModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread() + " started!");
        MessageReceiver receiver = new MessageReceiver();

        try {
            receiver.connect(600000);
            System.out.println("Listen message...");
//            ServerMessage message = receiver.deserialize(receiver.listen());
//            System.out.println("Message got: " + message.getName());
            Object msg = ServerMessage.recover(receiver.listen().getData()).getContent("string");
            System.out.println(msg);
//            model.setNewCollection(MessageManager.recogniseMessage(message));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread() + " stopped!");
    }

}
