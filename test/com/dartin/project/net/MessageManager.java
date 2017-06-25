package com.dartin.project.net;

import com.dartin.project.exception.NotValidMessageException;
import com.dartin.util.Item;

import java.util.Set;

/**
 * Created by Martin on 25.06.2017.
 */
public class MessageManager {
    private static final int COLLECTION_UPDATE = 1;

    public static Set<Item> recogniseMessage(ServerMessage message) throws NotValidMessageException {
        int signal = message.getSignal();
        if (signal == COLLECTION_UPDATE){
            return (Set<Item>) message.getContent();
        }

        throw new NotValidMessageException("Cant recognise signal " + signal);
    }
}
