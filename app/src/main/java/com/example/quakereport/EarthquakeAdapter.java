package com.example.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        // Getting the current Earthquake object from the List
        Earthquake currentEarthquake = getItem(position);

        // Getting the different Views from listItemView
        TextView magnitudeView = listItemView.findViewById(R.id.magnitude);
        TextView locationOffsetView = listItemView.findViewById(R.id.location_offset);
        TextView locationView = listItemView.findViewById(R.id.primary_location);
        TextView dateView = listItemView.findViewById(R.id.date);
        TextView timeView = listItemView.findViewById(R.id.time);

        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());

        // Setting the magnitude in the magnitudeView
        magnitudeView.setText(formattedMagnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        String currentLocation = currentEarthquake.getLocation();

        String locationOffset, primaryLocation;

        if(currentLocation.contains(LOCATION_SEPARATOR)) {
            String[] locationArray = currentLocation.split(LOCATION_SEPARATOR);
            locationOffset = locationArray[0] + LOCATION_SEPARATOR;
            primaryLocation = locationArray[1];
        }
        else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = currentLocation;
        }

        // Setting the location prefix in the locationPrefixView
        locationOffsetView.setText(locationOffset);

        // Setting the location in the locationView
        locationView.setText(primaryLocation);

        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        String formattedDate = formatDate(dateObject);
        String formattedTime = formatTime(dateObject);

        // Setting the date in the dateView
        dateView.setText(formattedDate);

        // Setting the time in the timeView
        timeView.setText(formattedTime);

        return listItemView;
    }

    // Private method to format date
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    // Private method to format time
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    // Private method to format magnitude in specified decimal format
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);

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
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
