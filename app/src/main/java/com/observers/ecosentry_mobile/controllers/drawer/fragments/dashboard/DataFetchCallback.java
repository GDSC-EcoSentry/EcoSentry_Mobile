package com.observers.ecosentry_mobile.controllers.drawer.fragments.dashboard;

import com.observers.ecosentry_mobile.models.node.Node;

import java.util.ArrayList;

@FunctionalInterface
public interface DataFetchCallback {
    void onDataFetched(ArrayList<Node> nodes);
}