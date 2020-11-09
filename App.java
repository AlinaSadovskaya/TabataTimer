package com.lab2.tabatatimer;

import android.app.Application;

import androidx.room.Room;

import com.lab2.tabatatimer.DataBase.DataBaseHelper;


public class App extends Application {
    public static App instance;

    private DataBaseHelper database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, DataBaseHelper.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        if (instance==null)
        {

        }
        return instance;
    }

    public DataBaseHelper getDatabase() {
        return database;
    }
}
