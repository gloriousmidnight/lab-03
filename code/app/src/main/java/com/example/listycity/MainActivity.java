package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {"Edmonton", "Vancouver", "Toronto"};
        String[] provinces = {"AB", "BC", "ON"};

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // The following was created with the assistance of OpenAI's ChatGPT to help
        //me (Cassie Burke) with specifics of Java syntax and how fragments work,
        //particularly so I could make sure I was opening the fragment correctly
        //when a list item is clicked and the modifications necessary for an "edit mode"
        //to work using the same add city framework previously made (January 21st, 2026)
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City selectedCity = dataList.get(position); //get city that was clicked
            AddCityFragment fragment = AddCityFragment.newInstance(selectedCity);
            fragment.show(getSupportFragmentManager(), "Edit City");
        });

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddCityFragment().show(getSupportFragmentManager(), "Add City");
            }
        });
    }

    @Override
    public void addCity(City city) {
        if (!dataList.contains(city)) {
            dataList.add(city);
        }
        cityAdapter.notifyDataSetChanged();
    }
}