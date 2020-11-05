package com.lab2.tabatatimer;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TimerModel.class}, version = 1)
public abstract class DataBaseHelper extends RoomDatabase {
    public abstract TimerDao timerDao();
}