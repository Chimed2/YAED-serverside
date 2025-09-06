package com.example.yaed.serverside.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yaed.serverside.R
import com.example.yaed.serverside.viewmodel.DetectorViewModel
import android.widget.TextView

class StatusFragment : Fragment() {

    private val viewModel: DetectorViewModel by viewModels()

    private lateinit var earthquakeRecycler: RecyclerView
    private lateinit var systemRecycler: RecyclerView

    private lateinit var earthquakeAdapter: LogAdapter
    private lateinit var systemAdapter: LogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_status, container, false)

        // Initialize RecyclerViews
        earthquakeRecycler = view.findViewById(R.id.earthquake_log_recycler)
        systemRecycler = view.findViewById(R.id.system_log_recycler)

        earthquakeAdapter = LogAdapter()
        systemAdapter = LogAdapter()

        earthquakeRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = earthquakeAdapter
        }

        systemRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = systemAdapter
        }

        // Observe ViewModel LiveData
        viewModel.earthquakeLog.observe(viewLifecycleOwner) { logs ->
            earthquakeAdapter.updateLogs(logs)
        }

        viewModel.logMessages.observe(viewLifecycleOwner) { logs ->
            systemAdapter.updateLogs(logs)
        }

        return view
    }

    // Simple RecyclerView Adapter for displaying log strings
    class LogAdapter : RecyclerView.Adapter<LogAdapter.LogViewHolder>() {

        private var logs: List<String> = emptyList()

        fun updateLogs(newLogs: List<String>) {
            logs = newLogs
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            return LogViewHolder(view)
        }

        override fun getItemCount(): Int = logs.size

        override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
            holder.bind(logs[position])
        }

        class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val textView: TextView = itemView.findViewById(android.R.id.text1)
            fun bind(text: String) {
                textView.text = text
            }
        }
    }
}
