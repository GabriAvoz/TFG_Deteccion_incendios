package com.gam.firealarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Stats extends AppCompatActivity {

    int node;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        node = getIntent().getExtras().getInt("node");
    }

    public void chartTemp(View view){

        if(node == 1){
            intent = new Intent(this, ChartNode1.class);
        }
        else {
            intent = new Intent(this, ChartNode2.class);
        }

        intent.putExtra("sensor", "temperature");
        startActivity(intent);
    }

    public void chartHum(View view){

        if(node == 1){
            intent = new Intent(this, ChartNode1.class);
        }
        else {
            intent = new Intent(this, ChartNode2.class);
        }

        intent.putExtra("sensor", "humidity");
        startActivity(intent);
    }

    public void chartFlame(View view){

        if(node == 1){
            intent = new Intent(this, ChartNode1.class);
        }
        else {
            intent = new Intent(this, ChartNode2.class);
        }

        intent.putExtra("sensor", "flame");
        startActivity(intent);
    }
}
