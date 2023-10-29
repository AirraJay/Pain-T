package com.example.paint;

import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class UI {
    private static ToggleButton pen,colorPick, eraser, tex;
    private static ComboBox shapes, dashedLines;

    private static ToolBar toolBar;

    private static TextField widthDou, polygonSides, text;

    private static ColorPicker colorPicker;
    private static Object whatShape, whatbreak;
    private static Label displayTime;

    private static double spacedDashes;

    public UI(main mp){

        pen = new ToggleButton("Pen");
        tex = new ToggleButton("Text");
        colorPick = new ToggleButton("Color Picker");
        eraser = new ToggleButton("Eraser");
        toolBar = new ToolBar();
        shapes = new ComboBox<>();
        displayTime = new Label();
        whatShape = "Pen";
        pen.setSelected(true);
        shapes.getItems().addAll(
                "Line",
                "Square",
                "Circle",
                "Rectangle",
                "Ellipse",
                "Round Rectangle",
                "Polygon"
        );
        text = new TextField("Write Text Here to print");


        dashedLines = new ComboBox<>();
        dashedLines.getItems().addAll(0d, 1d, 3d, 5d, 10d, 20d);
        dashedLines.setOnAction(ActionEvent -> {
            whatbreak = dashedLines.getValue();
            if(whatbreak != "No Dashes"){
                spacedDashes = 0;
            }
            else{
                spacedDashes = Double.parseDouble(whatbreak.toString());
            }
                });

        Label x = new Label("Width of Canvas");
        Label y = new Label("Height of Canvas");



        polygonSides = new TextField();

        polygonSides.setText("Polygon Sides");

        whatShape = shapes.getValue();

        shapes.setOnAction(shapesb ->{
            whatShape = null;
            whatShape = shapes.getValue();
            pen.setSelected(false);
            colorPick.setSelected(false);
            eraser.setSelected(false);
            MenuLayout.setSelected(false);
        });

        shapes.setValue("Square");
        //allows pen to open
        pen.setOnAction(penB ->{
            shapes.setValue(null);
            pen.setSelected(true);
            whatShape = null;
           colorPick.setSelected(false);
            eraser.setSelected(false);
            MenuLayout.setSelected(false);
        });


        colorPick.setOnAction(cB ->{
            shapes.setValue(null);
            pen.setSelected(false);
            whatShape = null;
            colorPick.setSelected(true);
            eraser.setSelected(false);
            MenuLayout.setSelected(false);
        });

        eraser.setOnAction(ActionEvent -> {
            shapes.setValue(null);
            pen.setSelected(false);
            whatShape = null;
            colorPick.setSelected(false);
            eraser.setSelected(true);
            MenuLayout.setSelected(false);
        });


        toolBar.getItems().add(colorPick);
        toolBar.getItems().add(pen);
        toolBar.getItems().add(eraser);
        toolBar.getItems().add(tex);
        toolBar.getItems().add(text);


        shapes.setValue("Shapes");
        toolBar.getItems().add(shapes);
        Label NumSides = new Label("Number of polygon sides");
        toolBar.getItems().add(NumSides);
        toolBar.getItems().add(polygonSides);
        toolBar.getItems().add(dashedLines);

        Label yVarSt = new Label("Width");
        toolBar.getItems().add(yVarSt);
        //puts the string on the text field
        widthDou = new TextField("");
        colorPicker = new ColorPicker(Color.BLACK);




        double widthChange;
        displayTime.setText("Timer Off");
        toolBar.getItems().add(widthDou);
        toolBar.getItems().add(colorPicker);
        toolBar.getItems().add(displayTime);


    }

    //Accessors
    public static ToggleButton getPen() {
        return pen;
    }
    public static ToggleButton getColorPick() {
        return colorPick;
    }

    public static Label getDisplayTime() {
        return displayTime;
    }

    public ToolBar getToolBar(){
        return toolBar;
    }

    public static TextField getWidthDou(){
        return widthDou;
    }
    public static ColorPicker getColorPicker(){
        return colorPicker;
    }
    public static Object getWhatShape(){return whatShape;}

    public static TextField getPolygonSides() {
        return polygonSides;
    }

    public static ComboBox getSpacedDashes() {
        return dashedLines;
    }
    public static ToggleButton getEraser(){
        return eraser;
    }
    public static ToggleButton getTex(){
        return tex;
    }

    public static TextField getText() {
        return text;
    }


    public static void setWhatShape(Object a){
        whatShape = a;
    }
}
