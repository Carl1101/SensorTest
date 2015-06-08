package com.carlosfilemanager.sensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import sensor.data.Sample;

//Activity that shows gyroscope, accelerometer, magnetic field, rotation vector and saves the data
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor gyroscope, accelerometer, magneticField, rotationVector; //Sensors being used
    private SensorManager sm; //Sensor Manager
    TextView accValues, gyroValues, magValues, rotValues; //TextViews that will show sensor values
    ArrayList<Sample> accList = new ArrayList<>(), gyroList = new ArrayList<>(), //Lists with the sensor data
            magList = new ArrayList<>(), rotList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize variables
        this.accValues = (TextView)this.findViewById(R.id.accValues);
        this.rotValues = (TextView)this.findViewById(R.id.rotValues);
        this.gyroValues = (TextView)this.findViewById(R.id.gyroValues);
        this.magValues = (TextView)this.findViewById(R.id.magValues);

        this.sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        this.gyroscope = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        this.accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.magneticField = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        this.rotationVector = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Displays and saves sensor values when they change
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accList.add(new Sample(event.values[0], event.values[1], event.values[2], event.timestamp));
            accValues.setText("X: " + event.values[0] + "\n" + "Y: " + event.values[1] + "\n" + "Z: " + event.values[2]);
        }

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyroList.add(new Sample(event.values[0], event.values[1], event.values[2], event.timestamp));
            gyroValues.setText("X: " + event.values[0] + "\n" + "Y: " + event.values[1] + "\n" + "Z: " + event.values[2]);
        }

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magList.add(new Sample(event.values[0], event.values[1], event.values[2], event.timestamp));
            magValues.setText("X: " + event.values[0] + "\n" + "Y: " + event.values[1] + "\n" + "Z: " + event.values[2]);
        }

        if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            rotList.add(new Sample(event.values[0], event.values[1], event.values[2], event.timestamp));
            rotValues.setText("X: " + event.values[0] + "\n" + "Y: " + event.values[1] + "\n" + "Z: " + event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //Registers sensors when toggle button is on and unregisters them when off
    public void toggleClick(View view) {
        boolean isClicked = ((ToggleButton)view).isChecked();

        if (isClicked) {
            sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
            sm.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME);
            sm.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_GAME);
            sm.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_GAME);
        }
        else
            sm.unregisterListener(this);
    }

    //Saves sensor data in .csv files when save data button is clicked
    public void saveData(View view) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date now = cal.getTime();

        writeToFile("Accelerometer_" + sdf.format(now) + ".csv", sdf.format(now),accList);
        writeToFile("Gyroscope_" + sdf.format(now) + ".csv", sdf.format(now), gyroList);
        writeToFile("RotationVector_" + sdf.format(now) + ".csv", sdf.format(now), rotList);
        writeToFile("MagneticField_" + sdf.format(now) + ".csv", sdf.format(now), magList);

        accList.clear();
        gyroList.clear();
        rotList.clear();
        magList.clear();
    }

    //Writes sensor data to file
    public  void writeToFile(String fileName, String time, ArrayList<Sample> samples) {
        final String FOLDER_NAME = "Sensor_Data";

        try {
            File f = new File(Environment.getExternalStorageDirectory(), FOLDER_NAME);
            if (!f.exists())
                f.mkdirs();

            File dataFolder = new File(f, time);
            if (!dataFolder.exists())
                dataFolder.mkdirs();

            File out = new File(dataFolder, fileName);

            FileOutputStream fo = new FileOutputStream(out);

            for (Sample element : samples) {
                fo.write(("\"" + element.getX() + "\", " + "\""
                        + element.getY() + "\", " + "\""
                        + element.getZ() + "\", "
                        + "\"" + element.getTimestamp() + "\"" + "\n").getBytes());
            }

            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
