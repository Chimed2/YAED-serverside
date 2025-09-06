package com.example.yaed.serverside.data.repository

import android.util.Log
import com.example.yaed.serverside.data.model.Event
import com.example.yaed.serverside.service.CloudUploader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetectorRepository(private val cloudUploader: CloudUploader? = null) {

    private val events = mutableListOf<Event>()

    /**
     * Saves an event locally and optionally uploads to cloud.
     */
    fun saveEvent(event: Event) {
        events.add(event)
        Log.d("DetectorRepository", "Event saved: $event")

        cloudUploader?.let { uploader ->
            CoroutineScope(Dispatchers.IO).launch {
                uploader.uploadEvent(event)
            }
        }
    }

    /**
     * Returns a copy of all saved events.
     */
    fun getAllEvents(): List<Event> {
        return events.toList()
    }

    /**
     * Clears all stored events.
     */
    fun clearEvents() {
        events.clear()
        Log.d("DetectorRepository", "All events cleared")
    }
}
