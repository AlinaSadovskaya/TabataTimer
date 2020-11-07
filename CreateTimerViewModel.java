package com.lab2.tabatatimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateTimerViewModel extends ViewModel {
    private MutableLiveData<String> Name = new MutableLiveData<>(null);
    private MutableLiveData<Integer> Preparation = new MutableLiveData<>(10);
    private MutableLiveData<Integer> WorkTime = new MutableLiveData<>(20);
    private MutableLiveData<Integer> RestTime = new MutableLiveData<>(10);
    private MutableLiveData<Integer> RestSets = new MutableLiveData<>(10);
    private MutableLiveData<Integer> Cycles = new MutableLiveData<>(2);
    private MutableLiveData<Integer> Sets = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> Color = new MutableLiveData<>(-16777216);


    public MutableLiveData<String> getName() {
        return Name;
    }

    public MutableLiveData<Integer> getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color.setValue(color);
    }

    public void setPreparation(MutableLiveData<Integer> preparation) {
        Preparation = preparation;
    }

    public void setWorkTime(MutableLiveData<Integer> workTime) {
        WorkTime = workTime;
    }

    public void setRestTime(MutableLiveData<Integer> restTime) {
        RestTime = restTime;
    }

    public void setRestSets(MutableLiveData<Integer> restSets) {
        RestSets = restSets;
    }

    public void setCycles(MutableLiveData<Integer> cycles) {
        Cycles = cycles;
    }

    public void setName(String name) {
        Name.setValue(name);
    }

    public MutableLiveData<Integer> getPreparation() {
        return Preparation;
    }

    public void setIncrementPreparation() {
        Preparation.setValue(Preparation.getValue() + 1);
    }

    public void setDecrementPreparation() {
        if(Preparation.getValue() != 0) {
            Preparation.setValue(Preparation.getValue() - 1);
        }
    }

    public void setPrep(int prep) {
        Preparation.setValue(prep);
    }

    public MutableLiveData<Integer> getWorkTime() {
        return WorkTime;
    }

    public void setIncrementWorkTime( ) {
        WorkTime.setValue(WorkTime.getValue() + 1);
    }

    public void setDecrementWorkTime() {
        if(WorkTime.getValue() != 0) {
            WorkTime.setValue(WorkTime.getValue() - 1);
        }
    }

    public void setWork(int work) {
        WorkTime.setValue(work);
    }

    public MutableLiveData<Integer> getRestTime() {
        return RestTime;
    }

    public void setIncrementRestTime() {
        RestTime.setValue(RestTime.getValue() + 1);
    }

    public void setDecrementRestTime() {
        if(RestTime.getValue() != 0) {
            RestTime.setValue(RestTime.getValue() - 1);
        }
    }

    public void setRest(int rest) {
        RestTime.setValue(rest);
    }

    public MutableLiveData<Integer> getRestSets() {
        return RestSets;
    }

    public void setIncrementRestSets() {
        RestSets.setValue(RestSets.getValue() + 1);
    }

    public void setDecrementRestSets() {
        if(RestSets.getValue() != 0) {
            RestSets.setValue(RestSets.getValue() - 1);
        }
    }

    public void setRestSets(int calm) {
        RestSets.setValue(calm);
    }

    public MutableLiveData<Integer> getCycles() {
        return Cycles;
    }

    public void setIncrementCycle() {
        Cycles.setValue(Cycles.getValue() + 1);
    }

    public void setDecrementCycle() {
        if(Cycles.getValue() != 0) {
            Cycles.setValue(Cycles.getValue() - 1);
        }
    }

    public void setCycle(int cycle) {
        Cycles.setValue(cycle);
    }

    public MutableLiveData<Integer> getSets() {
        return Sets;
    }

    public void setSets(int sets) {
        Sets.setValue(sets);
    }

    public void setIncrementSets() {
        Sets.setValue(Sets.getValue() + 1);
    }

    public void setDecrementSets() {
        if(Sets.getValue() != 1) {
            Sets.setValue(Sets.getValue() - 1);
        }
    }
}
