package com.hugo.geekwars;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        float maxRange = mSensor.getMaximumRange();

        Button mButton = (Button) findViewById(R.id.b5);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this, AboutActivity.class));
            }
        });

        Button mButton1 = (Button) findViewById(R.id.b1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Main2Activity.this, "No Service", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this, GameActivity.class));
            }
        });

        Button mButton2 = (Button) findViewById(R.id.b2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Main2Activity.this, "No Service", Toast.LENGTH_SHORT).show();
            }
        });

        Button mButton3 = (Button) findViewById(R.id.b4);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Main2Activity.this, "No Service", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this, ProfileActivity.class));
            }
        });
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
//        if (mSensor.getMaximumRange() == event.values[0]) {
//            //iv.setImageResource(R.drawable.near);
//            Toast.makeText(Main2Activity.this, "Free Add", Toast.LENGTH_SHORT).show();
//        } else {
//            //iv.setImageResource(R.drawable.far);
//        }

        if (mSensor.getMaximumRange() == event.values[0]) {
            //iv.setImageResource(R.drawable.near);
            Toast.makeText(Main2Activity.this, "Free Add", Toast.LENGTH_SHORT).show();
        } else {
            //iv.setImageResource(R.drawable.far);
        }

//        float distance = event.values[0];
//        Toast.makeText(this, "proximity "+mSensor.getMaximumRange(),Toast.LENGTH_LONG).show();
//        if(distance > 10){
//            Toast.makeText(Main2Activity.this, "Free GeekCoin", Toast.LENGTH_SHORT).show();
//        }else{
//            //sensorLayout.setBackgroundResource(R.drawable.sensor_background_two);
//        }

    }


}
