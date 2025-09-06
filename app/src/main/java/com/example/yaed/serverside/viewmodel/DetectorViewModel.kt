package com.example.yaed.serverside.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yaed.serverside.data.model.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class DetectorViewModel : ViewModel() {

    private val _logMessages = MutableLiveData<List<String>>(emptyList())
    val logMessages: LiveData<List<String>> = _logMessages

    private val _earthquakeLog = MutableLiveData<List<String>>(emptyList())
    val earthquakeLog: LiveData<List<String>> = _earthquakeLog

    private val maxLogLines = 5

    init {
        startSystemLogSimulation()
    }

    // Extension function to prepend items and enforce max size
    private fun <T> MutableLiveData<List<T>>.prepend(item: T, maxSize: Int) {
        val current = this.value.orEmpty().toMutableList()
        current.add(0, item)
        this.value = current.take(maxSize)
    }

    // Simulate system log events
    private fun startSystemLogSimulation() {
        viewModelScope.launch {
            for (i in 1..20) {
                delay(2000)
                addLogMessage("System event: Processing data packet $i")
            }
        }
    }

    fun addLogMessage(message: String) {
        _logMessages.prepend("${getCurrentTimestamp()}: $message", maxLogLines)
    }

    fun logEarthquakeEvent(): Event {
        val magnitudeValue = Random.nextDouble(3.0, 7.0)
        val magnitude = String.format("%.1f", magnitudeValue)
        val timestamp = getCurrentTimestamp(includeTime = true)
        val location = "Simulated Location ${Random.nextInt(1, 100)}"
        val message = "EARTHQUAKE DETECTED: Magnitude $magnitude at $location on $timestamp"

        addLogMessage(message)
        _earthquakeLog.prepend(message, maxLogLines)

        // Return Event for future repository use
        return Event(
            id = System.currentTimeMillis().toString(),
            type = "earthquake",
            magnitude = magnitudeValue,
            timestamp = System.currentTimeMillis(),
            location = location
        )
    }

    private fun getCurrentTimestamp(includeTime: Boolean = false): String =
        SimpleDateFormat(
            if (includeTime) "yyyy-MM-dd HH:mm:ss" else "HH:mm:ss",
            Locale.getDefault()
        ).format(Date())
}
