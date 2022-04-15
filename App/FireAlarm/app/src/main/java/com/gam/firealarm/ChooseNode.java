package com.gam.firealarm;

import android.content.Intent;
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

public class ChooseNode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_node);
    }

    public void node1(View view) {
        Intent intent = new Intent(this, Stats.class);
        intent.putExtra("node", 1);
        startActivity(intent);
    }

    public void node2(View view) {
        Intent intent = new Intent(this, Stats.class);
        intent.putExtra("node", 2);
        startActivity(intent);
    }
}
