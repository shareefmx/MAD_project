package com.example.mad2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private boolean isCounting;
    private int stepCount;

    private TextView stepCountText;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepCountText = findViewById(R.id.stepCountText);
        restartButton = findViewById(R.id.restartButton);

        // Initialize the sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Check if the step counter sensor is available
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepSensor == null) {
            // Step counter sensor is not available on this device
            // You can show an error message or disable the step counting feature
            stepCountText.setText("Step Counter Not Available");
        } else {
            // Step counter sensor is available
            isCounting = true;
            stepCount = 0;

            // Register the sensor listener
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        // Set the restart button click listener
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restart the step count
                stepCount = 0;
                updateStepCountText();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isCounting) {
            // Increment the step count
            stepCount = (int) event.values[0];
            updateStepCountText();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the sensor listener when the activity is destroyed
        sensorManager.unregisterListener(this);
    }

    private void updateStepCountText() {
        // Update the step count text view
        stepCountText.setText("Step Count: " + stepCount);
    }
}
