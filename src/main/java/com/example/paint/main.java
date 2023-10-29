package com.example.paint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class main extends Application{
    private static Stage myStage;
    private static BorderPane border;
    private static Boolean haveSaved;

    private static Pane newCanvas;

    private TabPane tabList;



    public void start(Stage stage) throws IOException {

        tabList = new TabPane();
        //call menuLayout
        MenuLayout thisLayout = new MenuLayout(this);

        UI thisUI = new UI(this);

        //TabPane



        VBox layout = new VBox(thisLayout.getMenuBar(), thisUI.getToolBar(), tabList);
        haveSaved = false;
        thisLayout.addTab(this);




        //BoarderPane

        border = new BorderPane();


        resetCanvas(thisLayout.getCurrentDraw().getNewProject());


        border.setTop(layout);

        //Setting the Scene object

        Scene scene = new Scene(border, 1600, 800);
        stage.setTitle("Displaying Image");

        stage.setScene(scene);
        smartsave(stage, thisLayout.getCurrentDraw().getNewProject(), thisLayout, this);




        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

    //saves image on close
    public void smartsave(Stage s, Canvas a, MenuLayout m, main Mp){

        ButtonType saE = new ButtonType("Save and Exit");
        ButtonType noSave = new ButtonType("Just exit");
        ButtonType takeMeBack = new ButtonType("Take me Back", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert saveA = new Alert(null,"Do you want to save", saE, takeMeBack, noSave);
        if(haveSaved){
            saveA.show();
        }
        s.setOnCloseRequest((WindowEvent j) -> {


            ButtonType bt = saveA.showAndWait().get();
            if(bt == saE){
                try {
                    MenuLayout.saveImageAs(m.getPickAFile(), a, s, Mp);
                } catch (IOException e) {
                }
            }
            else{

            }
            });


    }




    public static Stage getMyStage(){return myStage;}

    public static void drawImage(File file, Canvas canvas){
        //creates the image for future use

        if(file == null)
            return;
        Image img = new Image(file.toURI().toString());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //readies image for displaying
        canvas.setWidth(img.getWidth());
        canvas.setHeight(img.getHeight());
        gc.drawImage(img, 0, 0);
        //displays
        //border.setCenter(canvas);
    }

    public void setHaveSaved(Boolean haveSaved) {
        main.haveSaved = haveSaved;

    }
    //resets canvas' width and height
    public void resetCanvas(Canvas a){
        newCanvas = new Pane(a);
        border.setCenter(newCanvas);

    }

    public TabPane getTabList() {
        return tabList;
    }
}