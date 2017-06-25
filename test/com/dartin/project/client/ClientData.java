package com.dartin.project.client;

import com.daniily.util.Tree;
import com.dartin.project.util.OnClientCollectionModifiedListener;
import com.dartin.project.util.UniversalConverter;
import com.dartin.util.Item;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ClientData {

    private Set<Item> collectionCopy;
    private OnClientCollectionModifiedListener onClientCollectionModifiedListener;
    public static final int CLIENT_ID = LocalDateTime.now().hashCode();

    private static ClientData ourInstance = new ClientData();

    public static ClientData getInstance() {
        return ourInstance;
    }

    private ClientData() {
        collectionCopy = new HashSet<>();
        collectionCopy.add(new Item("name", "usage", "small"));
    }

    public int getClientId() {
        return CLIENT_ID;
    }

    public OnClientCollectionModifiedListener getOnClientCollectionModifiedListener() {
        return onClientCollectionModifiedListener;
    }

    public void setOnClientCollectionModifiedListener
            (OnClientCollectionModifiedListener listener) {
        onClientCollectionModifiedListener = listener;
    }

    public Set<Item> getCollectionCopy() {
        return collectionCopy;
    }

    public Tree<String> getTreeCollectionCopy() {
        return UniversalConverter.itemSetToTree(collectionCopy);
    }

    public void setCollectionCopy(Set<Item> newCopy) {
        collectionCopy = newCopy;
        if (onClientCollectionModifiedListener != null) {
            onClientCollectionModifiedListener.onHandle(collectionCopy);
        }
    }
}
