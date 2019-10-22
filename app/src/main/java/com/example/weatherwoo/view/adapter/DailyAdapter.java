package com.example.weatherwoo.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherwoo.R;
import com.example.weatherwoo.model.Daily;
import com.example.weatherwoo.model.DailyDatum;
import com.google.android.material.textview.MaterialTextView;

import java.sql.Time;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {
    private Context context;
    private Daily dailyForecast;

    public DailyAdapter(Daily dailyForecast) {
        this.dailyForecast = dailyForecast;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View theView = LayoutInflater.from(context)
                .inflate(R.layout.daily_item, parent, false);

        return new DailyViewHolder(theView);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {
        DailyDatum forecast = dailyForecast.getData().get(position);

        Log.e("Icon value", "Icon value was: " + forecast.getIcon());
        switch (forecast.getIcon()) {
            case "cloudy":
                Glide.with(context).load(R.drawable.cloudy).fitCenter().into(holder.weatherIcon);
                break;
            case "snow":
                Glide.with(context).load(R.drawable.snow).fitCenter().into(holder.weatherIcon);
                break;
            case "rain":
                Glide.with(context).load(R.drawable.rain).fitCenter().into(holder.weatherIcon);
                break;
            case "partly-cloudy-day":
                Glide.with(context).load(R.drawable.partly_cloudy_day).fitCenter().into(holder.weatherIcon);
                break;
            case "partly-cloudy-night":
                Glide.with(context).load(R.drawable.partly_cloudy_night).fitCenter().into(holder.weatherIcon);
                break;
            case "sleet":
                Glide.with(context).load(R.drawable.sleet).fitCenter().into(holder.weatherIcon);
                break;
            case "fog":
                Glide.with(context).load(R.drawable.fog).fitCenter().into(holder.weatherIcon);
                break;
            case "clear-day":
                Glide.with(context).load(R.drawable.clear_day).fitCenter().into(holder.weatherIcon);
                break;
            case "clear-night":
                Glide.with(context).load(R.drawable.clear_night).fitCenter().into(holder.weatherIcon);
                break;
            case "hail":
                Glide.with(context).load(R.drawable.hail).fitCenter().into(holder.weatherIcon);
                break;
        }

        long timeAsLong = forecast.getTime();
        Time time = new Time(timeAsLong);
        String timeAsString = String.format("HH:mm", time);
        Double high = forecast.getTemperatureHigh();
        Double low = forecast.getTemperatureLow();

        Log.e("Time Value", "The time value is: " + timeAsString);
        Log.e("High Value", "The high for the day is: " + high);
        Log.e("Low Value", "The low for the day is: " + low);

        holder.tvTime.setText(timeAsString);
        holder.tvHigh.setText(String.valueOf(high));
        holder.tvLow.setText(String.valueOf(low));

    }

    @Override
    public int getItemCount() {
        return dailyForecast.getData().size();
    }

    class DailyViewHolder extends RecyclerView.ViewHolder {

        ImageView weatherIcon;
        MaterialTextView tvTime, tvHigh, tvLow;

        DailyViewHolder(@NonNull View itemView) {
            super(itemView);

            weatherIcon = itemView.findViewById(R.id.ivWeatherIcon);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvHigh = itemView.findViewById(R.id.tvHigh);
            tvLow = itemView.findViewById(R.id.tvLow);
        }

    }
}
