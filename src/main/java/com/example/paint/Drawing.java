package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class Drawing {


    private static GraphicsContext gc;
    private static Canvas newProject;

    private double xMouse, yMouse, secondX, secondY;

    private static double penWidth = 3;

    private static Object thisShape;

    int Polysides;

    public Drawing(){

        Shapes runShapes = new Shapes();
        newProject = new Canvas(1920, 1080);
        gc = newProject.getGraphicsContext2D();

        Shapes loadShapes = new Shapes();




        newProject.setOnMousePressed((MouseEvent pain) ->{
            //look to see if pen is typed
            try{
                penWidth = Double.parseDouble(UI.getWidthDou().getText());
            } catch (NumberFormatException e) {
                //If someone types a string returns penWidth to default 3
                penWidth = 3;
            }

            inItDraw(gc, penWidth, UI.getColorPicker().getValue(), UI.getSpacedDashes());


            //get starting location
            xMouse = pain.getX();
            yMouse = pain.getY();
            if(UI.getPen().isSelected()){
                gc.beginPath();
                gc.moveTo(xMouse, yMouse);
                gc.stroke();
            }
            if(UI.getWhatShape() != null && UI.getWhatShape() != "Shapes") {
                thisShape = UI.getWhatShape();
                }
            else{
                thisShape = null;
            }


        });

        newProject.setOnMouseDragged((MouseEvent drag) -> {
            secondX = drag.getX();
            secondY = drag.getY();

            if (UI.getPen().isSelected()) {
                gc.lineTo(secondX, secondY);
                gc.stroke();
            }

                try{
                    Polysides = Integer.parseInt(UI.getPolygonSides().getText());
                }
                catch(Exception e){
                    Polysides = 3;
                }


        });

        newProject.setOnMouseReleased((MouseEvent dragEnd ) ->{
            if(UI.getPen().isSelected()){
                //for future use
            }
            try {
                switch (thisShape.toString()) {
                    case "Line": {
                        runShapes.drawLine(gc, xMouse, yMouse, secondX, secondY);
                        break;
                    }
                    case "Square": {
                        runShapes.drawSquare(gc, xMouse, yMouse, secondX, secondY);
                        break;
                    }
                    case "Circle": {
                        runShapes.drawCircle(gc, xMouse, yMouse, secondX, secondY);
                        break;
                    }
                    case "Rectangle": {
                        runShapes.drawRectangle(gc, xMouse, yMouse, secondX, secondY);
                        break;
                    }
                    case "Ellipse": {
                        runShapes.drawEllipse(gc, xMouse, yMouse, secondX, secondY);
                        break;
                    }
                    case "Polygon": {
                        if (UI.getPolygonSides().getText() != "Polygon Sides" && UI.getPolygonSides() != null) {
                            runShapes.drawPolygon(gc, Polysides, xMouse, yMouse, secondX, secondY);
                        } else {
                            runShapes.drawPolygon(gc, 3, xMouse, yMouse, secondX, secondY);
                        }
                        break;
                    }
                }


            } catch (Exception y){

            }

            });

        newProject.setOnMouseClicked((MouseEvent click )-> {

            if(UI.getColorPick().isSelected() == true){
                WritableImage wi = new WritableImage((int) newProject.getWidth(), (int) newProject.getHeight());
                newProject.snapshot(null, wi);
                PixelReader pr = wi.getPixelReader();
                UI.getColorPicker().setValue(pr.getColor((int) click.getX(), (int) click.getY()));
            }
        });





    }

    public static void inItDraw(GraphicsContext gc, double width, Color color, ComboBox dashes){
        //changes the pen's stats

        double dash = Double.parseDouble(dashes.getValue().toString());
        gc.setStroke(color);
        gc.setLineWidth(width);
        gc.setLineDashes(new double[] {dash, dash * 1.3,dash, dash * 1.3});
    }


    public static Canvas getNewProject() {
        return newProject;
    }

    public static double getPenWidth(){
        return penWidth;
    }

    public static GraphicsContext getGC(){
        return gc;
    }
}
