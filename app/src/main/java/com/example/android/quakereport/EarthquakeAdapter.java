/*
 *  Copyright [2017] [Muhammad Elsayed]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Muhammad Elsayed on 11/8/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(@NonNull Context context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);


        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());

        TextView magnitudeView = listItemView.findViewById(R.id.magnitude_txt);
        magnitudeView.setText(formattedMagnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        String[] locationsArray = getLocation(currentEarthquake.getPlace());
        String primaryLocation = locationsArray[0];
        String offsetLocation = locationsArray[1];


        TextView primaryLocationView = listItemView.findViewById(R.id.primary_location_txt);
        primaryLocationView.setText(primaryLocation);
        TextView offsetLocationView = listItemView.findViewById(R.id.offset_location_txt);
        offsetLocationView.setText(offsetLocation);


        Date date = new Date(currentEarthquake.getTimeInMilliseconds());

        TextView dateView = listItemView.findViewById(R.id.date_txt);
        String formattedDate = formateDate(date);
        dateView.setText(formattedDate);

        TextView timeView = listItemView.findViewById(R.id.time_txt);
        String formattedTime = formatTime(date);
        timeView.setText(formattedTime);

        return listItemView;
    }


    private String formateDate(Date dateObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormatter.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a");
        return dateFormatter.format(dateObject);
    }

    private String[] getLocation(String location) {
        String[] locationArray = new String[2];
        if (!location.contains("km")) {
            locationArray[0] = location;
            locationArray[1] = "NEAR THE";
        } else {
            int idx = location.indexOf("of");
            String primaryLocation = location.substring(idx + 3, location.length());
            String offsetLocation = location.substring(0, idx + 2);
            locationArray[0] = primaryLocation;
            locationArray[1] = offsetLocation;
        }
        return locationArray;
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat formatter = new DecimalFormat("0.0");
        return formatter.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeFloor = (int) Math.floor(magnitude);
        int magnitudeColorResourceId;
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        //getColor:     Returns a color associated with a particular resource ID
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}