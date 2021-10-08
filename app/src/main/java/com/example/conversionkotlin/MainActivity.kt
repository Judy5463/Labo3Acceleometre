package com.example.conversionkotlin

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity() : AppCompatActivity(), SensorEventListener {
    private var manager: SensorManager? = null
    private var sensor: Sensor? = null
    private var ls: List<Sensor>? = null
    private var gX: TextView? = null
    private var gY: TextView? = null
    private var gZ: TextView? = null
    private var txtMove: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        gX = findViewById<View>(R.id.Xacceleration) as TextView
        gY = findViewById<View>(R.id.Yacceleration) as TextView
        gZ = findViewById<View>(R.id.Zacceleration) as TextView
        txtMove = findViewById<View>(R.id.txtMove) as TextView
        manager = getSystemService(SENSOR_SERVICE) as SensorManager
        ls = manager!!.getSensorList(Sensor.TYPE_ALL)
        for (element: Sensor in (ls as MutableList<Sensor>?)!!) {
            Log.i("id", element.toString())
        }
        sensor = manager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        manager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent) {
        gX!!.text = "X: " + event.values.get(0).toString()
        gY!!.text = "Y: " + event.values.get(1).toString()
        gZ!!.text = "Z: " + event.values.get(2).toString()
        val vectorLength: Float
        vectorLength = Math.sqrt(
            Math.pow(event.values[0].toDouble(), 2.0) + Math.pow(
                event.values[1].toDouble(), 2.0
            )
                    + Math.pow(event.values[2].toDouble(), 2.0)
        ).toFloat()
        txtMove!!.x = txtMove!!.x + event.values.get(0) * (-1 * vectorLength)
        txtMove!!.y = txtMove!!.y + event.values.get(1) * vectorLength
    }

    override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
}