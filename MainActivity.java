package com.lab2.tabatatimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper db;
    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = App.getInstance().getDatabase();


        lst = findViewById(R.id.ListTimer);
        TimerAdapter adapter = new TimerAdapter(this, R.layout.timer_list
                , db.timerDao().getAll(), db);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                TimerModel training = (TimerModel) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(),TimerPage.class);
                intent.putExtra("timerId", training.Id);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonAddTimer).setOnClickListener(i -> {
            Intent intent = new Intent(getApplicationContext(), CreateTimer.class);
            intent.putExtra("trainingId", new int[]{0,0});
            startActivity(intent);
        });
    }
}