package com.dartin.project.util;

import com.daniily.util.Tree;
import com.dartin.util.Item;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.Iterator;
import java.util.Set;

public class UniversalConverter {

    public static <E> TreeView<E> treeToTreeView(Tree<E> tree) {
        TreeItem<E> root = passEveryone(tree.getRoot());
        return new TreeView<>(root);
    }

    @SuppressWarnings("unchecked")
    private static <E> TreeItem<E> passEveryone(Tree.Node currentNode) {
        if (currentNode.isLeaf())
            return new TreeItem<E>((E) currentNode.getValue());
        TreeItem<E> currentItem = new TreeItem<E>((E) currentNode.getValue());
        currentNode.getChildren().forEach(o -> currentItem.getChildren().add(passEveryone((Tree.Node) o)));
        return currentItem;
    }

    public static Tree<String> itemSetToTree(Set<Item> set) {
        Tree<String> tree = new Tree<>("Item tree");
        Iterator<Item> iterator = set.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Item current = iterator.next();
            tree.add(current.name());
            tree.add(current.usage(), i);
            tree.add(current.size().toString(), i);
            i++;
        }
        return tree;
    }
}
