package com.example.paint;


import javafx.scene.canvas.GraphicsContext;

public abstract class Shapes {

    public Shapes(){


    }

    //line
    public static void drawLine(GraphicsContext gc, double x, double y, double secondX, double secondY){
        

        gc.strokeLine(x, y, secondX, secondY);

    }

    //square
    public static void drawSquare(GraphicsContext gc, double x, double y, double secondX, double secondY){
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

    //circle
    public static void drawCircle(GraphicsContext gc, double x, double y, double secondX, double secondY){
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

    //rectangle
    public static void drawRectangle(GraphicsContext gc, double x, double y, double secondX, double secondY){
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

    public static void drawRoundRectangle(GraphicsContext gc, double x, double y, double secondX, double secondY){
        double height = Math.abs(secondY - y);
        double length = Math.abs(secondX - x);


        if(secondX >= x && secondY >= y){

            gc.strokeRoundRect(x, y, length, height, 25, 25);
        }
        //Up and left
        else if (secondX <= x && secondY <= y) {
            gc.strokeRoundRect(secondX, secondY, length, height, 25, 25);
        }
        //Up and right
        else if (secondX >= x && secondY <= y) {
            gc.strokeRoundRect(x, secondY, length, height, 25, 25 );

        }
        //Down and right
        else if (secondX <= x && secondY >= y) {
            gc.strokeRoundRect(secondX, y, length, height, 25, 25);

        }


    }

    //Ellipse
    public static void drawEllipse(GraphicsContext gc, double x, double y, double secondX, double secondY){
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

    //Polygons
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
