package com.example.paint;

import javafx.scene.control.*;

public class UI {
    private static ToggleButton pen;

    private static ToolBar toolBar;

    private static TextField widthDou;

    private static ColorPicker colorPicker;

    public UI(){

        pen = new ToggleButton("Pen");
        toolBar = new ToolBar();
        //allows pen to open
        pen.setOnAction(penB ->{
            pen.setSelected(true);
        });
        toolBar.getItems().add(pen);
        Label yVarSt = new Label("Width");
        toolBar.getItems().add(yVarSt);
        //puts the string on the text field
        widthDou = new TextField(Drawing.getPenWidth() + "");
        colorPicker = new ColorPicker();


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
}
