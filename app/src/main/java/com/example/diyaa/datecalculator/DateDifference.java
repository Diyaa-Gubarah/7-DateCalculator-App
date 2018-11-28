package com.example.diyaa.datecalculator;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DateDifference extends AppCompatActivity {

    private EditText yearEditText, monthEditText, daysEditText;
    private TextView currentDateTextView, resultDateTextView;
    private LocalDate currentDate ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_difference);
        initialize();
    }

    private void initialize() {
        yearEditText = (EditText) findViewById(R.id.years_editText);
        monthEditText = (EditText) findViewById(R.id.month_editText);
        daysEditText = (EditText) findViewById(R.id.days_editText);
        currentDateTextView = (TextView) findViewById(R.id.current_date_textView);
        resultDateTextView = (TextView) findViewById(R.id.result_date_textView);
        currentDate = LocalDate.now();
        currentDateTextView.setText(currentDate.toString());
    }

    public void getDate(View view) {
        setDateDays(currentDate,
                Integer.parseInt(daysEditText.getText().toString().trim()),
                Integer.parseInt(monthEditText.getText().toString().trim()),
                Integer.parseInt(yearEditText.getText().toString().trim()));
    }

    //    return LocalDate after change his days and send it to setDateMonth().
    private LocalDate setDateDays(LocalDate date, int days, int month, int year) {
        LocalDate newdate;
        newdate = days < 0
                ? date.minusDays(Math.abs(days))
                : date.plusDays(days);

        return setDateMonth(newdate, month, year);
    }

    //    return LocalDate after change his months and send it to setDateYear().
    private LocalDate setDateMonth(LocalDate date, int month, int year) {
        LocalDate newdate;
        newdate = month < 0
                ? date.minusMonths(Math.abs(month))
                : date.plusMonths(month);

        return setDateYear(newdate, year);
    }

    //    return newLocalDate after change his years.
    private LocalDate setDateYear(LocalDate date, int year) {
        LocalDate newdate;
        newdate = year < 0
                ? date.minusYears(Math.abs(year))
                : date.plusYears(year);
        formatDate(newdate);
        return newdate;
    }

    //    recive newLocalDate from setDateYear() and show it to the user.
    @RequiresApi(api = Build.VERSION_CODES.O)

    private void formatDate(LocalDate date) {
        resultDateTextView.setText(date.getDayOfWeek()
                + ", " + date.getDayOfMonth() + " "
                + date.getMonth()
                + " " + date.getYear());
    }
}
