package com.example.paint;


import javafx.scene.canvas.GraphicsContext;
import java.lang.Math;

public class Shapes {

    public Shapes(){


    }

    public void drawLine(GraphicsContext gc, double x, double y, double secondX, double secondY){
        

        gc.strokeLine(x, y, secondX, secondY);

    }

    public void drawSquare(GraphicsContext gc, double x, double y, double secondX, double secondY){
        //Down and left
        if(secondX >= x && secondY  >= y){
            gc.strokeRect(secondX, secondY, secondX - x, secondX - x);
        }
        //Up and left
        else if (secondX <= x && secondY <= y) {
            gc.strokeRect(secondX, secondY, x - secondX, x - secondX);
        }
        //Up and right
        else if (secondX >= x && secondY <= y) {
            gc.strokeRect(x, secondY, secondX - x, secondX - x);
        }
        else if (secondX <= x && secondY >= y){
            gc.strokeRect(secondX, y, x - secondX, x - secondX);
        }
    }

    public void drawCircle(GraphicsContext gc, double x, double y, double secondX, double secondY){
        //Down and left
        if(secondX >= x && secondY >= y){
            gc.strokeOval(x, y, secondX - x, secondX- x);
        }
        //Up and left
        else if (secondX <= x && secondY <= y) {
            gc.strokeOval(secondX, secondY, x - secondX, x - secondX);
        }
        //Up and right
        else if (secondX >= x && secondY <= y) {
            gc.strokeOval(x, secondY, secondX - x, secondX- x );

        }
        //Down and right
        else if (secondX <= x && secondY >= y) {
            gc.strokeOval(secondX, y, x - secondX, x - secondX);

        }

    }

    public void drawRectangle(GraphicsContext gc, double x, double y, double secondX, double secondY){
        double height = Math.abs(secondY - y);
        double length = Math.abs(secondX - x);


        if(secondX >= x && secondY >= y){

            gc.strokeRect(x, y, length, height);
        }
        //Up and left
        else if (secondX <= x && secondY <= y) {
            gc.strokeRect(secondX, secondY, length, height);
        }
        //Up and right
        else if (secondX >= x && secondY <= y) {
            gc.strokeRect(x, secondY, length, height );

        }
        //Down and right
        else if (secondX <= x && secondY >= y) {
            gc.strokeRect(secondX, y, length, height);

        }

    }

    public void drawEllipse(GraphicsContext gc, double x, double y, double secondX, double secondY){
        double height = Math.abs(secondY - y);
        double length = Math.abs(secondX - x);


        if(secondX >= x && secondY >= y){

            gc.strokeOval(x, y, length, height);
        }
        //Up and left
        else if (secondX <= x && secondY <= y) {
            gc.strokeOval(secondX, secondY, length, height);
        }
        //Up and right
        else if (secondX >= x && secondY <= y) {
            gc.strokeOval(x, secondY, length, height );

        }
        //Down and right
        else if (secondX <= x && secondY >= y) {
            gc.strokeOval(secondX, y, length, height);

        }
    }

    public static void drawPolygon(GraphicsContext gc, int polySides, double x, double y, double secondX, double secondY){
        double[] xPoints = new double[polySides];
        double[] yPoints = new double[polySides];

        double radius = Math.sqrt((Math.pow((x - secondX), 2) + Math.pow((y - secondY), 2)));
        for(int i = 0; i < polySides; i++){
            xPoints[i] = x + (radius * Math.cos((2 * Math.PI * i) / polySides));
            yPoints[i] = y + (radius * Math.sin((2 * Math.PI * i) / polySides));

        }
        gc.strokePolygon(xPoints, yPoints, polySides);


    }
}
