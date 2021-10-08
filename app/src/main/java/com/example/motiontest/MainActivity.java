package com.example.motiontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView img, img2, img3;
    private TextView Xtext;
    private Sensor mainAccelerometer;
    private SensorManager mainSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.androidImage);
        mainSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mainAccelerometer = mainSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mainSensorManager.registerListener(this, mainAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent e) {

        if (Xtext == null){
            getTextView();
        }

        if (img2 == null || img3 == null){
            getImage();
        }

        float x = e.values[0] + img.getRotation();
        float y = e.values[1] + img2.getRotationY();
        float z = e.values[2] + img3.getRotationX();

        if ( x < -360.0 ){
            x += 360.0;
        }

        if ( x > 360.0 ){
            x -= 360.0;
        }

        if ( y < -360.0 ){
            y += 360.0;
        }

        if ( y > 360.0 ){
            y -= 360.0;
        }

        if ( z < -360.0 ){
            z += 360.0;
        }

        if ( z > 360.0 ){
            z -= 360.0;
        }

        img.setRotation(x);
        img2.setRotationY(y);
        img3.setRotationX(z);
        Xtext.setText("X=" + e.values[0] + "\nY=" + e.values[1] + "\nZ=" + e.values[2]);

    }

    private void getImage() {
        img2 = (ImageView) findViewById(R.id.androidImage2);
        img3 = (ImageView) findViewById(R.id.androidImage3);
    }

    public void getTextView(){
        Xtext = (TextView) findViewById(R.id.textX);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //Register the Listener when the Activity is resumed
    protected void onResume() {
        super.onResume();
        mainSensorManager.registerListener(this, mainAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //Unregister the Listener when the Activity is paused
    protected void onPause() {
        super.onPause();
        mainSensorManager.unregisterListener(this);
    }
}