package com.example.diyaa.datecalculator;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)

public class DateInterval extends AppCompatActivity {

    private EditText fromEditText, toEditText;
    private TextView resultDate;
    private String fromDateString, toDateString;


    ArrayList<DataModel> dataModels;
    ListView listView;
    private DateIntervalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_interval);
        initialize();
    }

    private void initialize() {
        fromEditText = (EditText) findViewById(R.id.from_date_editText);
        fromEditText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });

        toEditText = (EditText) findViewById(R.id.to_date_editText);
        toEditText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });

        resultDate = (TextView) findViewById(R.id.result_date_textView);

        listView = (ListView) findViewById(R.id.date_interval_list);

        dataModels = new ArrayList<>();


        adapter = new DateIntervalAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);
    }

    public void getFromDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("DATE", 1);
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(),
                getString(R.string.from_date_picker));
    }

    public void getToDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("DATE", 2);
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(),
                getString(R.string.to_date_picker));
    }

    public void processFromDatePickerResult(int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear + 1;
        String month_string = "" + month;
        String day_string = "" + dayOfMonth;
        String year_string = Integer.toString(year);


        if (month < 10) {

            month_string = "0" + month;
        }
        if (dayOfMonth < 10) {

            day_string = "0" + dayOfMonth;
        }
        fromDateString = year_string + "-" + month_string + "-" + day_string;
        fromEditText.setText(fromDateString);
    }


    public void processToDatePickerResult(int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear + 1;
        String month_string = "" + month;
        String day_string = "" + dayOfMonth;
        String year_string = Integer.toString(year);


        if (month < 10) {
            month_string = "0" + month;
        }
        if (dayOfMonth < 10) {
            day_string = "0" + dayOfMonth;
        }

        toDateString = year_string + "-" + month_string + "-" + day_string;
        toEditText.setText(toDateString);
    }

    private String differenceBetweenTowDates() {
        LocalDate fromDate = LocalDate.parse(fromDateString);
        LocalDate toDate = LocalDate.parse(toDateString);

        Period diffInDate = Period.between(fromDate, toDate);

        long diffInDays = ChronoUnit.DAYS.between(fromDate, toDate);
        long diffInMonths = ChronoUnit.MONTHS.between(fromDate, toDate);
        long diffInYears = ChronoUnit.YEARS.between(fromDate, toDate);
        long diffInWeeks = ChronoUnit.WEEKS.between(fromDate, toDate);
        long diffInHours = diffInDays * 24;
        long diffInMins = diffInHours * 60;
        long diffInSeconds = diffInMins * 60;

        if (dataModels.size() != 0) dataModels.clear();

        fillArrayListWithData(dataModels, diffInYears, diffInMonths, diffInWeeks, diffInDays, diffInHours, diffInMins, diffInSeconds);

        String resultDate
                = diffInDate.getYears() + " years, "
                + diffInDate.getMonths() + " months, "
                + diffInDate.getDays() + " days";
        return resultDate;
    }

    public void getDateInterval(View view) {
        resultDate.setText(differenceBetweenTowDates());
    }

    private ArrayList<DataModel> fillArrayListWithData(ArrayList<DataModel> array, long years, long month, long weeks, long days, long hours, long mins, long seconds) {
        array.add(new DataModel("Years", years));
        array.add(new DataModel("Months", month));
        array.add(new DataModel("Weeks", weeks));
        array.add(new DataModel("Days", days));
        array.add(new DataModel("Hours", hours));
        array.add(new DataModel("Minutes", mins));
        array.add(new DataModel("Seconds", seconds));

        return array;
    }

    //Model
    public static class DataModel {

        String section;
        long value;


        public DataModel(String name, long value) {
            this.section = name;
            this.value = value;
        }

        public String getName() {
            return section;
        }

        public long getType() {
            return value;
        }
    }
}
