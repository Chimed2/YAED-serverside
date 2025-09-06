package com.example.yaed.serverside.service

import android.util.Log
import com.example.yaed.serverside.data.model.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class CloudUploader(private val serverUrl: String) {

    private val client = OkHttpClient()

    /**
     * Uploads an Event object to the cloud asynchronously
     */
    fun uploadEvent(event: Event) {
        val json = JSONObject().apply {
            put("id", event.id)
            put("type", event.type)
            put("magnitude", event.magnitude)
            put("timestamp", event.timestamp)
            put("location", event.location)
        }

        val body = RequestBody.create(
            MediaType.get("application/json; charset=utf-8"), json.toString()
        )

        val request = Request.Builder()
            .url(serverUrl)
            .post(body)
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("CloudUploader", "Upload failed: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        Log.d("CloudUploader", "Upload successful")
                    } else {
                        Log.e("CloudUploader", "Upload failed: ${response.code}")
                    }
                    response.close()
                }
            })
        }
    }
}
