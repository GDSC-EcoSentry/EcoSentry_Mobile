package com.observers.ecosentry_mobile.controllers.drawer.fragments.home;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.GeoPoint;
import com.observers.ecosentry_mobile.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.observers.ecosentry_mobile.R;
import com.observers.ecosentry_mobile.models.node.Node;
import com.observers.ecosentry_mobile.models.station.Station;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    // ================================
    // == Fields
    // ================================
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference stationsRef = db.collection("stations");

    // ================================
    // == Life Cycle
    // ================================
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        stationsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    String stationID = snapshot.getId();
                    Station station = snapshot.toObject(Station.class);

                    // Add a marker in station and move camera
                    markStation(station);

                    // Reference to nodes in station
                    CollectionReference nodesRef = stationsRef.document(stationID)
                            .collection("nodes");

                    // Add markers in nodes and move camera
                    markNodes(nodesRef);
                }
            }
        });
    }

    // ================================
    // == Methods
    // ================================
    public void markStation(Station station) {
        Drawable vectorDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.baseline_wifi_24);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);

        GeoPoint stationGeo = station.getGeopoint();
        double stationLatitude = stationGeo.getLatitude();
        double stationLongitude = stationGeo.getLongitude();
        LatLng stationMarker = new LatLng(stationLatitude, stationLongitude);
        mMap.addMarker(new MarkerOptions().position(stationMarker)
                .title(station.getLocation()).icon(icon));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stationMarker));
    }

    public void markNodes(CollectionReference nodesRef) {
        nodesRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                Node node = snapshot.toObject(Node.class);
                GeoPoint nodeGeo = node.getGeopoint();
                double nodeLatitude = nodeGeo.getLatitude();
                double nodeLongitude = nodeGeo.getLongitude();
                LatLng nodeMarker = new LatLng(nodeLatitude, nodeLongitude);
                mMap.addMarker(new MarkerOptions().position(nodeMarker)
                        .title(node.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nodeMarker, 14));
            }
        });
    }

//    public String getData(Node node) {
//        String data = "Temperature: " + node.getTemperature()
//                + " Humidity: " + node.getHumidity()
//                + "\nSoil moisture: " + node.getSoil_moisture()
//                + " Dust: " + node.getDust()
//                + "\nRainfall: " + node.getRain()
//                + " CO: " + node.getCo();
//        return data;
//    }
}
