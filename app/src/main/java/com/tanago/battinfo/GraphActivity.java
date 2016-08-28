package com.tanago.battinfo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;

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
    int currentData = 0;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyMgr;
    private final int mNotificationId = 1;


    private void addDataToGraph(){
        currentData= Integer.parseInt(battery.getCurrent(currentFile));
        series.appendData(new DataPoint(graphCounter, currentData),true,60);
        graphCounter+=5;
    }

    private Runnable graphRefresher = new Runnable(){
        public void run(){
            addDataToGraph();
            mBuilder.setContentText(battery.getCurrent(currentFile) + " mA/h");
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
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
        graphVP.setXAxisBoundsManual(true);
        graphVP.setMaxX(0);
        graphVP.setMinX(-60);
        currentFile = batteryFiles.getCurrentFile();
    }

    protected void onResume() {
        super.onResume();
    }

    public void onStartButtonPressed(View v){
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.statusbaricon)
                        .setContentTitle("BattInfo")
                        .setContentText(currentData + " mA/h")
                        .setOngoing(true)
        .setContentIntent(resultPendingIntent);

        mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        currentFile = batteryFiles.getCurrentFile();
        handler.post(graphRefresher);
    }
    public void onStopButtonPressed(View v){
        handler.removeCallbacks(graphRefresher);
        mNotifyMgr.cancelAll();
    }
    public void onResetButtonPressed(View v){
        series.resetData(new DataPoint[]{new DataPoint(graphCounter, currentData)});
    }
}
