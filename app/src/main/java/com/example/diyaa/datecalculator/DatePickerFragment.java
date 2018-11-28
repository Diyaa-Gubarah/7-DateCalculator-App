package com.example.diyaa.datecalculator;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    static final int START_DATE = 1;
    static final int END_DATE = 2;
    static final int START_DATE_WORK_DAYS = 3;
    static final int END_DATE_WORK_DAYS = 4;

    private int mChosenDate;

    int cur = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if (cur == START_DATE) {
            DateInterval dateIntervalActivity = (DateInterval) getActivity();
            dateIntervalActivity.processFromDatePickerResult(year, month, dayOfMonth);
        } else if (cur == END_DATE) {
            DateInterval dateIntervalActivity = (DateInterval) getActivity();
            dateIntervalActivity.processToDatePickerResult(year, month, dayOfMonth);
        } else if (cur == START_DATE_WORK_DAYS) {
            WorkDays workDaysActivity = (WorkDays) getActivity();
            workDaysActivity.processFromDatePickerResult(year, month, dayOfMonth);
        } else {
            WorkDays workDaysActivity = (WorkDays) getActivity();
            workDaysActivity.processToDatePickerResult(year, month, dayOfMonth);
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mChosenDate = bundle.getInt("DATE", 1);
        }


        switch (mChosenDate) {

            case START_DATE:
                cur = START_DATE;
                // Create a new instance of DatePickerDialog and return it.
                return new DatePickerDialog(getActivity(), this, year, month, day);

            case END_DATE:
                cur = END_DATE;
                // Create a new instance of DatePickerDialog and return it.
                return new DatePickerDialog(getActivity(), this, year, month, day);

            case START_DATE_WORK_DAYS:
                cur = START_DATE_WORK_DAYS;
                return new DatePickerDialog(getActivity(), this, year, month, day);


            case END_DATE_WORK_DAYS:
                cur = END_DATE_WORK_DAYS;
                return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        return null;
    }
}
