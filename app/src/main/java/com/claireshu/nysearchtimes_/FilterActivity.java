package com.claireshu.nysearchtimes_;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.claireshu.nysearchtimes_.activities.SearchActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FilterActivity extends AppCompatActivity {
    Typeface font;
    ArrayAdapter spinnerNewsDeskAdapter;
    Spinner spNewsSpinner;
    Spinner spSortSpinner;
    ArrayAdapter spinnerSortAdapter;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        font = Typeface.createFromAsset(getAssets(), "fonts/sourcesanspro.otf");

        setStatusBarColor();
        setActionBarFont();

        Button btFilter = (Button) findViewById(R.id.btFilter);
        btFilter.setTypeface(font);

        String date = getIntent().getStringExtra("date");
        String newsDesk = getIntent().getStringExtra("news_desk");
        String sort = getIntent().getStringExtra("sort");

        setUpSpinners();

        if (date != null) {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4, 6));
            int day = Integer.parseInt(date.substring(6));
            datePicker = (DatePicker) findViewById(R.id.datePicker);
            datePicker.updateDate(year, month - 1, day);
        }

        if (newsDesk != null) {
            Log.d("position newsdesk", newsDesk);
            spNewsSpinner.setSelection(spinnerNewsDeskAdapter.getPosition(newsDesk));
        }

        if (sort != null) {
            spSortSpinner.setSelection(spinnerSortAdapter.getPosition(sort));
        }

    }

    public void returnFilters(View view) {
        Intent intent = new Intent(FilterActivity.this, SearchActivity.class);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

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

    private void setActionBarFont() {
        SpannableString s = new SpannableString("NYTimesSearch");
        com.claireshu.nysearchtimes_.TypefaceSpan typeface = new com.claireshu.nysearchtimes_.TypefaceSpan(this, "sourcesanspro.otf");
        s.setSpan(typeface, 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
    }

    private void setStatusBarColor() {
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.parseColor("#b9d3b0"));
        getWindow().setNavigationBarColor(Color.parseColor("#81BDA4"));
    }

    private void setUpSpinners() {
        // sets up news desk spinner
        spNewsSpinner = (Spinner) findViewById(R.id.spinner_news_desk);
        spNewsSpinner.getBackground().setColorFilter(Color.parseColor("#F6AA93"), PorterDuff.Mode.SRC_ATOP);

        ArrayList<String> newsDeskValues = new ArrayList<>();
        newsDeskValues.add("Arts");
        newsDeskValues.add("Culture");
        newsDeskValues.add("Food");
        newsDeskValues.add("Home");
        newsDeskValues.add("Movies");
        newsDeskValues.add("Politics");
        newsDeskValues.add("Science");
        newsDeskValues.add("Sports");
        newsDeskValues.add("Technology");

        spinnerNewsDeskAdapter = new ArrayAdapter(this, R.layout.spinner_style, newsDeskValues) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(StaticUtils.sTypeFace(getApplicationContext()));//Typeface for normal view

                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(StaticUtils.sTypeFace(getApplicationContext()));//Typeface for dropdown view
                ((TextView) v).setBackgroundColor(Color.parseColor("#BBfef3da"));
                return v;
            }

        };
        spinnerNewsDeskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNewsSpinner.setAdapter(spinnerNewsDeskAdapter);

        // sets up sort spinner
        spSortSpinner = (Spinner) findViewById(R.id.spinner_sort);
        spSortSpinner.getBackground().setColorFilter(Color.parseColor("#F6AA93"), PorterDuff.Mode.SRC_ATOP);

        ArrayList<String> sortValues = new ArrayList<>();
        sortValues.add("Newest");
        sortValues.add("Oldest");

        spinnerSortAdapter = new ArrayAdapter(this, R.layout.spinner_style, sortValues) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(StaticUtils.sTypeFace(getApplicationContext()));//Typeface for normal view

                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(StaticUtils.sTypeFace(getApplicationContext()));//Typeface for dropdown view
                ((TextView) v).setBackgroundColor(Color.parseColor("#BBfef3da"));
                return v;
            }

        };
        spinnerSortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSortSpinner.setAdapter(spinnerSortAdapter);

    }
}