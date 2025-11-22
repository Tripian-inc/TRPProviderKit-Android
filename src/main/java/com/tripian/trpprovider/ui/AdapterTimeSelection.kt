package com.tripian.trpprovider.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tripian.trpprovider.R

/**
 * Created by semihozkoroglu on 29.07.2020.
 */
abstract class AdapterTimeSelection(private val context: Context, private val items: List<String>) : RecyclerView.Adapter<AdapterTimeSelection.TimeViewHolder>() {

    abstract fun onClickedTime(time: String)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        return TimeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_time, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val time = items[position]
        with(holder) {
            tvTime.text = time

            root.setOnClickListener { onClickedTime(time) }
        }
    }

    class TimeViewHolder internal constructor(vi: View) : RecyclerView.ViewHolder(vi) {
        var root: View = vi.findViewById(R.id.root)
        var tvTime: TextView = vi.findViewById(R.id.tvTime)
    }
}