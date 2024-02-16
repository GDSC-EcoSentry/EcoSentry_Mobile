package com.observers.ecosentry_mobile.controllers.drawer.fragments.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.observers.ecosentry_mobile.R;
import com.observers.ecosentry_mobile.models.node.Node;
import com.observers.ecosentry_mobile.models.node.NodeAdapter;
import com.observers.ecosentry_mobile.models.station.Station;

import java.util.ArrayList;
import java.util.Random;

public class DashboardFragment extends Fragment {

    // ================================
    // == Fields
    // ================================
    private MaterialAutoCompleteTextView mMaterialAutoCompleteTextView;
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

        // Setup Dropdown Material Textview
        setUpDropdownTextView(view);

        // Setup Node Adapter
        mNodeAdapter = new NodeAdapter(getContext());

        // Get Async Data from Fire Store
        DemoData.getNodes(new DataFetchCallback() {
            @Override
            public void onDataFetched(ArrayList<Node> nodes) {
                // Setup Node Adapter
                if (nodes.size() != 0) {
                    System.out.println("successsss");
                    for (Node n : nodes) {
                        System.out.println(n.toString());
                    }
                }

                mNodeAdapter.setData(nodes);
            }
        });

        // Setup RecyclerView Adapter
        mRecyclerView = view.findViewById(R.id.recyclerViewDashboard);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mNodeAdapter);
    }

    // ================================
    // == Methods
    // ================================

    /**
     * FIXME: When clicking the station, might be you should get nodes based on the station
     *
     * @param view: a view containing this dropdown
     */
    public void setUpDropdownTextView(@NonNull View view) {

        // Convert list to String[] as the required parameter
        String[] stations = DemoData.getFakeStations()
                .parallelStream()
                .map(Station::getName)
                .toArray(String[]::new);

        // Setup a list of stations
        mMaterialAutoCompleteTextView = view.findViewById(R.id.autoCompleteTextViewStation);
        mMaterialAutoCompleteTextView.setSimpleItems(stations);

        // Trigger get nodes when click a station
        mMaterialAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "You just click me " + id, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


/**
 * FIXME: Will move to separate class after done testing
 */
class DemoData {

    /**
     * A function to test for dropdown stations
     *
     * @return
     */
    public static ArrayList<Station> getFakeStations() {
        ArrayList<Station> stationArrayList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            stationArrayList.add(new Station("Rừng Sác", "Station " + i));
        }
        return stationArrayList;
    }

    /**
     * A function to test for recycler view layout
     *
     * @return list of fake nodes
     */
    public static ArrayList<Node> getFakeNodes() {
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

    /**
     * Get a list of nodes by calling firestore function
     *
     * @param callback: a callback adding nodes to the Node Adapter
     */
    public static void getNodes(DataFetchCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<Node> list = new ArrayList<>();
        CollectionReference nodesRef = db.collection("stations")
                .document("1")
                .collection("nodes");
        nodesRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    String nodeID = snapshot.getId();
                    DocumentReference nodeRef = nodesRef.document(nodeID);
                    nodeRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            Node node = null;
                            if (error != null) {
                                return;
                            }
                            if (value != null && value.exists()) {
                                node = value.toObject(Node.class);
                                list.add(node);
                            }
//                            if (list.size() == queryDocumentSnapshots.size()) {
//                                callback.onDataFetched(list);
//                            }
                            callback.onDataFetched(list);
                        }
                    });
                }
            }
        });
    }
}



