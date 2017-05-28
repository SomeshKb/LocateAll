package com.example.somesh.locateall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements AsyncResponse{

    private static final String API_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=23.2599,77.4126&radius=5000&type=park&sensor=true&key=AIzaSyCUA3sVAVODHyhgLgXahQ3EKqFGyAZK73o";
    private String TAG = MainActivity.class.getSimpleName();
    private ARPointAdapter mAdapter;

    List<ARPoint> arPointList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Find a reference to the {@link ListView} in the layout
        ListView arPointListView = (ListView) findViewById(R.id.listView);

        // Create a new adapter that takes an empty list of places as input
        mAdapter = new ARPointAdapter(this, R.id.listView, new ArrayList<ARPoint>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        arPointListView.setAdapter(mAdapter);

        PlacesAsyncTask placesAsyncTask= new PlacesAsyncTask();
        placesAsyncTask.delegate = this;
        placesAsyncTask.execute(API_URL);
        Log.e("API",API_URL);
    }

    @Override
    public void processFinish(List<ARPoint> output) {

// Clear the adapter of previous data
        mAdapter.clear();

        // If there is a valid list of {@link Places}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (output != null && !output.isEmpty()) {
            mAdapter.addAll(output);
        }    }
}