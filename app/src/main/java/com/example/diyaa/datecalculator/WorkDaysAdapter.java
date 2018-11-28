package com.example.diyaa.datecalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.diyaa.datecalculator.WorkDays.DataModelWork;

import java.util.ArrayList;

/**
 * Created by Diyaa on 9/17/2018.
 */

public class WorkDaysAdapter extends ArrayAdapter<DataModelWork> {

    private ArrayList<DataModelWork> dataSetWork;
    Context mContext;


    public WorkDaysAdapter(ArrayList<DataModelWork> data, Context context) {
        super(context, R.layout.work_days_list_item, data);
        this.dataSetWork = data;
        this.mContext = context;

    }

    // View lookup cache
    private static class ViewHolder {
        TextView weekName;
        TextView weekValue;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModelWork dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.work_days_list_item, parent, false);
            viewHolder.weekName = (TextView) convertView.findViewById(R.id.week_name_textView);
            viewHolder.weekValue = (TextView) convertView.findViewById(R.id.week_value_textView);
            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.weekName.setText(dataModel.getName());
        viewHolder.weekValue.setText("" + dataModel.getType());
        // Return the completed view to render on screen
        return convertView;
    }
}


