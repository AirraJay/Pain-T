package com.example.paint;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class timerta extends TimerTask {
    Timer autoSaveTime = new Timer();
    MenuLayout ml;
    Label timerDisplay;
    public timerta(MenuLayout m){
        ml = m;
    }

    int time = 100;
    @Override
    public void run() {

        if(MenuLayout.autoSaveOn){
            time--;
            Platform.runLater(() ->{
                if(!MenuLayout.hideTimerBool) {
                    UI.getDisplayTime().setText(time + " Seconds till auto Save");
                }
            });

            System.out.println(time);

            if(time == 0){
                Platform.runLater(() -> {
                    try {
                        time = 100;
                        ml.runSave();


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

            }
        }
    }
}
