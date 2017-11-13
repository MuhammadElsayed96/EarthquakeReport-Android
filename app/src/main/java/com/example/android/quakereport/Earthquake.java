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

/**
 * Created by Muhammad Elsayed on 11/8/2017.
 */

public class Earthquake {


    private double magnitude;
    private String place;
    private long timeInMilliseconds;
    private String Url;

    public Earthquake(double magnitude, String place, long timeInMilliseconds, String Url) {
        this.magnitude = magnitude;
        this.place = place;
        this.timeInMilliseconds = timeInMilliseconds;
        this.Url = Url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public String getUrl() {
        return Url;
    }
}
