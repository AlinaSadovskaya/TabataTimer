package com.lab2.tabatatimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;

import com.divyanshu.colorseekbar.ColorSeekBar;

public class CreateTimer extends AppCompatActivity {

    private DataBaseHelper db;
    private CreateTimerViewModel viewModel;

    Button btnPrepPlus;
    Button btnPrepMinus;
    Button btnWorkPlus;
    Button btnWorkMinus;
    Button btnRestPlus;
    Button btnRestMinus;
    Button btnCyclePlus;
    Button btnCycleMinus;
    Button btnSetPlus;
    Button btnSetMinus;
    Button btnCalmPlus;
    Button btnCalmMinus;

    EditText inputName;
    EditText inputPrep;
    EditText inputWork;
    EditText inputRest;
    EditText inputCycle;
    EditText inputSet;
    EditText inputCalm;

    ColorSeekBar bar;

    TimerModel timerModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_timer);

        viewModel = ViewModelProviders.of(this).get(CreateTimerViewModel.class);
        db = App.getInstance().getDatabase();
        FindControls();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int[] id = (int[])bundle.get("timerId");

        if(id[1] == 1){
            timerModel = db.timerDao().getById(id[0]);
            initInputs(timerModel);
        }

        viewModel.getName().observe(this, val -> inputName.setText(val));
        viewModel.getPreparation().observe(this, val -> inputPrep.setText(val.toString()));
        viewModel.getWorkTime().observe(this, val -> inputWork.setText(val.toString()));
        viewModel.getRestTime().observe(this, val -> inputRest.setText(val.toString()));
        viewModel.getCycles().observe(this, val -> inputCycle.setText(val.toString()));
        viewModel.getSets().observe(this, val -> inputSet.setText(val.toString()));
        viewModel.getRestSets().observe(this, val -> inputCalm.setText(val.toString()));
        btnPrepPlus.setOnClickListener(i -> viewModel.setIncrementPreparation());
        btnPrepMinus.setOnClickListener(i -> viewModel.setDecrementPreparation());

        btnWorkPlus.setOnClickListener(i -> viewModel.setIncrementWorkTime());
        btnWorkMinus.setOnClickListener(i -> viewModel.setDecrementWorkTime());

        btnRestPlus.setOnClickListener(i -> viewModel.setIncrementRestTime());
        btnRestMinus.setOnClickListener(i -> viewModel.setDecrementRestTime());

        btnCyclePlus.setOnClickListener(i -> viewModel.setIncrementCycle());
        btnCycleMinus.setOnClickListener(i -> viewModel.setDecrementCycle());

        btnSetPlus.setOnClickListener(i -> viewModel.setIncrementSets());
        btnSetMinus.setOnClickListener(i -> viewModel.setDecrementSets());

        btnCalmPlus.setOnClickListener(i -> viewModel.setIncrementRestSets());
        btnCalmMinus.setOnClickListener(i -> viewModel.setDecrementRestSets());

        inputName.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                viewModel.setName(inputName.getText().toString());
                if(keyCode == 4)
                {
                    Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(backIntent);
                    finish();
                    return true;
                }
                return true;
            }
            return false;
        });



        findViewById(R.id.btnCancel).setOnClickListener(i -> {
            Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backIntent);
            finish();
        });

        findViewById(R.id.submit).setOnClickListener(i -> {
            if (id[1] != 1) {
                TimerModel timerModel = new TimerModel();
                timerModel.Name = inputName.getText().toString();
                timerModel.Preparation = Integer.parseInt(inputPrep.getText().toString());
                timerModel.WorkTime = Integer.parseInt(inputWork.getText().toString());
                timerModel.RestTime = Integer.parseInt(inputRest.getText().toString());
                timerModel.Cycles = Integer.parseInt(inputCycle.getText().toString());
                timerModel.Sets = Integer.parseInt(inputSet.getText().toString());
                timerModel.RestSets = Integer.parseInt(inputCalm.getText().toString());
                timerModel.Color = bar.getColor();
                db.timerDao().insert(timerModel);
            }
            else {
                timerModel.Name = inputName.getText().toString();
                timerModel.Preparation = Integer.parseInt(inputPrep.getText().toString());
                timerModel.WorkTime = Integer.parseInt(inputWork.getText().toString());
                timerModel.RestTime = Integer.parseInt(inputRest.getText().toString());
                timerModel.Cycles = Integer.parseInt(inputCycle.getText().toString());
                timerModel.Sets = Integer.parseInt(inputSet.getText().toString());
                timerModel.RestSets = Integer.parseInt(inputCalm.getText().toString());
                timerModel.Color = bar.getColor();
                db.timerDao().update(timerModel);
            }
            Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backIntent);
            finish();
        });
    }

    private void initInputs(TimerModel timerModel){
        viewModel.setName(timerModel.Name);
        viewModel.setPrep(timerModel.Preparation);
        viewModel.setWork(timerModel.WorkTime);
        viewModel.setRest(timerModel.RestTime);
        viewModel.setCycle(timerModel.Cycles);
        viewModel.setSets(timerModel.Sets);
        viewModel.setRestSets(timerModel.RestSets);
    }

    private void FindControls(){
        btnPrepPlus = findViewById(R.id.btnPrepPlus);
        btnPrepMinus = findViewById(R.id.btnPrepMinus);
        btnWorkPlus = findViewById(R.id.btnWorkPlus);
        btnWorkMinus = findViewById(R.id.btnWorkMinus);
        btnRestPlus = findViewById(R.id.btnRestPlus);
        btnRestMinus = findViewById(R.id.btnRestMinus);
        btnCyclePlus = findViewById(R.id.btnCyclePlus);
        btnCycleMinus = findViewById(R.id.btnCycleMinus);
        btnSetPlus = findViewById(R.id.btnSetPlus);
        btnSetMinus = findViewById(R.id.btnSetMinus);
        btnCalmPlus = findViewById(R.id.btnCalmPlus);
        btnCalmMinus = findViewById(R.id.btnCalmMinus);

        inputName = findViewById(R.id.inputName);
        inputPrep = findViewById(R.id.inputPrep);
        inputWork = findViewById(R.id.inputWork);
        inputRest = findViewById(R.id.inputRest);
        inputCycle = findViewById(R.id.inputCycle);
        inputSet = findViewById(R.id.inputSet);
        inputCalm = findViewById(R.id.inputCalm);

        bar = findViewById(R.id.color_seek_bar);
    }



}