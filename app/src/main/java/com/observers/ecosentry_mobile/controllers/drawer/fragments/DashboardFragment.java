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

import com.google.android.gms.tasks.OnSuccessListener;
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
        DemoNodeData.getNodes(new DataFetchCallback() {
            @Override
            public void onDataFetched(ArrayList<Node> nodes) {
                // Setup Node Adapter
                if (nodes.size()!=0) {
                    System.out.println("successsss");
                    for (Node n : nodes) {
                        System.out.println(n.getTemperature());
                        System.out.println(n.getName());
                    }
                }
                mNodeAdapter = new NodeAdapter(getContext());
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

}


/**
 * FIXME: Fetching Data of each node from Firebase and add to the array list
 */
class DemoNodeData {
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
                            if (error!=null) {
                                return;
                            }
                            if (value!=null && value.exists()) {
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
interface DataFetchCallback {
    void onDataFetched(ArrayList<Node> nodes);
}
