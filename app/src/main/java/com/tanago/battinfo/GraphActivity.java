package com.tanago.battinfo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends AppCompatActivity {

    private GraphView graph;
    private static int graphCounter = 0;
    LineGraphSeries<DataPoint> series;
    private final Handler handler = new Handler();
    private final Battery battery= new Battery();
    private final BatteryFiles batteryFiles = new BatteryFiles();
    String currentFile;

    private void addDataToGraph(){
        series.appendData(new DataPoint(graphCounter, Integer.parseInt(battery.getCurrent(currentFile))),true,80);
        graphCounter+=5;
    }

    private Runnable graphRefresher = new Runnable(){
        public void run(){
            addDataToGraph();
            handler.postDelayed(this, 5000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        series = new LineGraphSeries<>();
        graph = (GraphView) findViewById(R.id.graph);
        graph.addSeries(series);
        Viewport graphVP = graph.getViewport();
        //graphVP.setYAxisBoundsManual(true);
        graphVP.setXAxisBoundsManual(true);
        graphVP.setMaxX(0);
        graphVP.setMinX(-80);
//        graphVP.setMaxY(1000);
//        graphVP.setMinY(-1000);
        currentFile = batteryFiles.getCurrentFile();
    }

    protected void onResume() {
        super.onResume();
//        series.appendData(new DataPoint(graphCounter++,0),true,8);
//        series.appendData(new DataPoint(graphCounter++,0),true,8);
//        series.appendData(new DataPoint(graphCounter++,0),true,8);
//        series.appendData(new DataPoint(graphCounter++,0),true,8);
//        series.appendData(new DataPoint(graphCounter++,0),true,8);
//        series.appendData(new DataPoint(graphCounter++,0),true,8);
//        series.appendData(new DataPoint(graphCounter++,0),true,8);
        handler.post(graphRefresher);
    }
}
