package com.dam.ehedeihega.masacorporal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] listViewOptions = new String[] {
                getString(R.string.main_calculate),
                getString(R.string.main_getdata)
        };

        final HashMap<String, Class<? extends Activity>> activities = new HashMap<>();
        activities.put(listViewOptions[0], CalculateActivity.class);
        activities.put(listViewOptions[1], GetDataActivity.class);

        ListAdapter listAdapter = new ArrayAdapter<String>(this, R.layout.item_options, listViewOptions);
        final ListView listView = findViewById(R.id.listview_main_options);
        listView.setAdapter(listAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = position;
                String option = (String) listView.getItemAtPosition(pos);

                Intent intent = new Intent(getApplicationContext(), (Class<Activity>) activities.get(option));
                startActivity(intent);

            }
        });


    }
}
