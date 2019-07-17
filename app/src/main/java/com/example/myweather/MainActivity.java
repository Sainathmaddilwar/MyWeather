package com.example.myweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result;
    ImageView image;

    //http://api.openweathermap.org/data/2.5/weather?q=Paris&appid=5f56d525d1619d0a2cd2eac4ce55588e

    String baseURL = "https://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=577b7d3e60200e91c523139eecab4f8b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        city = (EditText) findViewById(R.id.city);
        result = (TextView) findViewById(R.id.result);
        image=(ImageView)findViewById(R.id.imageView);
        final int[] j = new int[1];


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myURL = baseURL + city.getText().toString() + API;
                Log.i("URL", "URL: " + myURL);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.i("JSON", "JSON: " + jsonObject);

                                try {
                                    String info = jsonObject.getString("weather");
                                    Log.i("INFO", "INFO: "+ info);

                                    JSONArray ar = new JSONArray(info);

                                    for (int i = 0; i < ar.length(); i++){
                                        JSONObject parObj = ar.getJSONObject(i);

                                        String myWeather = parObj.getString("main");
                                        result.setText(myWeather);
                                        Log.i("MAIN", "MAIN: " + parObj.getString("main"));
                                        switch (myWeather){
                                            case "Haze":
                                                j[0] =1;
                                                break;
                                            case "Sunny":
                                                j[0] =2;
                                                break;
                                            case "Clear":
                                                j[0] =3;
                                                break;
                                            case "Rain":
                                                j[0] =4;
                                                break;
                                        }
                                        if (j[0] ==1)
                                        {
                                            image.setImageResource(R.drawable.hazy);
                                        }
                                        else if (j[0]==2)
                                        {
                                            image.setImageResource(R.drawable.sunny);
                                        }
                                        else if (j[0]==3)
                                        {
                                            image.setImageResource(R.drawable.clear);
                                        }
                                        else  if (j[0]==4)
                                        {
                                            image.setImageResource(R.drawable.rainy);
                                        }

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


//                                try {
//                                    String coor = jsonObject.getString("coord");
//                                    Log.i("COOR", "COOR: " + coor);
//                                    JSONObject co = new JSONObject(coor);
//
//                                    String lon = co.getString("lon");
//                                    String lat = co.getString("lat");
//
//                                    Log.i("LON", "LON: " + lon);
//                                    Log.i("LAT", "LAT: " + lat);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }


                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.i("Error", "Something went wrong" + volleyError);

                            }
                        }


                );
                MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);




            }
        });






    }
}
