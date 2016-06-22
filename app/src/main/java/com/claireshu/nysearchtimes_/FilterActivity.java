package com.claireshu.nysearchtimes_;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.claireshu.nysearchtimes_.activities.SearchActivity;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

    public void returnFilters(View view) {
        Intent intent = new Intent(FilterActivity.this, SearchActivity.class);
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);

        String year = Integer.toString(datePicker.getYear());
        String month = Integer.toString(datePicker.getMonth());
        String day = Integer.toString(datePicker.getDayOfMonth());
        if (month.length() < 2) month = "0" + month;
        if (day.length() < 2) day = "0" + day;
        String dateFormatted = year + month + day;
        intent.putExtra("date", dateFormatted);

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
