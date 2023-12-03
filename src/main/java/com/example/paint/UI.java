package com.example.paint;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;

public class UI {
    private static ToggleButton pen,colorPick, eraser, tex;
    private static ComboBox shapes, dashedLines;
    private static File texIm = new File("C:/Users/alkra/IdeaProjects/PAIN-T/src/main/resources/com/example/paint/Images/a_uppercasebw_p.png");
    private static File penIm = new File("C:/Users/alkra/IdeaProjects/PAIN-T/src/main/resources/com/example/paint/Images/pen.png");
    private static File eraserIm = new File("C:/Users/alkra/IdeaProjects/PAIN-T/src/main/resources/com/example/paint/Images/eraser.png");
    private static File colorPickIm = new File("C:/Users/alkra/IdeaProjects/PAIN-T/src/main/resources/com/example/paint/Images/color_picker.png");


    private static ToolBar toolBar;

    private static TextField widthDou, polygonSides, text;

    private static ColorPicker colorPicker;
    private static Object whatShape, whatbreak;
    private static Label displayTime;



    public UI(main mp){

        pen = new ToggleButton();
        pen.setGraphic(scale(penIm));
        Tooltip penTip = new Tooltip("Free Draw");
        Tooltip.install(pen, penTip);
        tex = new ToggleButton();
        tex.setGraphic(scale(texIm));
        Tooltip textTip = new Tooltip("Place Text");
        Tooltip.install(tex, textTip);
        colorPick = new ToggleButton();
        colorPick.setGraphic(scale(colorPickIm));
        Tooltip colorTip = new Tooltip("Grab a color from the canvas");
        Tooltip.install(colorPick, colorTip);
        eraser = new ToggleButton();
        eraser.setGraphic(scale(eraserIm));
        Tooltip eraserTip = new Tooltip("Removes color and returns to white");
        Tooltip.install(eraser, eraserTip);
        toolBar = new ToolBar();
        shapes = new ComboBox<>();
        Tooltip shapeTip = new Tooltip("Change your shape");
        Tooltip.install(shapes, shapeTip);
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

        Tooltip dashedTip = new Tooltip("Change the difference between dashes");
        Tooltip.install(dashedLines, dashedTip);



        Label x = new Label("Width of Canvas");
        Label y = new Label("Height of Canvas");



        polygonSides = new TextField();

        polygonSides.setText("Polygon Sides");

        whatShape = shapes.getValue();
        //sets item abilities
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
    //increases size
    public ImageView scale(File p){
        Image i = new Image(p.toURI().toString(), 25, 25,false, false);
        ImageView iv = new ImageView(i);
        return  iv;
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
