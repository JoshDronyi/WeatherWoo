package com.example.weatherwoo.view.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherwoo.R;
import com.example.weatherwoo.commons.WeatherUtils;
import com.example.weatherwoo.model.DailyData;
import com.google.android.material.textview.MaterialTextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {
    private Context context;
    private List<DailyData> dailyForecast;

    public DailyAdapter(List<DailyData> dailyForecast) {
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
        DailyData forecast = dailyForecast.get(position);

        Log.e("Icon value", "Icon value was: " + forecast.getIcon());
        int icon = WeatherUtils.getweatherIcon(forecast.getIcon());
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
                holder.tvHigh.setTextColor(darkText);
                holder.tvLow.setTextColor(darkText);
                holder.tvTime.setTextColor(darkText);
                break;
            case R.drawable.snow:
            case R.drawable.partly_cloudy_night:
            case R.drawable.sleet:
            case R.drawable.hail:
            case R.drawable.partly_cloudy_day:
                holder.background.setBackgroundColor(medBackground);
                holder.tvHigh.setTextColor(medText);
                holder.tvLow.setTextColor(medText);
                holder.tvTime.setTextColor(medText);
                break;
            case R.drawable.rain:
            case R.drawable.clear_night:
                holder.background.setBackgroundColor(darkBackground);
                holder.tvHigh.setTextColor(lightText);
                holder.tvLow.setTextColor(lightText);
                holder.tvTime.setTextColor(lightText);
                break;
        }

        Glide.with(context).load(icon).fitCenter().into(holder.ivWeatherIcon);

        String timeAsString = getDayOfWeek(forecast.getTime());
        Double high = forecast.getTemperatureHigh();
        Double low = forecast.getTemperatureLow();

        Log.e("Time Value", "The time value is: " + timeAsString);
        Log.e("High Value", "The high for the day is: " + high);
        Log.e("Low Value", "The low for the day is: " + low);

        holder.tvTime.setText(timeAsString);


        holder.tvHigh.setText(formattedTemp(high));
        holder.tvLow.setText(formattedTemp(low));

    }

    String getDayOfWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);

        return DateFormat.format("EEEE", calendar).toString();

    }

    @Override
    public int getItemCount() {
        return dailyForecast.size();
    }

    class DailyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivWeatherIcon;
        MaterialTextView tvTime, tvHigh, tvLow;
        View background;

        DailyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivWeatherIcon = itemView.findViewById(R.id.ivWeatherIcon);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvHigh = itemView.findViewById(R.id.tvHigh);
            tvLow = itemView.findViewById(R.id.tvLow);
            background = itemView.findViewById(R.id.daily_background);
        }

    }

    private String formattedTemp(double temp) {

        long stringVal = Math.round(temp);
        return String.format(Locale.US, "% d\u00B0", stringVal);
    }
}
