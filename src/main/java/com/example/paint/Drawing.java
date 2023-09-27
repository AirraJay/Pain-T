package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class Drawing {


    private GraphicsContext gc;
    private Canvas newProject;

    private double xMouse, yMouse, secondX, secondY;

    private double penWidth = 3;

    private Object thisShape;

    int Polysides;

    private UI nu;

    public Drawing(main pMain){

        nu = new UI(pMain);

        newProject = new Canvas(1000, 500);
        gc = newProject.getGraphicsContext2D();


        newProject.setOnMousePressed((MouseEvent pain) ->{
            //look to see if pen is typed
            try{
                penWidth = Double.parseDouble(nu.getWidthDou().getText());
            } catch (NumberFormatException e) {
                //If someone types a string returns penWidth to default 3
                penWidth = 3;
            }

            inItDraw(gc, penWidth, nu.getColorPicker().getValue(), nu.getSpacedDashes(), nu);


            //get starting location
            xMouse = pain.getX();
            yMouse = pain.getY();
            if(nu.getPen().isSelected()){

                gc.beginPath();
                gc.moveTo(xMouse, yMouse);
                gc.stroke();
            } else if (nu.getEraser().isSelected()) {
                inItDraw(gc, penWidth, Color.WHITE, nu.getSpacedDashes(), nu);
                gc.beginPath();
                gc.moveTo(xMouse, yMouse);
                gc.stroke();
            }
            if(nu.getWhatShape() != null && nu.getWhatShape() != "Shapes") {
                thisShape = nu.getWhatShape();
                }
            else{
                thisShape = null;
            }




        });

        newProject.setOnMouseDragged((MouseEvent drag) -> {
            secondX = drag.getX();
            secondY = drag.getY();

            if (nu.getPen().isSelected()) {
                gc.lineTo(secondX, secondY);
                gc.stroke();
            } else if (nu.getEraser().isSelected()) {
                gc.lineTo(secondX, secondY);
                gc.stroke();
            }

            try{
                    Polysides = Integer.parseInt(nu.getPolygonSides().getText());
                }
                catch(Exception e){
                    Polysides = 3;
                }


        });

        newProject.setOnMouseReleased((MouseEvent dragEnd ) ->{
            if(nu.getPen().isSelected()){
                //for future use
            }
            pMain.setHaveSaved(true);
            try {
                //takes the combo box and then the shapes
                switch (thisShape.toString()) {
                    case "Line": {
                        Shapes.drawLine(gc, xMouse, yMouse, secondX, secondY);
                        break;
                    }
                    case "Square": {
                        Shapes.drawSquare(gc, xMouse, yMouse, secondX, secondY);
                        break;
                    }
                    case "Circle": {
                        Shapes.drawCircle(gc, xMouse, yMouse, secondX, secondY);
                        break;
                    }
                    case "Rectangle": {
                        Shapes.drawRectangle(gc, xMouse, yMouse, secondX, secondY);
                        break;
                    }
                    case "Ellipse": {
                        Shapes.drawEllipse(gc, xMouse, yMouse, secondX, secondY);
                        break;
                    }
                    case "Polygon": {
                        if (nu.getPolygonSides().getText() != "Polygon Sides" && nu.getPolygonSides() != null) {
                            Shapes.drawPolygon(gc, Polysides, xMouse, yMouse, secondX, secondY);
                        } else {
                            Shapes.drawPolygon(gc, 3, xMouse, yMouse, secondX, secondY);
                        }
                        break;
                    }
                }



            } catch (Exception y){

            }

            });

        newProject.setOnMouseClicked((MouseEvent click )-> {

            if(nu.getColorPick().isSelected() == true){
                WritableImage wi = new WritableImage((int) newProject.getWidth(), (int) newProject.getHeight());
                newProject.snapshot(null, wi);
                PixelReader pr = wi.getPixelReader();
                nu.getColorPicker().setValue(pr.getColor((int) click.getX(), (int) click.getY()));
            }
        });





    }

    public void inItDraw(GraphicsContext gc, double width, Color color, ComboBox dashes, UI nu){
        //changes the pen's stats


        try{
            if(nu.getPen().isSelected()) {
                double dash = Double.parseDouble(dashes.getValue().toString());
                gc.setLineDashes(new double[]{dash, dash * 1.3, dash, dash * 1.3});
            }
        }
        catch(Exception k){

        }
        gc.setStroke(color);
        gc.setLineWidth(width);

    }


    public Canvas getNewProject() {
        return newProject;
    }

    public double getPenWidth(){
        return penWidth;
    }

    public GraphicsContext getGC(){
        return gc;
    }
    public void setGc(GraphicsContext ngc){
        gc = ngc;
    }
}
