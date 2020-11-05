package com.lab2.tabatatimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TrainingTimer extends AppCompatActivity {

    private DataBaseHelper db;

    TextView namePart;
    TextView timePart;
    ListView allParts;
    Button btnStart;
    Button btnStop;

    Timer timer;
    Runnable Timer_Tick;

    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> parts;
    ArrayList<Integer> times;

    int t = 10;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training);

        db = App.getInstance().getDatabase();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = (int)bundle.get("timerId");

        TimerModel timerModel = db.timerDao().getById(id);
        CreateItemSequence(timerModel);
        namePart = findViewById(R.id.partName);
        timePart = findViewById(R.id.partTime);
        allParts = findViewById(R.id.allParts);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        allParts.setAdapter(adapter);

        int size = parts.size()-1;
        Timer_Tick = () -> {
            if(times.get(counter) < 0){
                if(counter < size){
                    counter++;
                }
                else {
                    timer.cancel();
                    timer = null;
                }
            }
            int value = times.get(counter);
            String temp = Integer.toString(value);
            timePart.setText(temp);
            namePart.setText(parts.get(counter));
            if(counter < size){
                value--;
            }
            else {
                timePart.setText("Тренировка завершена");
            }
            times.set(counter, value);
        };

        btnStart.setOnClickListener(i -> {
            if (timer != null) {
                timer.cancel();
            }
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    TimerMethod();
                    int d = times.get(counter);
                }
            }, 0, 1000);
        });

        btnStop.setOnClickListener(i -> {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        });

        allParts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TimerSequence(timerModel);
                counter = position;
            }
        });
    }

    private void TimerMethod() {
        this.runOnUiThread(Timer_Tick);
    }

    private void TimerSequence(TimerModel timerModel){
        String[] names = {"Работа","Отдых","Подготовиться","Отдых между подходами","Финиш"};
        parts = new ArrayList<>();
        times = new ArrayList<>();
        parts.add(names[2]);
        times.add(timerModel.Preparation);

        for(int i = 0; i < timerModel.Sets; i++) {
            for (int j = 0; j < timerModel.Cycles; j++) {
                parts.add(names[0]);
                parts.add(names[1]);
                times.add(timerModel.WorkTime);
                times.add(timerModel.RestTime);
            }
            parts.add(names[3]);
            times.add(timerModel.RestSets);
        }
        parts.add(names[4]);
        times.add(0);
    }

    private void CreateItemSequence(TimerModel timerModel){
        String[] names = {"Работа","Отдых","Подготовиться","Отдых между подходами","Финиш"};
        parts = new ArrayList<>();
        times = new ArrayList<>();
        items.add(names[2] + " : " + timerModel.Preparation);
        parts.add(names[2]);
        times.add(timerModel.Preparation);

        for(int i = 0; i < timerModel.Sets; i++) {
            for (int j = 0; j < timerModel.Cycles; j++) {
                items.add(names[0] + " : " + timerModel.WorkTime);
                items.add(names[1] + " : " + timerModel.RestTime);
                parts.add(names[0]);
                parts.add(names[1]);
                times.add(timerModel.WorkTime);
                times.add(timerModel.RestTime);
            }
            items.add(names[3] + " : " + timerModel.RestSets);
            parts.add(names[3]);
            times.add(timerModel.RestSets);
        }
        items.add(names[4]);
        parts.add(names[4]);
        times.add(0);
    }
}