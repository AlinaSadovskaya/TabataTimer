package com.lab2.tabatatimer.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lab2.tabatatimer.Model.TimerModel;

@Database(entities = {TimerModel.class}, version = 1)
public abstract class DataBaseHelper extends RoomDatabase {
    public abstract TimerDao timerDao();
}