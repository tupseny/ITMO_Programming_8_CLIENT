package com.dartin.project.net;

import com.dartin.project.exception.NotValidMessageException;
import com.dartin.project.gui.CollectionOverviewModel;

import java.io.IOException;

/**
 * Created by Martin on 25.06.2017.
 */
public class CollectionWaiter implements Runnable{
    private CollectionOverviewModel model;

    public CollectionWaiter(CollectionOverviewModel model){
        this.model = model;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread() + " started!");
        MessageReceiver receiver = new MessageReceiver();

        try {
            receiver.connect();
            System.out.println("listen message");
            ServerMessage message = receiver.deserialize(receiver.listen());
            System.out.println("Message got: " + message.getName());
            model.setNewCollection(MessageManager.recogniseMessage(message));
        } catch (IOException | ClassNotFoundException | NotValidMessageException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread() + " stopped!");
    }

}
