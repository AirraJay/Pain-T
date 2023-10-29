package com.example.paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Stack;

import static java.lang.Double.parseDouble;
import static java.lang.Math.abs;

/**
* Drawing is where all canvases and Drawing tools are set to be used
 */


public class Drawing {

    private GraphicsContext gc;
    private Canvas newProject;

    private double xMouse, yMouse, secondX, secondY;

    private double penWidth = 3;

    private Object thisShape;

    int Polysides;
    public Stack<Image> undos, redos;

    public Image Copied;
    public Image selected;

    public double widthOrig, dashOriginal;
    public Color colorOrig;



    public Drawing(){

        newProject = new Canvas(1600, 800);
        gc = newProject.getGraphicsContext2D();
        main pMain = new main();


        undos = new Stack<>();
        redos = new Stack<>();
        //addUndos(undos, redos, newProject);

        newProject.setOnMouseMoved((MouseEvent moved) -> {
            xMouse = moved.getX();
            yMouse = moved.getY();
        });

        newProject.setOnMousePressed((MouseEvent pain) ->{
            //look to see if pen is typed
            try{
                penWidth = parseDouble(UI.getWidthDou().getText());
            } catch (NumberFormatException e) {
                //If someone types a string returns penWidth to default 3
                penWidth = 3;
            }

            if(!MenuLayout.isSelected){
                inItDraw(gc, widthOrig, colorOrig, UI.getSpacedDashes());
            }

            //get starting location
            xMouse = pain.getX();
            yMouse = pain.getY();
            if(UI.getPen().isSelected()){
                inItDraw(gc, penWidth, UI.getColorPicker().getValue(), UI.getSpacedDashes());
                gc.beginPath();
                gc.moveTo(xMouse, yMouse);
                gc.stroke();
            } else if (UI.getEraser().isSelected()) {
                inItDraw(gc, penWidth, Color.WHITE, UI.getSpacedDashes());
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
            } else if (UI.getEraser().isSelected()) {
                gc.lineTo(secondX, secondY);
                gc.stroke();

            }
            else if(UI.getWhatShape() != null && UI.getWhatShape() != "Shapes"){
                Image storedImage = undos.peek();
                gc.drawImage(storedImage, 0, 0);
            } else if (MenuLayout.isMove) {
                gc.clearRect(xMouse, yMouse, abs(xMouse - secondX), abs(yMouse - secondY));
                Image storedImage = undos.peek();
                gc.drawImage(storedImage, 0, 0);
                gc.drawImage(selected, 0, 0, secondX + selected.getWidth(), secondY + selected.getHeight());

            }

            try{
                Polysides = Integer.parseInt(UI.getPolygonSides().getText());
            }
            catch(Exception e){
                Polysides = 3;
            }
            try{
                switch (thisShape.toString()) {
                    case "Line": {
                        Image storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        Shapes.drawLine(gc, xMouse, yMouse, secondX, secondY);

                        break;
                    }
                    case "Square": {
                        Image storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        Shapes.drawSquare(gc, xMouse, yMouse, secondX, secondY);

                        break;
                    }
                    case "Circle": {
                        Image storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        Shapes.drawCircle(gc, xMouse, yMouse, secondX, secondY);

                        break;
                    }
                    case "Rectangle": {
                        Image storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        Shapes.drawRectangle(gc, xMouse, yMouse, secondX, secondY);
                        break;
                    }
                    case "Ellipse": {
                        Image storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        Shapes.drawEllipse(gc, xMouse, yMouse, secondX, secondY);

                        break;
                    }
                    case "Polygon": {
                        Image storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        if (UI.getPolygonSides().getText() != "Polygon Sides" && UI.getPolygonSides() != null) {
                            Shapes.drawPolygon(gc, Polysides, xMouse, yMouse, secondX, secondY);
                        } else {
                            Shapes.drawPolygon(gc, 3, xMouse, yMouse, secondX, secondY);
                        }
                        break;
                    }
                    case "Round Rectangle": {
                        Image storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        if (UI.getPolygonSides().getText() != "Polygon Sides" && UI.getPolygonSides() != null){
                            Shapes.drawRoundRectangle(gc, xMouse, yMouse, secondX, secondY);
                        }
                    }
                }
            }
            catch (Exception a){

            }
            if(MenuLayout.isSelected){
                Image storedImage = undos.peek();
                gc.drawImage(storedImage, 0, 0);
                Shapes.drawRectangle(gc, xMouse, yMouse, secondX, secondY);

            }



        });

        newProject.setOnMouseReleased((MouseEvent dragEnd ) ->{
            if(UI.getPen().isSelected()){
                //for future use
            }
            pMain.setHaveSaved(true);
            try {
                //takes the combo box and then the shapes





            } catch (Exception y){

            }
            addUndos(undos, redos, newProject);
            if (MenuLayout.isSelected) {


            }

            });


        newProject.setOnMouseClicked((MouseEvent click )-> {

            if(UI.getColorPick().isSelected() == true){
                WritableImage wi = new WritableImage((int) newProject.getWidth(), (int) newProject.getHeight());
                newProject.snapshot(null, wi);
                PixelReader pr = wi.getPixelReader();
                UI.getColorPicker().setValue(pr.getColor((int) click.getX(), (int) click.getY()));
            } else if (UI.getTex().isSelected() == true) {
                gc.strokeText(UI.getText().getText(), xMouse, yMouse);

            } else if (MenuLayout.isPaste) {
                gc.drawImage(Copied,  click.getX(), click.getY(), click.getX() + getCopied().getWidth(), click.getY() + Copied.getHeight());

            }
        });





    }


    /**
     * inItDraw sets the selected options for the pen and more
     * @param gc gc is only used to make sure the line dashes are set up
     * @param width desired width of the line
     * @param color desired color of the line
     * @param dashes location of desired dash type
     */
    public void inItDraw(GraphicsContext gc, double width, Color color, ComboBox dashes){

        if(MenuLayout.isSelected){
            widthOrig = width;
            colorOrig = color;
            dashOriginal = Double.parseDouble(dashes.getValue().toString());
        }
        //changes the pen's stats
        double dash;
        if(!MenuLayout.isSelected){
            dashes.setValue(dashOriginal);
        }

        try{
            if(UI.getPen().isSelected()) {
                dash = parseDouble(dashes.getValue().toString());
                gc.setLineDashes(new double[]{dash, dash * 1.3, dash, dash * 1.3});
            }
        }
        catch(Exception k){

        }
        gc.setStroke(color);
        gc.setLineWidth(width);

    }

    public void undo(){
        if(!undos.empty()){
            WritableImage store = new WritableImage((int) newProject.getWidth(),(int) newProject.getHeight());
            newProject.snapshot(null, store);
            Image storedImage = undos.pop();
            gc.drawImage(storedImage, 0, 0);
            redos.push(store);
        }
        if(undos.empty()){
            WritableImage newUndo = new WritableImage((int) newProject.getWidth(), (int) newProject.getHeight());
            newProject.snapshot(null, newUndo);
            undos.addElement(newUndo);
        }
    }
    public void redo(){
        if(!redos.empty()){
            WritableImage store = new WritableImage((int) newProject.getWidth(),(int) newProject.getHeight());
            newProject.snapshot(null, store);
            Image storedImage = redos.pop();
            gc.drawImage(storedImage, 0, 0);
            undos.push(store);
        }


    }

    public void addUndos( Stack u, Stack r, Canvas c){

        Image prev = new WritableImage((int) c.getWidth(),(int) c.getHeight());
        c.snapshot(null, (WritableImage) prev);
        u.push(prev);
        r.clear();

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
    public void setNewProject(Canvas a){
        newProject = a;
    }
    public void setCopied(Image a){
        //System.out.println(Copied.toString());
        Copied = a;

        System.out.println(Copied.toString());

    }

    public void setSelected(Image selected) {this.selected = selected;
    }

    public Image getCopied(){
        return Copied;
    }

    public double getxMouse() {
        return xMouse;
    }

    public double getyMouse() {
        return yMouse;
    }

    public double getSecondX() {
        return secondX;
    }

    public double getSecondY() {
        return secondY;
    }

}
