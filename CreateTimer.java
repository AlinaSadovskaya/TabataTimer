package com.lab2.tabatatimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.divyanshu.colorseekbar.ColorSeekBar;
import com.lab2.tabatatimer.DataBase.DataBaseHelper;
import com.lab2.tabatatimer.Model.TimerModel;
import com.lab2.tabatatimer.Service.Timer;

import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar;
import codes.side.andcolorpicker.model.IntegerHSLColor;

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

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;

    HSLColorPickerSeekBar bar;

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
            openQuitDialogCansel();
        });



        findViewById(R.id.submit).setOnClickListener(i -> {
            openQuitDialogSave();
        });
    }

    private void openQuitDialogCansel() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                CreateTimer.this);
        quitDialog.setTitle(getResources().getString(R.string.Cansel));
        quitDialog.setPositiveButton(getResources().getString(R.string.Yes), (dialog, which) -> {
            Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backIntent);
            finish();
        });
        quitDialog.setNegativeButton(getResources().getString(R.string.No), (dialog, which) -> {
        });
        quitDialog.show();
    }

    private void openQuitDialogSave() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                CreateTimer.this);
        quitDialog.setTitle(getResources().getString(R.string.SaveMess));
        quitDialog.setPositiveButton(getResources().getString(R.string.Yes), (dialog, which) -> {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            int[] id = (int[])bundle.get("timerId");

            if(id[1] == 1){
                timerModel = db.timerDao().getById(id[0]);
                initInputs(timerModel);
            }
            if (id[1] != 1) {
                TimerModel timerModel = new TimerModel();
                timerModel.Name = inputName.getText().toString();
                timerModel.Preparation = Integer.parseInt(inputPrep.getText().toString());
                timerModel.WorkTime = Integer.parseInt(inputWork.getText().toString());
                timerModel.RestTime = Integer.parseInt(inputRest.getText().toString());
                timerModel.Cycles = Integer.parseInt(inputCycle.getText().toString());
                timerModel.Sets = Integer.parseInt(inputSet.getText().toString());
                timerModel.RestSets = Integer.parseInt(inputCalm.getText().toString());
                IntegerHSLColor ii = bar.getPickedColor();

                timerModel.Color = Color.HSVToColor(new float[]{ii.getFloatH(), ii.getFloatL(), ii.getFloatS()});
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
                IntegerHSLColor ii = bar.getPickedColor();
                timerModel.Color = Color.HSVToColor(new float[]{ii.getFloatH(), ii.getFloatL(), ii.getFloatS()});
                db.timerDao().update(timerModel);
            }
            Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backIntent);
            finish();
        });
        quitDialog.setNegativeButton(getResources().getString(R.string.No), (dialog, which) -> {
            onBackPressed();
        });
        quitDialog.show();
    }

    private void initInputs(TimerModel timerModel){
        viewModel.setName(timerModel.Name);
        viewModel.setPrep(timerModel.Preparation);
        viewModel.setWork(timerModel.WorkTime);
        viewModel.setRest(timerModel.RestTime);
        viewModel.setCycle(timerModel.Cycles);
        viewModel.setSets(timerModel.Sets);
        viewModel.setRestSets(timerModel.RestSets);
        viewModel.setColor(timerModel.Color);
        bar.setPickedColor(convertToIntegerHSLColor(timerModel.Color));
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
        imageView6 = findViewById(R.id.icon6);
        imageView1 = findViewById(R.id.icon);
        imageView2 = findViewById(R.id.icon2);
        imageView3 = findViewById(R.id.icon3);
        imageView4 = findViewById(R.id.icon4);
        imageView5 = findViewById(R.id.icon5);
        bar = findViewById(R.id.color_seek_bar);
    }

    private IntegerHSLColor convertToIntegerHSLColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        IntegerHSLColor integerHSLColor = new IntegerHSLColor();
        integerHSLColor.setFloatH(hsv[0]);
        integerHSLColor.setFloatL(hsv[1]);
        integerHSLColor.setFloatS(hsv[2]);
        return integerHSLColor;
    }

}