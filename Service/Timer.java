package com.lab2.tabatatimer.Service;

import android.app.Service;
import android.content.Intent;
import android.media.SoundPool;
import android.os.IBinder;

import com.lab2.tabatatimer.R;
import com.lab2.tabatatimer.TrainingTimer;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class Timer extends Service {

    ScheduledExecutorService service;
    SoundPool soundPool;
    int soundPrepair;
    int soundFinal;
    int current_time;

    String name;
    ScheduledFuture<?> scheduledFuture;

    public void onCreate() {
        super.onCreate();
        soundPool = new SoundPool.Builder()
                            .setMaxStreams(5)
                            .build();
        soundPrepair = soundPool.load(this, R.raw.censore_preview, 1);
        soundFinal = soundPool.load(this, R.raw.final_sound, 1);
        service = Executors.newScheduledThreadPool(1);
    }

    public void onDestroy() {
        service.shutdownNow();
        scheduledFuture.cancel(true);
        Intent intent = new Intent(TrainingTimer.BROADCAST_ACTION);
        intent.putExtra(TrainingTimer.CURRENT_ACTION, "pause");
        intent.putExtra(TrainingTimer.NAME_ACTION, name);
        intent.putExtra(TrainingTimer.TIME_ACTION, Integer.toString(current_time));
        sendBroadcast(intent);
        super.onDestroy();

    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        int time = Integer.parseInt(intent.getStringExtra(TrainingTimer.PARAM_START_TIME));
        name = intent.getStringExtra(TrainingTimer.NAME_ACTION);
        MyTimerTask mr = new MyTimerTask(startId, time, name);
        if (scheduledFuture != null) {
            service.schedule(() -> {
                scheduledFuture.cancel(true);
                scheduledFuture = service.scheduleAtFixedRate(mr, 0, time + 1, TimeUnit.SECONDS);
            }, 1000, TimeUnit.MILLISECONDS);
        } else {
            scheduledFuture = service.scheduleAtFixedRate(mr, 0, time + 1, TimeUnit.SECONDS);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    class MyTimerTask extends TimerTask {

        int time;
        int startId;
        String name;

        public MyTimerTask(int startId, int time, String name) {
            this.time = time;
            this.startId = startId;
            this.name = name;
        }


        public void run() {
            Intent intent = new Intent(TrainingTimer.BROADCAST_ACTION);
            if (name.equals(getResources().getString(R.string.Finish))) {
                intent.putExtra(TrainingTimer.CURRENT_ACTION, "work");
                intent.putExtra(TrainingTimer.NAME_ACTION, name);
                intent.putExtra(TrainingTimer.TIME_ACTION, "");
                sendBroadcast(intent);
            }
            try {
                for (current_time = time; current_time > 0; current_time--) {
                    intent.putExtra(TrainingTimer.CURRENT_ACTION, "work");
                    intent.putExtra(TrainingTimer.NAME_ACTION, name);
                    intent.putExtra(TrainingTimer.TIME_ACTION, Integer.toString(current_time));
                    sendBroadcast(intent);
                    TimeUnit.SECONDS.sleep(1);
                    signal_sound(current_time);
                }
                intent = new Intent(TrainingTimer.BROADCAST_ACTION);
                intent.putExtra(TrainingTimer.CURRENT_ACTION, "clear");
                sendBroadcast(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void signal_sound(int time) {
            if (time <= 4) {
                if (time == 1)
                    soundPool.play(soundFinal, 1, 1, 0, 0, 1);
                else
                    soundPool.play(soundPrepair, 1, 1, 0, 0, 1);
            }
        }

    }
}