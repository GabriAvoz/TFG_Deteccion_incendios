package com.gam.firealarm;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChartNode1 extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    String choice;
    RequestQueue requestQueue;
    JsonObjectRequest query;
    String URL = "http://192.168.1.57/tfg_db/get_measurements_node1.php";
    LineChart mChart;
    ArrayList<Measurement> measurements = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_node1);

        choice = getIntent().getExtras().getString("sensor");
        mChart = findViewById(R.id.chart_node1);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        query = new JsonObjectRequest(Request.Method.GET, URL, null, this, this);
        requestQueue.add(query);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"No se pudo consultar con éxito: " +
                error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Measurement measurement;
        ArrayList<Measurement> values = new ArrayList<>();
        JSONArray json = response.optJSONArray("node1");

        try {
            for (int i = 0; i < json.length(); i++){
                measurement = new Measurement();
                JSONObject jsonObject = json.getJSONObject(i);

                measurement.setId(jsonObject.optInt("measurement"));
                measurement.setTemperature((float)jsonObject.optDouble("temperature"));
                measurement.setHumidity((float)jsonObject.optDouble("humidity"));
                measurement.setFlame((float) jsonObject.optDouble("flame"));
                values.add(measurement);
            }

            reorderData(values);

            setData();
            Legend legend = mChart.getLegend();
            legend.setForm(Legend.LegendForm.LINE);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"No se pudo conectar con la base de datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void reorderData(ArrayList<Measurement> values) {
        for (int i = 1; i <= values.size(); i ++){
            measurements.add(values.get(values.size()- i));
        }

    }

    private void setData(){
        //Obtenemos las listas
        ArrayList<Entry> list1 = setList(measurements);

        //Las cogemos como LineDataSet
        LineDataSet set1 = new LineDataSet(list1, "Node 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        //Caracteristicas cada funcion
        set1.setColor(Color.GREEN);
        set1.setCircleColor(Color.GREEN);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);

        //Cargamos las funciones con los ajustes realizados
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);
        mChart.invalidate();
    }

    private ArrayList<Entry> setList(ArrayList<Measurement> list){
        ArrayList<Entry> points = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){

            switch (choice){
                case "temperature":
                    points.add(new Entry(i, list.get(i).getTemperature()));
                    break;

                case "humidity":
                    points.add(new Entry(i, list.get(i).getHumidity()));
                    break;

                default:
                    points.add(new Entry(i, list.get(i).getFlame()));
                    break;
            }
        }

        return points;
    }
}
