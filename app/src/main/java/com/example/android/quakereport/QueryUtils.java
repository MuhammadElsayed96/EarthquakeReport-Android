package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Muhammad Elsayed on 11/8/2017.
 */

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static ArrayList<Earthquake> fetchEarthquakeData(String url) {
        Log.e(LOG_TAG, "fetchEarthquakeData method has been triggered.");
        String jsonResponse = "";

        //  create URL object
        URL mUrl = createURL(url);

        try {
            jsonResponse = makeHTTPRequest(mUrl);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        ArrayList<Earthquake> earthquakes = extractJSONEarthquakes(jsonResponse);

        return earthquakes;
    }


    /**
     * returns new URL object from the given String url .
     */
    public static URL createURL(String url) {
        URL mUrl = null;
        try {
            mUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error with creating URL", e);
            return null;
        }
        return mUrl;
    }


    /**
     * Make an Http request to the given url and returns a String as the response.
     */
    public static String makeHTTPRequest(URL url) throws IOException {

        String jsonResponse = "";

        // if the url is null, then return the jsonResponse.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        // InputStream: It is the stream of bytes of the information we want. ("raw data")
        // InputStream: It represents a stream of bytes (small chuck of data)
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error with response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error retrieving the JSON results from the servers.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Converts the InputStream into a String that holds the
     * whole JSON response from the server.
     */
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder JSONResponse = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            // BufferedReader: it allows us to convert the raw data into human-readable text.
            // BufferedReader: it helps us to read the text from the inputStreamReader.
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                JSONResponse.append(line);
                line = reader.readLine();
            }
        }
        return JSONResponse.toString();
    }


    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractJSONEarthquakes(String earthquakesJSON) {

        if (TextUtils.isEmpty(earthquakesJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the earthquakesJSON. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root = new JSONObject(earthquakesJSON);
            JSONArray earthquakesArray = root.getJSONArray("features");

            for (int i = 0; i < earthquakesArray.length(); i++) {
                JSONObject currentEarthquake = earthquakesArray.getJSONObject(i);
                JSONObject features = currentEarthquake.getJSONObject("properties");
                double magnitude = features.getDouble("mag");
                String place = features.getString("place");
                long time = features.getLong("time");
                String url = features.getString("url");
                earthquakes.add(new Earthquake(magnitude, place, time, url));
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }


}
