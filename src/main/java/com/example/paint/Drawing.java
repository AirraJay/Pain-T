package com.example.paint;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Stack;
import static java.lang.Double.parseDouble;
import static java.lang.Math.abs;
import static javafx.scene.paint.Color.WHITE;
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
    public Image selected, storedImage;
    public double widthOrig, dashOriginal;
    public Color colorOrig;
    public Scale scale;
    public double zoom;
    public Drawing(){
        newProject = new Canvas(1280, 720);
        gc = newProject.getGraphicsContext2D();
        gc.setFill(WHITE);
        zoom = 1;
        undos = new Stack<>();
        redos = new Stack<>();
        addUndos(undos,redos,newProject);

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
                addUndos(undos, redos, newProject);
            } else if (UI.getEraser().isSelected()) {
                inItDraw(gc, penWidth, WHITE, UI.getSpacedDashes());
                gc.beginPath();
                gc.moveTo(xMouse, yMouse);
                gc.stroke();
                addUndos(undos, redos, newProject);
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

                storedImage = undos.peek();
                gc.drawImage(storedImage, 0, 0);
            } else if (MenuLayout.isMove) {
                gc.clearRect(xMouse, yMouse, abs(xMouse - secondX), abs(yMouse - secondY));
                storedImage = undos.peek();
                gc.drawImage(storedImage, 0, 0);
                gc.drawImage(selected, 0, 0, secondX + selected.getWidth(), secondY + selected.getHeight());
                try {
                    MenuLayout.threading("Move");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

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
                        storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        Shapes.drawLine(gc, xMouse, yMouse, secondX, secondY);
                        break;
                    }
                    case "Square": {
                        storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        Shapes.drawSquare(gc, xMouse, yMouse, secondX, secondY);
                        try {
                            MenuLayout.threading("Square");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case "Circle": {
                        storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        Shapes.drawCircle(gc, xMouse, yMouse, secondX, secondY);
                        try {
                            MenuLayout.threading("Circle");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case "Rectangle": {
                        storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        Shapes.drawRectangle(gc, xMouse, yMouse, secondX, secondY);
                        try {
                            MenuLayout.threading("Rectangle");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case "Ellipse": {
                        storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        Shapes.drawEllipse(gc, xMouse, yMouse, secondX, secondY);
                        try {
                            MenuLayout.threading("Ellipse");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case "Polygon": {
                        storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        if (UI.getPolygonSides().getText() != "Polygon Sides" && UI.getPolygonSides() != null) {
                            Shapes.drawPolygon(gc, Polysides, xMouse, yMouse, secondX, secondY);
                        } else {
                            Shapes.drawPolygon(gc, 3, xMouse, yMouse, secondX, secondY);
                        }
                        try {
                            MenuLayout.threading("Polygon");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                    case "Round Rectangle": {
                        storedImage = undos.peek();
                        gc.drawImage(storedImage, 0, 0);
                        Shapes.drawRoundRectangle(gc, xMouse, yMouse, secondX, secondY);
                        try {
                            MenuLayout.threading("Round Rectangle");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            catch (Exception a){

            }
            if(MenuLayout.isSelected){
                storedImage = undos.peek();
                gc.drawImage(storedImage, 0, 0);
                Shapes.drawRectangle(gc, xMouse, yMouse, secondX, secondY);
            }
        });


        newProject.setOnMouseClicked((MouseEvent click )-> {

            if(UI.getColorPick().isSelected() == true){
                WritableImage wi = new WritableImage((int) newProject.getWidth(), (int) newProject.getHeight());
                newProject.snapshot(null, wi);
                PixelReader pr = wi.getPixelReader();
                UI.getColorPicker().setValue(pr.getColor((int) click.getX(), (int) click.getY()));
                try {
                    MenuLayout.threading("Color Picker");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (UI.getTex().isSelected() == true) {
                gc.strokeText(UI.getText().getText(), xMouse, yMouse);
                addUndos(undos, redos, newProject);
                try {
                    MenuLayout.threading("Text");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (MenuLayout.isPaste) {
                gc.drawImage(Copied,  click.getX(), click.getY(), click.getX() + getCopied().getWidth(), click.getY() + Copied.getHeight());
                addUndos(undos, redos, newProject);
                try {
                    MenuLayout.threading("Paste");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        newProject.setOnMouseReleased((MouseEvent released) -> {
            double releasedX = released.getX();
            double releasedY = released.getY();
            if(UI.getPen().isSelected()){
                addUndos(undos, redos, newProject);
                try {
                    MenuLayout.threading("Pen");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (UI.getEraser().isSelected()) {
                addUndos(undos, redos, newProject);
                try {
                    MenuLayout.threading("Eraser");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(MenuLayout.isSelected){
                undo();
                int x = (int)abs(xMouse - releasedX);
                int y = (int)abs(yMouse - releasedY);

                int x1 = (int) xMouse;
                int y1 = (int) yMouse;

                Image snap = newProject.snapshot(null, null);
                BufferedImage newBuff = SwingFXUtils.fromFXImage(snap, null);
                BufferedImage next = new BufferedImage(x, y, BufferedImage.OPAQUE);
                next.createGraphics().drawImage(newBuff.getSubimage(x1, y1, x, y), 0, 0, null);

                selected = SwingFXUtils.toFXImage(next, null);
                try {
                    MenuLayout.threading("Selection");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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


        widthOrig = width;
        colorOrig = color;


        //changes the pen's stats
        double dash;
        if(UI.getTex().isSelected()){
            widthOrig = width;
            //One is required to type text without issues
            width = 1;
        }

        try{
            dash = parseDouble(dashes.getValue().toString());
            gc.setLineDashes(new double[]{dash, dash * 1.3, dash, dash * 1.3});
        }
        catch(Exception k){

        }
        if(!UI.getTex().isSelected()){
            gc.setStroke(colorOrig);
            gc.setLineWidth(widthOrig);
        }
        else{
            gc.setStroke(color);
            gc.setLineWidth(width);
        }


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
        Copied = a;
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
    public Image getSelected(){return selected;}
}
