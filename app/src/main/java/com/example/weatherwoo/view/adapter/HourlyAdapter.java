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
import com.example.weatherwoo.commons.WeatherUtils;
import com.example.weatherwoo.model.HourlyData;
import com.google.android.material.textview.MaterialTextView;

import java.sql.Time;
import java.util.List;
import java.util.Locale;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder> {
    private Context context;
    private List<HourlyData> hourlyForecast;

    public HourlyAdapter(List<HourlyData> hourlyForecast) {
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
        HourlyData hourly = hourlyForecast.get(position);

        int icon = WeatherUtils.getweatherIcon(hourly.getIcon());
        int lightText = context.getResources().getColor(R.color.lightText);
        int darkText = context.getResources().getColor(R.color.darkText);
        int medText = context.getResources().getColor(R.color.medText);
        int lightBackground = context.getResources().getColor(R.color.clearSkyDay);
        int medBackground = context.getResources().getColor(R.color.darkSkyDay);
        int darkBackground = context.getResources().getColor(R.color.nightSky);
        switch (icon) {
            case R.drawable.cloudy:
            case R.drawable.fog:
            case R.drawable.clear_day:
                holder.background.setBackgroundColor(lightBackground);
                holder.tvTemp.setTextColor(darkText);
                holder.tvTime.setTextColor(darkText);
                break;
            case R.drawable.snow:
            case R.drawable.partly_cloudy_night:
            case R.drawable.sleet:
            case R.drawable.hail:
            case R.drawable.partly_cloudy_day:
                holder.background.setBackgroundColor(medBackground);
                holder.tvTemp.setTextColor(medText);
                holder.tvTime.setTextColor(medText);
                break;
            case R.drawable.rain:
            case R.drawable.clear_night:
                holder.background.setBackgroundColor(darkBackground);
                holder.tvTemp.setTextColor(lightText);
                holder.tvTime.setTextColor(lightText);
                break;
        }

        Glide.with(context).load(icon).fitCenter().into(holder.ivWeatherIcon);

        Time time = new Time(hourly.getTime());
        String timeAsString = String.format(Locale.US, "%tr", time);

        long hourlyTemp = Math.round(hourly.getTemperature());
        String hourlyTemps = String.format(Locale.US, "% d\u00B0", hourlyTemp);


        holder.tvTime.setText(timeAsString);
        holder.tvTemp.setText(hourlyTemps);


    }

    @Override
    public int getItemCount() {
        return hourlyForecast.size();
    }

    class HourlyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivWeatherIcon;
        MaterialTextView tvTime, tvTemp;
        View background;


        HourlyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivWeatherIcon = itemView.findViewById(R.id.ivWeatherIcon);
            tvTemp = itemView.findViewById(R.id.tv_temp);
            tvTime = itemView.findViewById(R.id.tvTime);
            background = itemView.findViewById(R.id.hourly_background);
        }
    }

}
