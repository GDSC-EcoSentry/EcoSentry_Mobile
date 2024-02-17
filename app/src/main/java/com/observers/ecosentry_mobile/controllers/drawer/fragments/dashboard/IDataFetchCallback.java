package com.observers.ecosentry_mobile.controllers.drawer.fragments.dashboard;

import java.util.TreeMap;

@FunctionalInterface
public interface IDataFetchCallback {
    void onDataFetched(TreeMap nodes);
}