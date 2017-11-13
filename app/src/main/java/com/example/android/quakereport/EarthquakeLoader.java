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

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Elsayed on 11/12/2017.
 */


/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String url;

    // A constructor to create a new EarthquakeLoader instance.
    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {

        Log.i(LOG_TAG, "TEST: onStartLoading method has been triggered.");

        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {

        Log.i(LOG_TAG, "TEST: loadInBackground method has been triggered.");
        if (url == null) {
            return null;
        }

        ArrayList<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(url);
        return earthquakes;
    }
}
