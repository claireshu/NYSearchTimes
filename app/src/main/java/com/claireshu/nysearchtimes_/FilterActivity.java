package com.claireshu.nysearchtimes_;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.claireshu.nysearchtimes_.activities.SearchActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

    public void returnFilters(View view) {
        Intent intent = new Intent(FilterActivity.this, SearchActivity.class);

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        String date = dateFormat.format(calendar.getTime());

        intent.putExtra("date", date);

        Spinner spNewsSpinner = (Spinner) findViewById(R.id.spinner_news_desk);
        Spinner spSortSpinner = (Spinner) findViewById(R.id.spinner_sort);
        String newsDesk = spNewsSpinner.getSelectedItem().toString();
        String sort = spSortSpinner.getSelectedItem().toString();
        intent.putExtra("news_desk", newsDesk);
        intent.putExtra("sort", sort);

        setResult(RESULT_OK, intent);
        finish();
    }
}
