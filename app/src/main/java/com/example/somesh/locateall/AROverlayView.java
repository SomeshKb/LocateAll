package com.example.somesh.locateall;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.opengl.Matrix;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by somesh on 4/30/17.
 */



public class AROverlayView extends View implements AsyncResponse{

    Context context;
    String API_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=23.2599,77.4126&radius=20000&type=train_station&sensor=true&key=AIzaSyCUA3sVAVODHyhgLgXahQ3EKqFGyAZK73o";
    private float[] rotatedProjectionMatrix = new float[16];
    private Location currentLocation;
    private List<ARPoint> arPoints;
    public static int range;
    public static Location currentLocation1;


    public AROverlayView(Context context) {

        super(context);
        this.context = context;
      //  PlacesAsyncTask placesAsyncTask= new PlacesAsyncTask();
        //placesAsyncTask.delegate = this;
        //placesAsyncTask.execute(API_URL);
        Log.e("API",API_URL);
    }

    public void updateRotatedProjectionMatrix(float[] rotatedProjectionMatrix) {
        this.rotatedProjectionMatrix = rotatedProjectionMatrix;
        this.invalidate();
    }

    public void updateCurrentLocation(Location currentLocation, int range){
        if(currentLocation==null){
            Toast.makeText(getContext(),"Please Check Network Setting",Toast.LENGTH_SHORT).show();
        }
        else {
            AROverlayView.range = range;
            AROverlayView.currentLocation1=currentLocation;
            API_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+currentLocation.getLatitude()
                    +","+currentLocation.getLongitude()+"&radius="+range+
                    "&type="+MainActivity.placeType+"&sensor=true&key=AIzaSyCUA3sVAVODHyhgLgXahQ3EKqFGyAZK73o";

            PlacesAsyncTask placesAsyncTask= new PlacesAsyncTask();
            placesAsyncTask.delegate = this;
            placesAsyncTask.execute(API_URL);
            Log.e("API",API_URL);
            this.currentLocation = currentLocation;
            this.invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentLocation == null) {
            return;
        }

        final int radius = 12;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setTextSize(30);
if(arPoints==null){

}

else {
        for (int i = 0,j=0; i < arPoints.size(); i++,j++) {

            switch (j){
                case 1:        paint.setColor(Color.WHITE);break;
                case 2:        paint.setColor(Color.BLUE);break;
                case 3:        paint.setColor(Color.MAGENTA);break;
                case 4:        paint.setColor(Color.YELLOW);break;
                case 5:        paint.setColor(Color.RED);break;
                case 6:        paint.setColor(Color.GRAY);break;
                case 7:        paint.setColor(Color.GREEN);break;
                case 8:        paint.setColor(Color.RED);break;
                case 9:        paint.setColor(Color.YELLOW);break;
                case 10:        paint.setColor(Color.WHITE);break;
                case 11:        paint.setColor(Color.BLACK);break;
                case 12:        paint.setColor(Color.MAGENTA);break;
                case 13:        paint.setColor(Color.GREEN);break;
                case 14:        paint.setColor(Color.BLUE);break;
                case 15:        paint.setColor(Color.YELLOW);break;
                case 0:        paint.setColor(Color.RED);break;
                default:        j=0;


            }

            Log.e("Color",""+j);

            float[] currentLocationInECEF = LocationHelper.WSG84toECEF(currentLocation);
            float[] pointInECEF = LocationHelper.WSG84toECEF(arPoints.get(i).getLocation());
            float[] pointInENU = LocationHelper.ECEFtoENU(currentLocation, currentLocationInECEF, pointInECEF);

            float[] cameraCoordinateVector = new float[4];
            Matrix.multiplyMV(cameraCoordinateVector, 0, rotatedProjectionMatrix, 0, pointInENU, 0);

            // cameraCoordinateVector[2] is z, that always less than 0 to display on right position
            // if z > 0, the point will display on the opposite
            if (cameraCoordinateVector[2] < 0) {
                float x = (0.5f + cameraCoordinateVector[0] / cameraCoordinateVector[3]) * canvas.getWidth();
                float y = (0.5f - cameraCoordinateVector[1] / cameraCoordinateVector[3]) * canvas.getHeight();

                canvas.drawCircle(x, y, radius, paint);
                canvas.drawText(arPoints.get(i).getName(), x - (30 * arPoints.get(i).getName().length() / 2), y - 10, paint);
            }
        }
    }
    }

    @Override
    public void processFinish(List<ARPoint> output) {

        Random rand = new Random();
        int  n = rand.nextInt(100) + 1;
        int altitude=n*20;
                arPoints = new ArrayList<>();
        int i=0;
        for (ARPoint place : output) {
            Double latP = place.getLocation().getLatitude();
            Double lngP = place.getLocation().getLongitude();
            String placeName = place.getName();
            arPoints.add(new ARPoint(placeName, latP, lngP, altitude));
            Log.e("AR POINT:",arPoints.get(i++).toString());
        }

        arPoints.add(new ARPoint("Truba Institute", 23.3033045,77.3490176, 0));


    }
}
