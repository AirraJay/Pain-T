package com.example.paint;


import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class UnitTests {
    @Test
    //makes sure starting canvas height is correct
    public void CanvasHeight(){

        Drawing draw = new Drawing();
        double height = 800;
        assertEquals(height, draw.getNewProject().getHeight());
    }
    @Test
    //Tests that starting width is 3
    public void widthGreater(){
        Drawing draw = new Drawing();
        double width = 3.0;
        assertEquals(width, draw.getPenWidth());
    }
    @Test
    //double checks mouse positioning working
    public void mousePos(){
        Drawing draw = new Drawing();
        double x = 0.0;
        assertEquals(x, draw.getxMouse());
    }

}
