package com.example.virus.weatherprojectfinalversion.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.virus.weatherprojectfinalversion.R;
import com.example.virus.weatherprojectfinalversion.customClasses.Tools;
import com.example.virus.weatherprojectfinalversion.models.children.TimeWeather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ListViewListenerAdapter extends ArrayAdapter
{
    String date;
    private List<TimeWeather> list;
    int resource;
    private LayoutInflater layoutInflater;
    ListView listView;
    public ListViewListenerAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        list = objects;
        notifyDataSetChanged();
        this.resource = resource;
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = layoutInflater.inflate(resource, null);
        fillListWithDescriptions(convertView, position);

        return convertView;
    }
    private void fillListWithDescriptions(View convertView, int position) {
        DateFormat df = new SimpleDateFormat("EEE, MMM d");
        date = df.format(Calendar.getInstance().getTime());
        TextView day = convertView.findViewById(R.id.day);
        TextView description = convertView.findViewById(R.id.description);
        TextView temp = convertView.findViewById(R.id.temp);
        ImageView imageView = convertView.findViewById(R.id.weather_image);
        int indexOfDay = 0;
        final String[] daysOfWeek = {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday"
        };
        for (int i = 0; i <daysOfWeek.length ; i++) {
            if(daysOfWeek[i].contains(date.toLowerCase()))
                indexOfDay = i;
        }
        day.setText(daysOfWeek[(position+indexOfDay) % 7]);
        description.setText(list.get(position > 0 ? position-1 : position).
                getWeatherDescriptionList().get(0).getDescription());
        Tools.chooseImage(description,imageView);

        temp.setText(String.format("%.0f°", list.get(position > 0 ? position-1 : position).getTemperature().getTemperature() - 273));


    }
}

