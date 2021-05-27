package com.cesar.school.avaliacaofinal.ui.search

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cesar.school.avaliacaofinal.R
import com.cesar.school.avaliacaofinal.utils.City
import com.cesar.school.avaliacaofinal.utils.WeatherResponse

class Adapter():
    ListAdapter<City, Adapter.WeatherViewHolder>(WeatherDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var cityName: TextView = itemView.findViewById(R.id.cityName)
        var temperature: TextView = itemView.findViewById(R.id.cityTemperature)
        var imagem: ImageView = itemView.findViewById(R.id.weatherIcon)

        fun bindTo(city: City){
            cityName.text = city.name.toString()
            temperature.text = city.main?.temp.toString()
            val url = "http://openweathermap.org/img/wn/${city.weather[0].icon}@4x.png"

            Glide.with(imagem.context).load(url).into(imagem)
        }
    }

    class WeatherDiffCallback: DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: City,
            newItem: City
        ): Boolean {
            return oldItem == newItem
        }
    }

    class MarginItemDecoration(private val spaceSize: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    top = spaceSize
                }
                left = spaceSize
                right = spaceSize
                bottom = spaceSize
            }
        }
    }
}

