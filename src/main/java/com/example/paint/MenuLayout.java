package com.example.paint;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MenuLayout {

    final MenuItem Save, SaveAs, Open,releaseNotes, About, HelpIt, changeSize, AddTab;
    private final MenuBar menuBar;

    final Menu File, Help;

    private static final FileChooser pickAFile = new FileChooser();

    private static File saveFile;

    private final File releaseNotesFile = new File("/Users/alkra/IdeaProjects/PAIN-T/src/main/resources/com/example/paint/Text/ReleaseNotesPain-T.txt");
    private final File aboutFile = new File("/Users/alkra/IdeaProjects/PAIN-T/src/main/resources/com/example/paint/Text/about.txt");
    private final File helpFile = new File("/Users/alkra/IdeaProjects/PAIN-T/src/main/resources/com/example/paint/Text/help.txt");

    private main mainLocation;

    private List<Drawing> drawList = new ArrayList<>();


    public MenuLayout(main passM){

        mainLocation = passM;
        Drawing thisDrawing = new Drawing(passM);
        File = new Menu("File");
        Help = new Menu("Help");

        menuBar = new MenuBar();
        Save = new MenuItem("Save");
        SaveAs = new MenuItem("Save As");
        Open = new MenuItem("Open");
        releaseNotes = new MenuItem("Release Notes");
        About = new MenuItem("About");
        HelpIt = new MenuItem("Help");
        changeSize = new MenuItem("Change Canvas Size");
        AddTab = new MenuItem("Add New Tab");




        Stage homeStage = main.getMyStage();
        //Creates menu Bar
        menuBar.getMenus().add(File);
        menuBar.getMenus().add(Help);

        //Adds items to File
        File.getItems().add(Save);

        File.getItems().add(SaveAs);
        File.getItems().add(Open);
        File.getItems().add(changeSize);
        File.getItems().add(AddTab);



        Help.getItems().add(releaseNotes);
        Help.getItems().add(About);
        Help.getItems().add(HelpIt);

        Save.setOnAction(actionEvent -> {
            if(saveFile == null){
                try {
                    saveImageAs( pickAFile, thisDrawing.getNewProject(), homeStage, passM);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                try {
                    saveImage(saveFile, thisDrawing.getNewProject(), passM);

                } catch (IOException ex) {
                    // handle exception...
                }
            }

        });
        Save.setAccelerator(KeyCombination.keyCombination("Ctrl + S" ));


        SaveAs.setOnAction(actionEvent -> {
            try {
                saveImageAs( pickAFile, thisDrawing.getNewProject(), homeStage, passM);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        SaveAs.setAccelerator(KeyCombination.keyCombination("Ctrl + Shift + S"));

        Open.setOnAction(actionEvent -> {
            openImage(saveFile, pickAFile, thisDrawing.getNewProject(), homeStage);
        });
        Open.setAccelerator(KeyCombination.keyCombination("Ctrl + O" ));

        releaseNotes.setOnAction(actionEvent -> {
            windowWithDialog(releaseNotesFile);
        });

        About.setOnAction(actionEvent -> {
            windowWithDialog(aboutFile);
        });

        HelpIt.setOnAction(actionEvent -> {
            windowWithDialog(helpFile);
        });

        AddTab.setOnAction(actionEvent -> {
            addTab(mainLocation);
        });


        changeSize.setOnAction(actionEvent -> {
            Stage ne = new Stage();
            TextField width = new TextField("Width");
            TextField height = new TextField("Height");
            Button change = new Button("Change to this width and length");
            TilePane tilePane = new TilePane(width, height, change);
            Scene changeSce = new Scene(tilePane, 300, 100 );
            ne.setScene(changeSce);
            ne.show();
            change.setOnAction(ActionEvent ->{
                thisDrawing.getNewProject().setHeight(Double.parseDouble(height.getText()));
                thisDrawing.getNewProject().setWidth(Double.parseDouble(width.getText()));
                thisDrawing.setGc(thisDrawing.getNewProject().getGraphicsContext2D());
                passM.resetCanvas(thisDrawing.getNewProject());
            });

        });



    }

    public void windowWithDialog(File f){
        Stage DialogStage = new Stage();


        TextArea ta = new TextArea();
        Scene scene = new Scene(ta);

        //changes Print output
        System.setOut(new PrintStream(new OutputStream() {

            @Override
            public void write(int b){
                ta.appendText("" + ((char) b));
            }

            @Override
            public void write(byte[] b){
                ta.appendText(new String(b));
            }

            @Override
            public void write(byte[] b, int off, int len){
                ta.appendText(new String(b, off, len));
            }
        }));

        DialogStage.setScene(scene);
        DialogStage.show();
        FileReader fr;

        //reads file
        try {
            fr = new FileReader(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boolean j = true;
        int i;
        // Holds true till there is nothing to read
        while (j)

        // Print all the content of a file
        {
            try {
                if (((i = fr.read()) == -1)) j = false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.print((char) i);
        }
    }




    public static void saveImageAs(FileChooser pickIt, Canvas canvas, Stage stage, main mp) throws IOException {
        saveFile = pickIt.showSaveDialog(stage);
        saveImage(saveFile, canvas, mp);
    }



    public static void saveImage(File file, Canvas canvas, main mp) throws IOException {
        Image image = getRegion(canvas, canvas.getWidth(), canvas.getHeight());

        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        mp.setHaveSaved(false);
    }

    public static Image getRegion(Canvas canvas, double width, double height){


        SnapshotParameters sp = new SnapshotParameters();
        //writes image
        WritableImage writtenImage = new WritableImage((int)width, (int)height);

        canvas.snapshot(sp, writtenImage);
        return writtenImage;
    }

    public void openImage(File file, FileChooser fc, Canvas canvas, Stage stage){
        if(file == null)
            file = fc.showOpenDialog(stage);
        main.drawImage(file, canvas);
    }

    public MenuBar getMenuBar(){
        return menuBar;
    }

    public static FileChooser getPickAFile(){return pickAFile;}

    public void addTab(main mp){
        Drawing newTab = new Drawing(mp);
        Tab createTab = new Tab();
        mainLocation.getTabList().getTabs().add(createTab);
        createTab.setContent(newTab.getNewProject());
        drawList.add(newTab);
    }

    public Drawing getCurrentDraw() {
        int stored = mainLocation.getTabList().getSelectionModel().getSelectedIndex();
        return drawList.get(stored);
    }
}
