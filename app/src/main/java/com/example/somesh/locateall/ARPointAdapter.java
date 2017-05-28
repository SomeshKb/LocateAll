package com.example.somesh.locateall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Somesh on 5/27/2017.
 */

public class ARPointAdapter  extends ArrayAdapter<ARPoint> {

    ArrayList<ARPoint> arPoints = new ArrayList<>();


    public ARPointAdapter(Context context, int resource, ArrayList<ARPoint> arPoints) {
        super(context, resource,arPoints);
        this.arPoints = arPoints;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.places_list,parent,false);
        }
        ARPoint currentARPoint= getItem(position);

        Double latitude= currentARPoint.getLocation().getLatitude();
        TextView latitudeTextView = (TextView) convertView.findViewById(R.id.latitude);
        latitudeTextView.setText(latitude.toString());

        Double longitude = currentARPoint.getLocation().getLongitude();
        TextView longitudeTextView=(TextView)convertView.findViewById(R.id.longitude);
        longitudeTextView.setText(longitude.toString());

        String name = currentARPoint.getName();
        TextView nameTextView=(TextView)convertView.findViewById(R.id.name);
        nameTextView.setText(name);

        return convertView;

    }
}
