package com.example.somesh.locateall;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

 interface AsyncResponse {
    void processFinish(List<ARPoint> output);
}

public class PlacesAsyncTask extends AsyncTask<String,Void,List<ARPoint>> {

    public AsyncResponse delegate = null;

    @Override
    protected List<ARPoint> doInBackground(String... params) {

        if (params.length < 1 || params[0] == null) {
            return null;
        }


        List<ARPoint> result = QueryUtils.fetchPlacesData(params[0]);

        return result;
    }


    @Override
    protected void onPostExecute(List<ARPoint> places) {

        delegate.processFinish(places);

    }
}
