package com.gam.firealarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ServoControl extends AppCompatActivity  implements Response.ErrorListener, Response.Listener<JSONObject> {
    double mov = 7.5;
    RequestQueue requestQueue;
    JsonObjectRequest query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servo_control);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public void node1(View view){
        mov = 11;
        setQuery();
    }

    public void node2(View view){
        mov = 4;
        setQuery();
    }

    public void center(View view){
        mov = 7.5;
        setQuery();
    }

    public void left(View view){
        if(mov < 11){
            mov = mov + 0.5;
        }
        setQuery();
    }

    public void right(View view){
        if(mov > 4){
            mov = mov - 0.5;
        }
        setQuery();
    }

    public void setQuery(){
        String URL = "http://192.168.1.164/servo/servo_mov.php?mov=" + String.valueOf(mov);
        query = new JsonObjectRequest(Request.Method.GET, URL, null, this, this);
        requestQueue.add(query);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
    }

    @Override
    public void onResponse(JSONObject response) {

    }

}
