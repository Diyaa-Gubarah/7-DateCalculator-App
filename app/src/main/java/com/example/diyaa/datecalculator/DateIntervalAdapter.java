package com.example.diyaa.datecalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.diyaa.datecalculator.DateInterval.DataModel;
import com.example.diyaa.datecalculator.WorkDays.DataModelWork;

import java.util.ArrayList;

/**
 * Created by Diyaa on 9/16/2018.
 */

public class DateIntervalAdapter extends ArrayAdapter<DataModel> {
    private ArrayList<DataModel> dataSet;
    private ArrayList<DataModelWork> dataSetWork;
    Context mContext;


    public DateIntervalAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.date_interval_list_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    // View lookup cache
    private static class ViewHolder {
        TextView dateSection;
        TextView dateSectionValue;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.date_interval_list_item, parent, false);
            viewHolder.dateSection = (TextView) convertView.findViewById(R.id.date_section_textView);
            viewHolder.dateSectionValue = (TextView) convertView.findViewById(R.id.date_section_value_textView);
            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.dateSection.setText(dataModel.getName());
        viewHolder.dateSectionValue.setText("" + dataModel.getType());
        // Return the completed view to render on screen
        return convertView;
    }
}


