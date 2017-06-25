package com.dartin.project.util;

import java.util.Collection;

@FunctionalInterface
public interface OnClientCollectionModifiedListener {
    void onHandle(Collection collection);
}
