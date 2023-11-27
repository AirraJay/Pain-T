package com.example.paint;

import javafx.application.Platform;

import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class timerta extends TimerTask {
    Timer autoSaveTime = new Timer();
    MenuLayout ml;
    public timerta(MenuLayout m){
        ml = m;
    }

    int time = 100;

    @Override
    public void run() {

        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        if(SystemTray.isSupported()){
            SystemTray tray = SystemTray.getSystemTray();

            trayIcon.setToolTip("System tray icon demo");
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }


        }

        if(MenuLayout.autoSaveOn){
            //time will go down by seconds
            time--;
            Platform.runLater(() ->{
                if(!MenuLayout.hideTimerBool) {
                    UI.getDisplayTime().setText(time + " Seconds till auto Save");
                }
            });



            if(time == 0){
                Platform.runLater(() -> {
                    try {
                        trayIcon.displayMessage("Auto Saving", "Saved", TrayIcon.MessageType.INFO);
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
