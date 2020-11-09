package com.lab2.tabatatimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lab2.tabatatimer.DataBase.DataBaseHelper;
import com.lab2.tabatatimer.Model.TimerModel;

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
                Intent intent = new Intent(getApplicationContext(), TrainingTimer.class);
                intent.putExtra("timerId", training.Id);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonAddTimer).setOnClickListener(i -> {
            Intent intent = new Intent(getApplicationContext(), CreateTimer.class);
            intent.putExtra("timerId", new int[]{0,0});
            startActivity(intent);
        });

        findViewById(R.id.SettingsButton).setOnClickListener(i -> {
            Intent Settings = new Intent(this, com.lab2.tabatatimer.Setting.Settings.class);
            startActivityForResult(Settings, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }
}