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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.lang.Math.abs;


public class MenuLayout {

    final MenuItem Save, SaveAs, Open,releaseNotes, About, HelpIt, changeSize, AddTab, clearCanvas, redo, undo, copy, paste, select,
            move, autoSave, hideTimer, rotateRight, rotateLeft, rotateRight180, rotateLeft180, rotateRight270, rotateLeft270, mirrorYAxis,
            mirrorXAxis, clearSelect;
    private final MenuBar menuBar;

    final  Menu File , Edit , Help, rotateCan ;

    private static final FileChooser pickAFile = new FileChooser();
    int currentTab;
    Stage homeStage;

    private static File saveFile;

    private final File releaseNotesFile = new File("/Users/alkra/IdeaProjects/PAIN-T/src/main/resources/com/example/paint/Text/ReleaseNotesPain-T.txt");
    private final File aboutFile = new File("/Users/alkra/IdeaProjects/PAIN-T/src/main/resources/com/example/paint/Text/about.txt");
    private final File helpFile = new File("/Users/alkra/IdeaProjects/PAIN-T/src/main/resources/com/example/paint/Text/help.txt");

    private main mainLocation;

    private List<Drawing> drawList = new ArrayList<>();
    static Boolean isSelected, isPaste, isMove, autoSaveOn, hideTimerBool;
    private int tab;


    public Image ka;

    Timer autoSaveTime;
    public static Logger log;
    String useTools = "Used tools";
    public static FileHandler fh;
    public MenuLayout(main passM) throws IOException {
        fh= new FileHandler("C:/Users/alkra/IdeaProjects/PAIN-T/src/main/resources/com/example/paint/Text/logging.log");;
        log = Logger.getLogger(useTools);

        mainLocation = passM;
        //Drawing thisDrawing = new Drawing(passM);
        File = new Menu("File");
        Help = new Menu("Help");
        Edit = new Menu("Edit");
        rotateCan = new Menu("Rotate Canvas");

        //DisplayTimer

        menuBar = new MenuBar();
        Save = new MenuItem("Save");
        SaveAs = new MenuItem("Save As");
        Open = new MenuItem("Open");
        releaseNotes = new MenuItem("Release Notes");
        About = new MenuItem("About");
        HelpIt = new MenuItem("Help");
        changeSize = new MenuItem("Change Canvas Size");
        AddTab = new MenuItem("Add New Tab");
        clearCanvas = new MenuItem("Clear Canvas");
        hideTimer = new MenuItem("Timer Off");
        tab = 0;
        select = new MenuItem("Select");
        clearSelect = new MenuItem("Select");
        copy = new MenuItem("Copy");
        paste = new MenuItem("Paste");
        move = new MenuItem("Move");
        rotateRight = new MenuItem("Rotate Right 90 Degrees");
        rotateLeft = new MenuItem("Rotate Left 90 Degrees");
        rotateRight180 = new MenuItem("Rotate Right 180 Degrees");
        rotateLeft180 = new MenuItem("Rotate Left 180 Degrees");
        rotateRight270 = new MenuItem("Rotate Right 270 Degrees");
        rotateLeft270 = new MenuItem("Rotate Left 270 Degrees");
        mirrorYAxis = new MenuItem("Flip Horizontally");
        mirrorXAxis = new MenuItem("Flip Vertically");

        redo = new MenuItem("Redo");
        undo = new MenuItem("Undo");
        autoSave = new MenuItem("Auto Save Off");
        isSelected = false;
        isPaste = false;
        autoSaveOn = false;
        hideTimerBool = false;

        homeStage = main.getMyStage();
        //Creates menu Bar
        menuBar.getMenus().addAll(File, Edit, Help);

        //Adds items to File
        File.getItems().addAll(Save, SaveAs, Open, changeSize, AddTab, clearCanvas, autoSave, undo, redo);

        Edit.getItems().addAll(select, copy, paste, move, rotateCan);
        rotateCan.getItems().addAll(rotateRight, rotateLeft, rotateRight180, rotateLeft180, rotateRight270, rotateLeft270, mirrorYAxis, mirrorXAxis);

        //add items to Help
        Help.getItems().addAll(releaseNotes, About, HelpIt, hideTimer);
        currentTab = passM.getTabList().getSelectionModel().getSelectedIndex();

        autoSaveTime = new Timer();
        timerta timerTask = new timerta(this);
        autoSaveTime.scheduleAtFixedRate(timerTask, 0, 1000);

        //Sets up all function actions
        Save.setOnAction(actionEvent -> {

            if(saveFile == null){
                try {
                    saveImageAs( pickAFile, drawList.get(currentTab).getNewProject(), homeStage, passM);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                try {
                    saveImage(saveFile, drawList.get(currentTab).getNewProject(), passM);

                } catch (IOException ex) {
                    // handle exception...
                }
            }


        });Save.setAccelerator(KeyCombination.keyCombination("Ctrl + S" ));
        SaveAs.setOnAction(actionEvent -> {
            try {
                saveImageAs( pickAFile, drawList.get(currentTab).getNewProject(), homeStage, passM);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });SaveAs.setAccelerator(KeyCombination.keyCombination("Ctrl + Shift + S"));
        Open.setOnAction(actionEvent -> {
            addTab((passM));
            openImage(null, pickAFile, drawList.get(currentTab).getNewProject(), homeStage);});
        Open.setAccelerator(KeyCombination.keyCombination("Ctrl + O" ));
        undo.setOnAction(actionEvent -> {getCurrentDraw().undo();});undo.setAccelerator(KeyCombination.keyCombination("Ctrl + Z"));
        redo.setOnAction(actionEvent -> {getCurrentDraw().redo();});redo.setAccelerator(KeyCombination.keyCombination("Ctrl + Y"));
        releaseNotes.setOnAction(actionEvent -> {windowWithDialog(releaseNotesFile);});
        About.setOnAction(actionEvent -> {windowWithDialog(aboutFile);});
        HelpIt.setOnAction(actionEvent -> {windowWithDialog(helpFile);});
        AddTab.setOnAction(actionEvent -> {addTab(mainLocation);});
        clearCanvas.setOnAction(actionEvent -> {
            ButtonType no = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType yes = new ButtonType("Yes clear it");
            Alert doYouClear = new Alert(null, "Are you sure you want to clear the Canvas", yes, no);

            ButtonType bt = doYouClear.showAndWait().get();
            if(bt == yes){
                getCurrentDraw().getGC().clearRect(0,0, getCurrentDraw().getNewProject().getWidth(), getCurrentDraw().getNewProject().getHeight() );
            }
        });

        select.setOnAction(actionEvent -> {
            reset();
            isSelected = true;

            UI.getPen().setSelected(false);
            getCurrentDraw().inItDraw(getCurrentDraw().getGC(), 3, Color.BLACK, UI.getSpacedDashes());
            UI.getSpacedDashes().setValue(3d);
            UI.setWhatShape("Rectangle");




        });
        clearSelect.setOnAction(actionEvent -> {
            getCurrentDraw().setSelected(null);
        });
        copy.setOnAction(actionEvent -> {
            reset();
            getCurrentDraw().setCopied(getCurrentDraw().getSelected());

        });
        paste.setOnAction(actionEvent -> {
            reset();
            isPaste = true;

            int x = (int)getCurrentDraw().getxMouse();
            int y = (int)getCurrentDraw().getyMouse();
            getCurrentDraw().getGC().drawImage(getCurrentDraw().getCopied(), 0, 0, x, y);
            getCurrentDraw().inItDraw(getCurrentDraw().getGC(), 3, Color.BLACK, UI.getSpacedDashes());
        });
        paste.setAccelerator(KeyCombination.keyCombination("Ctrl + V"));
        copy.setAccelerator(KeyCombination.keyCombination("Ctrl + C"));

        move.setOnAction(actionEvent -> {
            reset();
            isMove = true;
            getCurrentDraw().undo();
            getCurrentDraw().undo();
            int x = (int)abs(getCurrentDraw().getxMouse() - getCurrentDraw().getSecondX());
            int y = (int)abs(getCurrentDraw().getyMouse() - getCurrentDraw().getSecondY());

            int x1 = (int) getCurrentDraw().getxMouse();
            int y1 = (int) getCurrentDraw().getyMouse();

            Image snap = getCurrentDraw().getNewProject().snapshot(null, null);
            BufferedImage newBuff = SwingFXUtils.fromFXImage(snap, null);
            BufferedImage next = new BufferedImage(x, y, BufferedImage.OPAQUE);
            next.createGraphics().drawImage(newBuff.getSubimage(x1, y1, x, y), 0, 0, null);

            ka = SwingFXUtils.toFXImage(next, null);
            getCurrentDraw().setSelected(ka);

        });


        changeSize.setOnAction(actionEvent -> {
            Stage ne = new Stage();
            TextField width = new TextField("Width");
            TextField height = new TextField("Height");
            Button change = new Button("Change to this width and length");
            TilePane tilePane = new TilePane(width, height, change);
            Scene changeSce = new Scene(tilePane, 300, 100 );
            Tab curTab = passM.getTabList().getSelectionModel().getSelectedItem();
            int TabIndex = passM.getTabList().getSelectionModel().getSelectedIndex();
            ne.setScene(changeSce);
            ne.show();
            change.setOnAction(ActionEvent ->{
                drawList.get(TabIndex).getNewProject().setHeight(Double.parseDouble(height.getText()));
                drawList.get(TabIndex).getNewProject().setWidth(Double.parseDouble(width.getText()));
                drawList.get(TabIndex).setGc(drawList.get(TabIndex).getNewProject().getGraphicsContext2D());
                passM.resetCanvas(drawList.get(TabIndex).getNewProject());
            });

        });
        autoSave.setOnAction(actionEvent -> {
            if(autoSaveOn == false){
                autoSaveOn = true;
                autoSave.setText("Auto Save On");

            }
            else{
                autoSaveOn = false;
                autoSave.setText("Auto Save Off");
            }
        });

        hideTimer.setOnAction(actionEvent -> {

            if(!hideTimerBool){

                if(!autoSaveOn){
                    UI.getDisplayTime().setText("Auto Save is Off");
                }
                else{
                    UI.getDisplayTime().setText("Auto Save Display is off");
                }
                UI.getDisplayTime().setDisable(true);
                hideTimer.setText("Turn on Timer");

                hideTimerBool = true;
            }
            else{
                UI.getDisplayTime().setDisable(false);
                hideTimer.setText("Turn off Timer");
                hideTimerBool = false;
            }

        });

        rotateRight.setOnAction(actionEvent -> {
            rotate(90);
        });
        rotateLeft.setOnAction(actionEvent -> {
            rotate(-90);
        });
        rotateRight180.setOnAction(actionEvent -> {
            rotate(180);
        });
        rotateLeft180.setOnAction(actionEvent -> {
            rotate(-180);
        });
        rotateRight270.setOnAction(actionEvent -> {
            rotate(270);
        });
        rotateLeft270.setOnAction(actionEvent -> {
            rotate(-270);
        });
        mirrorYAxis.setOnAction((actionEvent -> {
            getCurrentDraw().getNewProject().setScaleX(getCurrentDraw().getNewProject().getScaleX() * -1);
        }));
        mirrorXAxis.setOnAction((actionEvent -> {
            getCurrentDraw().getNewProject().setScaleY(getCurrentDraw().getNewProject().getScaleY() * -1);
        }));
    }


    //opens files for help menu
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
        while (j){
        // Print all the content of a file
            try {
                if (((i = fr.read()) == -1)) j = false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.print((char) i);
        }
    }
    //runs saving the file
    public void runSave() throws IOException {
        FileChooser pickIt = new FileChooser();
        Canvas project = getCurrentDraw().getNewProject();
        Stage stage = homeStage;
        main mp = mainLocation;
        saveImageAs(pickIt, project, stage, mp);
    }
    //rotation
    public void rotate(int howMuch){
        if(getCurrentDraw().getSelected() != null){
            Canvas layer2 = new Canvas(getCurrentDraw().getNewProject().getWidth(), getCurrentDraw().getNewProject().getHeight());
            mainLocation.getNewCanvas().getChildren().add(layer2);
            layer2.toFront();
            layer2.getGraphicsContext2D().drawImage(getCurrentDraw().getSelected(), getCurrentDraw().getxMouse(), getCurrentDraw().getyMouse());
            getCurrentDraw().getGC().clearRect(getCurrentDraw().getxMouse(), getCurrentDraw().getyMouse(), getCurrentDraw().getNewProject().getWidth(), getCurrentDraw().getNewProject().getHeight());
            layer2.setRotate(howMuch);

        }
        else{
            getCurrentDraw().getNewProject().setRotate(getCurrentDraw().getNewProject().getRotate() + howMuch);
        }
     }
     //Can choose file tho
    public static void saveImageAs(FileChooser pickIt, Canvas canvas, Stage stage, main mp) throws IOException {
        pickIt.setInitialFileName("Document");
        pickIt.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.jpg"));
        pickIt.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files",  "*.png"));
        pickIt.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files",  "*.bmp"));
        saveFile = pickIt.showSaveDialog(stage);
        String fileType = saveFile.toString().substring(saveFile.toString().lastIndexOf("."));
        System.out.println(fileType);

        if(fileType.equals(".jpg") || fileType.equals(".bmp")){
            dataLossNotis(fileType);
        }
        saveImage(saveFile, canvas, mp);
    }
    //reports on lost data after save
    public static void dataLossNotis(String type){
        ButtonType takeMeBack = new ButtonType("Acknowledge", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert data = new Alert(null,"Warning that save may have lost some data", takeMeBack);
        data.show();
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
    //resets to previous data before selection
    public void reset(){
        UI.getPen().setSelected(false);
        UI.getTex().setSelected(false);
        UI.getEraser().setSelected(false);
        UI.setWhatShape(null);
        isMove = false;
        isSelected = false;
        isPaste = false;
    }
    //adds tab to tabs list
    public void addTab(main mp){
        tab++;

        currentTab = mp.getTabList().getSelectionModel().getSelectedIndex();
        Drawing newTab = new Drawing();
        Tab createTab = new Tab(tab + "");
        mainLocation.getTabList().getTabs().add(createTab);
        createTab.setContent(newTab.getNewProject());
        drawList.add(newTab);

        currentTab = mp.getTabList().getSelectionModel().getSelectedIndex();
    }
    public static void threading(String currentTool) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        dateFormat.format(date);

        try{
            fh = new FileHandler("C:/Users/alkra/IdeaProjects/PAIN-T/src/main/resources/com/example/paint/Text/logging.log");
            log.addHandler(fh);
            SimpleFormatter format = new SimpleFormatter();
            fh.setFormatter(format);
            log.info("(" + date + ") | Active Tool: " + currentTool + "\t\t\t");
        }
        catch (Exception e){

        }


    }

    public static FileChooser getPickAFile(){return pickAFile;}
    public Drawing getCurrentDraw() {
        int stored = mainLocation.getTabList().getSelectionModel().getSelectedIndex();
        return drawList.get(stored);
    }
    public static void setSelected(Boolean f) {
        isSelected = f;
    }
    public MenuBar getMenuBar() {
        return menuBar;
    }
}
