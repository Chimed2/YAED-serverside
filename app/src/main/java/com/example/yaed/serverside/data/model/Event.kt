package com.example.yaed.serverside.data.model

data class Event(
    val id: String,           // Unique identifier, e.g., timestamp
    val type: String,         // Event type, e.g., "earthquake"
    val magnitude: Double,    // Magnitude of the earthquake
    val timestamp: Long,      // Epoch time in milliseconds
    val location: String      // Location or description of the event
)
