package com.example.a422androidapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


//new imports for networking library
//volley imports not added yet;

public class MainActivity extends AppCompatActivity {
    ArrayList<Double> averageList = new ArrayList<Double> ();
    ArrayList<Double> rainSums = new ArrayList<Double> ();
    ArrayList<Double> windSpeeds = new ArrayList<Double> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button helloButton = (Button) findViewById(R.id.button2);
        helloButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("UI Event Handler", "The Get Averages button was tapped");

                volleyRequest();

            }
        });
    }

/*
    public void okButtonTap(View view){
        Button okButton = (Button) view;
        okButton.setText("Tapped!");


        Log.i("UI Event Handler", "The OK button was tapped");

        EditText editTextObject = (EditText) findViewById(R.id.editText1);
        TextView helloWorldTextView = (TextView) findViewById(R.id.textView1);
        helloWorldTextView.setText(editTextObject.getText());

    }

 */

    private void volleyRequest() {
        final TextView textViewZero = (TextView) findViewById(R.id.textView0);
        final TextView textViewOne = (TextView) findViewById(R.id.textView1);
        final TextView textViewTwo = (TextView) findViewById(R.id.textView2);
        final TextView textViewThree = (TextView) findViewById(R.id.textView3);
        final TextView textViewFour = (TextView) findViewById(R.id.textView4);
        final TextView textViewFive = (TextView) findViewById(R.id.textView5);
        final TextView textViewSix = (TextView) findViewById(R.id.textView6);
        final TextView textViewSeven = (TextView) findViewById(R.id.textView7);

// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.open-meteo.com/v1/forecast?latitude=30.27&longitude=-97.74&hourly=temperature_2m&daily=temperature_2m_max,temperature_2m_min,rain_sum,windspeed_10m_max&current_weather=true&temperature_unit=fahrenheit&windspeed_unit=mph&timezone=America%2FChicago";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //textViewOne.setText("Response is: " + response.substring(0,500));
                        //textView.setText("Response is: " + response.substring(3456,4297));
                        try {
                            JSONObject jsonObject = new JSONObject(response); //correct
                            JSONObject daily = jsonObject.getJSONObject("daily");
                            JSONObject currWeather = jsonObject.getJSONObject("current_weather");
                            int temper = currWeather.getInt("temperature");
                            int windspeed = currWeather.getInt("windspeed");
                            JSONArray min = daily.getJSONArray("temperature_2m_max");
                            JSONArray max = daily.getJSONArray("temperature_2m_min");
                            JSONArray wind = daily.getJSONArray("windspeed_10m_max");
                            JSONArray rain = daily.getJSONArray("rain_sum");
                            for(int i = 0; i < min.length(); i++){
                                averageList.add((min.getDouble(i) + max.getDouble(i))/2);
                                windSpeeds.add(wind.getDouble(i));
                                rainSums.add(rain.getDouble(i));
                            }
                            textViewZero.setText("Current weather:\nTemp (Fahrenheit): " + temper + "\nWindspeed (mph): " + windspeed);
                            textViewOne.setText("Day one:\n" + "Temp: " + averageList.get(0) + "\nRain: " +
                                    rainSums.get(0) + "\nMaximum Wind Speed: " + windSpeeds.get(0) + "\n");
                            textViewTwo.setText("Day two:\n" + "Temp: " + averageList.get(1) + "\nRain: " +
                                    rainSums.get(1) + "\nMaximum Wind Speed: " + windSpeeds.get(1) + "\n");
                            textViewThree.setText("Day three:\n" + "Temp: " + averageList.get(2) + "\nRain: " +
                                    rainSums.get(2) + "\nMaximum Wind Speed: " + windSpeeds.get(2) + "\n");
                            textViewFour.setText("Day four:\n" + "Temp: " + averageList.get(3) + "\nRain: " +
                                    rainSums.get(3) + "\nMaximum Wind Speed: " + windSpeeds.get(3) + "\n");
                            textViewFive.setText("Day five:\n" + "Temp: " + averageList.get(4) + "\nRain: " +
                                    rainSums.get(4) + "\nMaximum Wind Speed: " + windSpeeds.get(4) + "\n");
                            textViewSix.setText("Day six:\n" + "Temp: " + averageList.get(5) + "\nRain: " +
                                    rainSums.get(5) + "\nMaximum Wind Speed: " + windSpeeds.get(5) + "\n");
                            textViewSeven.setText("Day seven:\n" + "Temp: " + averageList.get(6) + "\nRain: " +
                                    rainSums.get(6) + "\nMaximum Wind Speed: " + windSpeeds.get(6) + "\n");
                            //textViewTwo.setText("Day two: " + averageList.get(1));
                            //textViewThree.setText("Day three: " + averageList.get(2));
                            //textViewFour.setText("Day four: " + averageList.get(3));
                            //textViewFive.setText("Day five: " + averageList.get(4));
                            //textViewSix.setText("Day six: " + averageList.get(5));
                            //textViewSeven.setText("Day seven: " + averageList.get(6));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /*
                        holdString = response.substring(3456, 4297);
                        for(int i = 1; i < holdString.length(); i+=5){
                            subStringList.add(holdString.substring(i, i+3));
                        }
                        for(int i = 0; i < subStringList.size();i++){
                            subNumList.add(Integer.valueOf(subStringList.get(i)));
                        }
                        int average = 0;
                        int j = 0;
                        for(int i = 0; i < 7; i++){
                            average = subNumList.get(j) + subNumList.get(j+1) + subNumList.get(j+2) +
                                    subNumList.get(j+3) + subNumList.get(j+4) + subNumList.get(j+5) + subNumList.get(j+6);
                            average = average/7;
                            averageList.add(average);
                            j += 7;
                        }
                        response = averageList.get(0) + "\n" + averageList.get(1)
                                + "\n" + averageList.get(2) + "\n" + averageList.get(3) + "\n" +
                                averageList.get(4) + "\n" + averageList.get(5) + "\n" + averageList.get(6);
                        textView.setText("Response is: " + response);

                         */

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textViewOne.setText("That didn't work!");

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}