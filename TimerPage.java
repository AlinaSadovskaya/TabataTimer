package com.lab2.tabatatimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TimerPage extends AppCompatActivity {

    DataBaseHelper db;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_page);
        db = App.getInstance().getDatabase();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = (int)bundle.get("timerId");

        txt = findViewById(R.id.textView1);
        txt.setText(db.timerDao().getById(id).Name);
    }
}