package com.observers.ecosentry_mobile.controllers.drawer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.observers.ecosentry_mobile.R;
import com.observers.ecosentry_mobile.models.node.Node;
import com.observers.ecosentry_mobile.models.node.NodeAdapter;

import java.util.ArrayList;
import java.util.Random;

public class DashboardFragment extends Fragment {

    // ================================
    // == Fields
    // ================================
    private RecyclerView mRecyclerView;
    private NodeAdapter mNodeAdapter;

    // ================================
    // == Life Cycle
    // ================================
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Fetching Data from Firebase
        ArrayList<Node> nodeArrayList = DemoNodeData.getNodes();

        // Setup Node Adapter
        mNodeAdapter = new NodeAdapter(getContext());
        mNodeAdapter.setData(nodeArrayList);

        // Setup RecyclerView Adapter
        mRecyclerView = view.findViewById(R.id.recyclerViewDashboard);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mNodeAdapter);
    }

    // ================================
    // == Methods
    // ================================

}


/**
 * FIXME: Fetching Data of each node from Firebase and add to the array list
 */
class DemoNodeData {
    public static ArrayList<Node> getNodes() {
        ArrayList<Node> nodeArrayList = new ArrayList<>();

        for (int i = 1; i < 100; i++) {
            nodeArrayList.add(new Node("Node " + i,
                    new Random().nextDouble() * 100 + 1,
                    new Random().nextDouble() * 100 + 1,
                    new Random().nextDouble() * 100 + 1,
                    new Random().nextDouble() * 100 + 1,
                    new Random().nextDouble() * 100 + 1,
                    new Random().nextDouble() * 100 + 1));
        }
        return nodeArrayList;
    }
}
