package com.example.weatherwoo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherwoo.R;
import com.example.weatherwoo.model.Hourly;
import com.example.weatherwoo.model.HourlyDatum;
import com.google.android.material.textview.MaterialTextView;

import java.sql.Time;
import java.text.SimpleDateFormat;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder> {
    Context context;
    Hourly hourlyForecast;

    public HourlyAdapter(Hourly hourlyForecast) {
        this.hourlyForecast = hourlyForecast;
    }

    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View theView = LayoutInflater.from(context)
                .inflate(R.layout.hourly_item, parent, false);

        return new HourlyViewHolder(theView);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyViewHolder holder, int position) {
        HourlyDatum hourly = hourlyForecast.getData().get(position);

        switch (hourly.getIcon()) {
            case "cloudy":
                Glide.with(context).load(R.drawable.cloudy).fitCenter().into(holder.ivWeatherIcon);
                break;
            case "snow":
                Glide.with(context).load(R.drawable.snow).fitCenter().into(holder.ivWeatherIcon);
                break;
            case "rain":
                Glide.with(context).load(R.drawable.rain).fitCenter().into(holder.ivWeatherIcon);
                break;
            case "partly-cloudy-day":
                Glide.with(context).load(R.drawable.partly_cloudy_day).fitCenter().into(holder.ivWeatherIcon);
                break;
            case "partly-cloudy-night":
                Glide.with(context).load(R.drawable.partly_cloudy_night).fitCenter().into(holder.ivWeatherIcon);
                break;
            case "sleet":
                Glide.with(context).load(R.drawable.sleet).fitCenter().into(holder.ivWeatherIcon);
                break;
            case "fog":
                Glide.with(context).load(R.drawable.fog).fitCenter().into(holder.ivWeatherIcon);
                break;
            case "clear-day":
                Glide.with(context).load(R.drawable.clear_day).fitCenter().into(holder.ivWeatherIcon);
                break;
            case "clear-night":
                Glide.with(context).load(R.drawable.clear_night).fitCenter().into(holder.ivWeatherIcon);
                break;
            case "hail":
                Glide.with(context).load(R.drawable.hail).fitCenter().into(holder.ivWeatherIcon);
                break;
        }

        Time time = new Time(hourly.getTime());
        String timeAsString = SimpleDateFormat.getTimeInstance().format(time);
        String hourlyTemps = String.format("", hourly.getTemperature());


        holder.tvTime.setText(timeAsString);
        holder.tvTemp.setText(hourlyTemps);


    }

    @Override
    public int getItemCount() {
        return hourlyForecast.getData().size();
    }

    public class HourlyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivWeatherIcon;
        MaterialTextView tvTime, tvTemp;


        public HourlyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivWeatherIcon = itemView.findViewById(R.id.ivWeatherIcon);
            tvTemp = itemView.findViewById(R.id.tv_temp);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
