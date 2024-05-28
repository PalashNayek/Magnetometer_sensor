package com.palash.magnetometer_sensor.repositories

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class SensorRepository @Inject constructor(private val sensorManager: SensorManager) {

    private val _magnetometerData = MutableLiveData<FloatArray?>()
    val magnetometerData: LiveData<FloatArray?> = _magnetometerData

    private val magnetometerListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                if (it.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                    _magnetometerData.postValue(it.values)
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // No action needed
        }
    }

    fun startListening() {
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        if (sensor != null) {
            sensorManager.registerListener(magnetometerListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            _magnetometerData.postValue(null)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(magnetometerListener)
    }
}