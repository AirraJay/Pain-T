package com.example.paint;

import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class UI {
    private static ToggleButton pen,colorPick, eraser;
    private static ComboBox shapes, dashedLines;

    private static ToolBar toolBar;

    private static TextField widthDou, polygonSides;

    private static ColorPicker colorPicker;
    private static Object whatShape, whatbreak;

    private static double spacedDashes;

    public UI(main mp){

        pen = new ToggleButton("Pen");
        colorPick = new ToggleButton("Color Picker");
        eraser = new ToggleButton("Eraser");
        toolBar = new ToolBar();
        shapes = new ComboBox<>();
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
        });

        shapes.setValue("Square");
        //allows pen to open
        pen.setOnAction(penB ->{
            shapes.setValue(null);
            pen.setSelected(true);
            whatShape = null;
           colorPick.setSelected(false);
            eraser.setSelected(false);
        });


        colorPick.setOnAction(cB ->{
            shapes.setValue(null);
            pen.setSelected(false);
            whatShape = null;
            colorPick.setSelected(true);
            eraser.setSelected(false);
        });

        eraser.setOnAction(ActionEvent -> {
            shapes.setValue(null);
            pen.setSelected(false);
            whatShape = null;
            colorPick.setSelected(false);
            eraser.setSelected(true);
        });


        toolBar.getItems().add(colorPick);
        toolBar.getItems().add(pen);
        toolBar.getItems().add(eraser);


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

        toolBar.getItems().add(widthDou);
        toolBar.getItems().add(colorPicker);


    }

    //Accessors
    public ToggleButton getPen() {
        return pen;
    }
    public ToggleButton getColorPick() {
        return colorPick;
    }

    public ToolBar getToolBar(){
        return toolBar;
    }

    public TextField getWidthDou(){
        return widthDou;
    }
    public ColorPicker getColorPicker(){
        return colorPicker;
    }
    public Object getWhatShape(){return whatShape;}

    public TextField getPolygonSides() {
        return polygonSides;
    }

    public ComboBox getSpacedDashes() {
        return dashedLines;
    }
    public ToggleButton getEraser(){
        return eraser;
    }
}
