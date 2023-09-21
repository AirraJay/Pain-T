package com.example.paint;

import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class UI {
    private static ToggleButton pen,shapeB;
    private static ComboBox shapes;

    private static ToolBar toolBar;

    private static TextField widthDou, polygonSides;

    private static ColorPicker colorPicker;
    private static Object whatShape;

    public UI(){

        pen = new ToggleButton("Pen");
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

        polygonSides = new TextField();

        polygonSides.setText("Polygon Sides");

        whatShape = shapes.getValue();

        shapes.setOnAction(shapesb ->{
            whatShape = null;
            whatShape = shapes.getValue();
            pen.setSelected(false);
        });

        shapes.setValue("Square");
        //allows pen to open
        pen.setOnAction(penB ->{
            shapes.setValue(null);
            pen.setSelected(true);
            whatShape = null;

        });

        toolBar.getItems().add(pen);

        shapes.setValue("Shapes");
        toolBar.getItems().add(shapes);
        Label NumSides = new Label("Number of polygon sides");
        toolBar.getItems().add(NumSides);
        toolBar.getItems().add(polygonSides);

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
}
