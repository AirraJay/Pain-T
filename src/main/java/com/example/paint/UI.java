package com.example.paint;

import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class UI {
    private static ToggleButton pen,colorPick;
    private static ComboBox shapes, dashedLines;

    private static ToolBar toolBar;

    private static TextField widthDou, polygonSides;

    private static ColorPicker colorPicker;
    private static Object whatShape, whatbreak;

    private static double spacedDashes;

    public UI(){

        pen = new ToggleButton("Pen");
        colorPick = new ToggleButton("Color Picker");
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
                "Polygon"
        );


        dashedLines = new ComboBox<>();
        dashedLines.getItems().addAll(0d, 1d, 3d, 5d, 10d, 20d);
        dashedLines.setOnAction(ActionEvent -> {
            whatbreak = dashedLines.getValue();
            System.out.println(whatbreak.toString());
            if(whatbreak != "No Dashes"){
                spacedDashes = 0;
            }
            else{
                spacedDashes = Double.parseDouble(whatbreak.toString());
            }
                });



        polygonSides = new TextField();

        polygonSides.setText("Polygon Sides");

        whatShape = shapes.getValue();

        shapes.setOnAction(shapesb ->{
            whatShape = null;
            whatShape = shapes.getValue();
            pen.setSelected(false);
            colorPick.setSelected(false);
        });

        shapes.setValue("Square");
        //allows pen to open
        pen.setOnAction(penB ->{
            shapes.setValue(null);
            pen.setSelected(true);
            whatShape = null;
           colorPick.setSelected(false);

        });


        colorPick.setOnAction(cB ->{
            shapes.setValue(null);
            pen.setSelected(false);
            whatShape = null;
            colorPick.setSelected(true);

        });


        toolBar.getItems().add(colorPick);
        toolBar.getItems().add(pen);


        shapes.setValue("Shapes");
        toolBar.getItems().add(shapes);
        Label NumSides = new Label("Number of polygon sides");
        toolBar.getItems().add(NumSides);
        toolBar.getItems().add(polygonSides);
        toolBar.getItems().add(dashedLines);

        Label yVarSt = new Label("Width");
        toolBar.getItems().add(yVarSt);
        //puts the string on the text field
        widthDou = new TextField(Drawing.getPenWidth() + "");
        colorPicker = new ColorPicker(Color.BLACK);




        double widthChange;

        toolBar.getItems().add(widthDou);
        toolBar.getItems().add(colorPicker);

    }

    public static ToggleButton getPen() {
        return pen;
    }
    public static ToggleButton getColorPick() {
        return colorPick;
    }

    public static ToolBar getToolBar(){
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
}
