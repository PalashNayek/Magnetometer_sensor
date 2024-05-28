package com.palash.magnetometer_sensor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.palash.magnetometer_sensor.R
import com.palash.magnetometer_sensor.databinding.FragmentHomeBinding
import com.palash.magnetometer_sensor.view_model.SensorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sensorViewModel by viewModels<SensorViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorViewModel.magnetometerData.observe(viewLifecycleOwner, Observer { data ->
            if (data != null) {
                binding.txt.text = "Magnetometer: X=${data[0]} µT, Y=${data[1]} µT, Z=${data[2]} µT"
            } else {
                binding.txt.text = "Magnetometer sensor not available"
                Toast.makeText(context, "Magnetometer sensor not available on this device", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        sensorViewModel.startListening()
    }

    override fun onPause() {
        super.onPause()
        sensorViewModel.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}