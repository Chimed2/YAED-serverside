package com.example.yaed.serverside.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.yaed.serverside.R
import com.example.yaed.serverside.viewmodel.DetectorViewModel

class SetupFragment : Fragment() {

    private val viewModel: DetectorViewModel by viewModels()

    private lateinit var intervalInput: EditText
    private lateinit var notificationsSwitch: Switch
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setup, container, false)

        intervalInput = view.findViewById(R.id.detection_interval_input)
        notificationsSwitch = view.findViewById(R.id.notifications_switch)
        saveButton = view.findViewById(R.id.save_setup_button)

        saveButton.setOnClickListener {
            saveSettings()
        }

        return view
    }

    private fun saveSettings() {
        val intervalText = intervalInput.text.toString()
        val interval = intervalText.toIntOrNull()

        if (interval == null || interval <= 0) {
            Toast.makeText(context, "Please enter a valid interval", Toast.LENGTH_SHORT).show()
            return
        }

        val notificationsEnabled = notificationsSwitch.isChecked

        // TODO: Save these settings to ViewModel or Repository as needed
        // Example: viewModel.setDetectionInterval(interval)
        // Example: viewModel.setNotificationsEnabled(notificationsEnabled)

        Toast.makeText(
            context,
            "Settings saved: Interval = $interval s, Notifications = $notificationsEnabled",
            Toast.LENGTH_SHORT
        ).show()

        // Optionally trigger a detection event for testing
        // viewModel.logEarthquakeEvent()
    }
}
