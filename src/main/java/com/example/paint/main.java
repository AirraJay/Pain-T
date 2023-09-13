package com.example.paint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;

public class main extends Application{
    private static Stage myStage;
    private static BorderPane border;



    public void start(Stage stage) {

        //call menuLayout
        MenuLayout thisLayout = new MenuLayout();

        //BoarderPane

        border = new BorderPane();

        //border.setCenter(root);
        border.setTop(thisLayout.getMenuBar());
        //Setting the Scene object

        Scene scene = new Scene(border, 595, 370);
        stage.setTitle("Displaying Image");
        stage.setScene(scene);


        stage.show();
    }
    public static void main(String[] args) {


        launch();
    }


    public static Stage getMyStage(){return myStage;}




    public static void drawImage(File file, Canvas canvas){
        //creates the image for future use

        if(file == null)
            return;
        Image img = new Image(file.toURI().toString());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //readies image for displaying
        canvas.setWidth(img.getWidth());
        canvas.setHeight(img.getHeight());
        gc.drawImage(img, 0, 0);
        //displays
        border.setCenter(canvas);
    }
}