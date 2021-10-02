package com.adivid.iprogrammertask.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adivid.iprogrammertask.R
import com.adivid.iprogrammertask.data.database.WeatherEntity

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {


    var list: List<WeatherEntity?> = mutableListOf()
    private lateinit var context: Context

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCityName: TextView = itemView.findViewById(R.id.cityName)
        val tvMaxTemp: TextView = itemView.findViewById(R.id.maxTemp)
        val tvMinTemp: TextView = itemView.findViewById(R.id.minTemp)
        val dateTime: TextView = itemView.findViewById(R.id.dateTime)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        context = parent.context;
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_list_layout, parent, false)
        return WeatherViewHolder(view)

    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val cityName = "City: ${list[position]?.cityName}"
        val maxTempDouble = String.format("%.2f", list[position]?.maxTemp?.minus(273.15))
        val minTempDouble = String.format("%.2f", list[position]?.minTemp?.minus(273.15))
        val maxTemp = "Max Temperature: $maxTempDouble°C"
        val minTemp = "Min Temperature: $minTempDouble°C"
        val dateTime = list[position]?.dateTimeFormatted

        holder.tvCityName.text = cityName
        holder.tvMaxTemp.text = maxTemp
        holder.tvMinTemp.text = minTemp
        holder.dateTime.text = dateTime

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitList(list: List<WeatherEntity?>) {
        this.list = list
    }


}