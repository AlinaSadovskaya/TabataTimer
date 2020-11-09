package com.lab2.tabatatimer.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TimerModel {
    @PrimaryKey(autoGenerate = true)
    public int Id;
    public String Name;
    public int Preparation;
    public int WorkTime;
    public int RestTime;
    public int Cycles;
    public int Sets;
    public int RestSets;
    public int Color;
}
