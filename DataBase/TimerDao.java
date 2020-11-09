package com.lab2.tabatatimer.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lab2.tabatatimer.Model.TimerModel;

import java.util.List;


@Dao
public interface TimerDao {


    @Query("SELECT * FROM timerModel")
    List<TimerModel> getAll();

    @Query("SELECT * FROM timerModel WHERE id = :id")
    TimerModel getById(long id);

    @Query("SELECT * FROM timerModel WHERE name = :name")
    TimerModel getByName(String name);

    @Insert
    void insert(TimerModel timerModel);

    @Update
    void update(TimerModel timerModel);

    @Delete
    void delete(TimerModel timerModel);

    @Query("DELETE FROM timerModel")
    void DeleteAll();
}

