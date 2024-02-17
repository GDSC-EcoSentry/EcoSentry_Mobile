package com.observers.ecosentry_mobile.controllers.drawer.fragments.dashboard;

import com.observers.ecosentry_mobile.models.node.Node;
import com.observers.ecosentry_mobile.models.station.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

@FunctionalInterface
public interface DataFetchCallback {
    void onDataFetched(TreeMap nodes);
}