package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class Drawing {


    private Canvas mainCan;
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

            inItDraw(gc, penWidth, UI.getColorPicker().getValue());


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

            if(thisShape.toString() != null && thisShape.toString() != "Shapes") {
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
                        if(UI.getPolygonSides().getText() != "Polygon Sides" && UI.getPolygonSides() != null){
                            runShapes.drawPolygon(gc, Polysides, xMouse, yMouse, secondX, secondY);
                        }
                        else{
                            runShapes.drawPolygon(gc, 3, xMouse, yMouse, secondX, secondY);
                        }
                        break;
                    }
                }
            }
        });



    }

    public static void inItDraw(GraphicsContext gc, double width, Color color){
        //changes the pen's stats
        gc.setStroke(color);
        gc.setLineWidth(width);

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
