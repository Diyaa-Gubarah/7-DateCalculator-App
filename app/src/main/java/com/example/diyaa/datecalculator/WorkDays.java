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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class WorkDays extends AppCompatActivity {
    private EditText fromEditText, toEditText;
    private TextView weekendDaysTextView, workdaysTextView;

    private String fromDateString, toDateString;

    ArrayList<DataModelWork> dataModelWorks;
    ListView listView;
    private WorkDaysAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_days);
        initialize();
    }

    private void initialize() {
        weekendDaysTextView = (TextView) findViewById(R.id.weekend_days_value_textView);
        workdaysTextView = (TextView) findViewById(R.id.work_days_value_textView);
        fromEditText = (EditText) findViewById(R.id.from_date_work_editText);

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

        toEditText = (EditText) findViewById(R.id.to_date_work_editText);

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

        listView = (ListView) findViewById(R.id.work_date_list);

        dataModelWorks = new ArrayList<>();

        adapter = new WorkDaysAdapter(dataModelWorks, getApplicationContext());

        listView.setAdapter(adapter);

    }

    public void getFromDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("DATE", 3);
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(),
                getString(R.string.from_date_picker));
    }

    public void getToDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("DATE", 4);
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

    public int[] workDaysAndWeekendDays() {
        LocalDate fromDate = LocalDate.parse(fromDateString);
        LocalDate toDate = LocalDate.parse(toDateString);

        long totalPeriodInDaysUnits = ChronoUnit.DAYS.between(fromDate, toDate);

        int[] value = new int[2];
        int friTracker = 0,
                satTracker = 0,
                sunTracker = 0,
                monTracker = 0,
                tueTracker = 0,
                wedTracker = 0,
                thuTracker = 0;

        for (int index = 0; index <= totalPeriodInDaysUnits; index++) {
            switch (fromDate.plusDays(index).getDayOfWeek()) {
                case FRIDAY:
                    friTracker += 1;
                    break;
                case SATURDAY:
                    satTracker += 1;
                    break;
                case SUNDAY:
                    sunTracker += 1;
                    break;
                case MONDAY:
                    monTracker += 1;
                    break;
                case TUESDAY:
                    tueTracker += 1;
                    break;
                case WEDNESDAY:
                    wedTracker += 1;
                    break;
                case THURSDAY:
                    thuTracker += 1;
                    break;
            }
        }

        value[0] = friTracker;
        value[1] = (int) totalPeriodInDaysUnits;
        if (dataModelWorks.size() != 0) dataModelWorks.clear();

        fillArrayListWithData(dataModelWorks,
                sunTracker,
                monTracker,
                tueTracker,
                wedTracker,
                thuTracker,
                friTracker,
                satTracker);

        return value;
    }

    private ArrayList<DataModelWork> fillArrayListWithData(ArrayList<DataModelWork> array, int sunTracker,
                                                           int monTracker, int tueTracker, int wedTracker, int thuTracker, int friTracker,
                                                           int satTracker) {
        array.add(new DataModelWork("Sunday", sunTracker));
        array.add(new DataModelWork("Monday", monTracker));
        array.add(new DataModelWork("Tuesday", tueTracker));
        array.add(new DataModelWork("Wednesday", wedTracker));
        array.add(new DataModelWork("Thursday", thuTracker));
        array.add(new DataModelWork("Friday", friTracker));
        array.add(new DataModelWork("Saturday", satTracker));
        dataModelWorks = array;
        adapter.notifyDataSetChanged();  //very Important Method
        return dataModelWorks;
    }

    public void getWeekendAndWorkDays(View view) {
        weekendDaysTextView.setText("" + workDaysAndWeekendDays()[0]);
        workdaysTextView.setText("" + (workDaysAndWeekendDays()[1] + 1 - workDaysAndWeekendDays()[0]));
    }

    //Model
    public static class DataModelWork {

        String weekName;
        long weekValue;


        public DataModelWork(String name, long value) {
            this.weekName = name;
            this.weekValue = value;
        }

        public String getName() {
            return weekName;
        }

        public long getType() {
            return weekValue;
        }
    }
}
