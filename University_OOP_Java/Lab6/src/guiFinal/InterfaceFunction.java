package guiFinal;

import functions.*;
import functions.basic.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.*;
import java.util.List;

public class InterfaceFunction{
    private double doubleLeftValue;
    private double doubleRightValue;
    private int intPointCount;
    private boolean checkSaves = true; // был ли сохранен файл
    private boolean lastChanges = false; // произошли ли изменения

    private String rowSaves = ""; // путь до файла
    private ObservableList<FunctionPoint> pointData = FXCollections.observableArrayList();
    private TabulatedFunction func = null;
    private SavesChange needForSaves = new SavesChange(); // окно для опроса

    private AnchorPane root = new AnchorPane();

    private TableView<FunctionPoint> tableXY = new TableView<>();
    private TableColumn<FunctionPoint, Integer> columnX = new TableColumn<>("X");
    private TableColumn<FunctionPoint, Integer> columnY = new TableColumn<>("Y");

    private Button buttonCreatePoint = new Button("Create Point");
    private Button buttonDeletePoint = new Button("Delete Point");

    private MenuBar menu = new MenuBar();
    private Menu menuFile = new Menu("File");
    private Menu menuTabulate = new Menu("Tabulated");

    // in menuFile
    private MenuItem menuNew = new MenuItem("New");
    private MenuItem menuOpen = new MenuItem("Open");
    private MenuItem menuSave = new MenuItem("Save");
    private MenuItem menuSaveAs = new MenuItem("Save as...");
    private MenuItem menuCheck = new MenuItem("Check");
    private  MenuItem menuExit = new MenuItem("Exit");

    // in menuTabulated
    private MenuItem menuCos = new MenuItem("Cos");
    private  MenuItem menuSin = new MenuItem("Sin");
    private  MenuItem menuTan = new MenuItem("Tan");
    private  MenuItem menuExp = new MenuItem("Exp");
    private  MenuItem menuLog = new MenuItem("Log");

    private  Text textX = new Text("X new point");
    private  Text textY = new Text("Y new point");

    private  TextField xValue = new TextField();
    private   TextField yValue = new TextField();


    public void winInterfaceFunction(Stage stage){
        columnX.setCellValueFactory(new PropertyValueFactory<>("x"));
        columnY.setCellValueFactory(new PropertyValueFactory<>("y"));

        buttonCreatePoint.setDisable(true);
        buttonDeletePoint.setDisable(true);
        menuSave.setVisible(false);
        menuSaveAs.setVisible(false);


        //menuBar size
        menu.setPrefWidth(355.0);
        menu.setPrefHeight(30.0);

        // add objects in menuFile
        menuFile.getItems().addAll(menuNew, menuOpen, menuSave, menuSaveAs, menuCheck, menuExit);

        // add objects in menuTabulated
        menuTabulate.getItems().addAll(menuCos, menuSin, menuTan, menuLog, menuExp);

        //add menu in menuBar
        menu.getMenus().addAll(menuFile, menuTabulate);

        //textField size
        xValue.setPrefWidth(100.0);
        yValue.setPrefWidth(100.0);

        //Policy size column
        tableXY.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //size
        tableXY.setPrefWidth(355.0);
        tableXY.setPrefHeight(331.0);

        //table layout
        AnchorPane.setTopAnchor(tableXY, 30.0);
        tableXY.getColumns().addAll(columnX, columnY);

        //Text layout
        AnchorPane.setLeftAnchor(textX, 22.0);
        AnchorPane.setTopAnchor(textX, 377.0);
        AnchorPane.setLeftAnchor(textY, 22.0);
        AnchorPane.setTopAnchor(textY, 411.0);

        //TextField layout
        AnchorPane.setLeftAnchor(xValue, 120.0);
        AnchorPane.setTopAnchor(xValue, 372.0);
        AnchorPane.setLeftAnchor(yValue, 120.0);
        AnchorPane.setTopAnchor(yValue, 407.0);

        //buttons layout
        AnchorPane.setLeftAnchor(buttonCreatePoint, 242.0);
        AnchorPane.setTopAnchor(buttonCreatePoint, 372.0);
        AnchorPane.setLeftAnchor(buttonDeletePoint, 243.0);
        AnchorPane.setTopAnchor(buttonDeletePoint, 407.0);

        //add objects in window
        root.getChildren().addAll(menu, tableXY, textX, textY, xValue, yValue, buttonCreatePoint, buttonDeletePoint);

        //Action
        // Menu Action
        menuNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stageNeedForSaves = new Stage();
                stageNeedForSaves.initModality(Modality.WINDOW_MODAL);
                stageNeedForSaves.initOwner(stage);
                if (!pointData.isEmpty() || lastChanges || !checkSaves){

                    if(needForSaves.winSavesChange(stageNeedForSaves)){
                        menuSave.getOnAction().handle(event);
                    }
                }

                func = null;
                CreatFunction newWin = new CreatFunction();
                Stage stageCreateFunc = new Stage();
                stageCreateFunc.initModality(Modality.WINDOW_MODAL);
                stageCreateFunc.initOwner(stage);
                newWin.wincreateFunction(stageCreateFunc);
                pointData.clear();
                stageCreateFunc.setOnHidden(event1 -> {
                    if (newWin.getWinStatus() == "OK"){
                        doubleRightValue = newWin.getDoubleRightValue();
                        doubleLeftValue = newWin.getDoubleLeftValue();
                        intPointCount = newWin.getIntPointCount();
                        try {
                            func = new ArrayTabulatedFunction(doubleLeftValue, doubleRightValue, intPointCount);
                        } catch (InappropriateFunctionPointException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < func.getPointsCount(); i++) {
                            pointData.addAll(func.getPoint(i));
                        }
                        tableXY.setItems(pointData);
                        if(func.getPointsCount() != 0){
                            activateButton();
                            checkSaves = false;
                        }
                    }
                });
            }
        });
        menuOpen.setOnAction(event -> {
            checkSaves(stage, menuSaveAs.getOnAction());
            pointData.clear();
            FileChooser fileOpen = new FileChooser();
            FileReader fileOp = null;
            try {
                rowSaves = fileOpen.showOpenDialog(stage) + "";
                fileOp = new FileReader(rowSaves);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(!rowSaves.equals("")) {
                func = TabFunctions.readTabulatedFunction(fileOp);
                doubleLeftValue = func.getPointX(0);
                doubleRightValue = func.getPointX(func.getPointsCount() - 1);
                for (int i = 0; i < func.getPointsCount(); i++) {
                    pointData.addAll(func.getPoint(i));
                }
                tableXY.setItems(pointData);
                if (func.getPointsCount() != 0) {
                    activateButton();
                    checkSaves = false;
                }
            }
        });
        menuSave.setOnAction(event -> {
            FileChooser fileSaves = new FileChooser();
            FileWriter fileSav = null;
            if (rowSaves.equals("")) {
                try {
                    rowSaves = fileSaves.showSaveDialog(stage) + "";
                    fileSav = new FileWriter(rowSaves);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TabFunctions.writeTabulatedFunction(func, fileSav);
                checkSaves = true;
            } else {
                try {
                    fileSav = new FileWriter(rowSaves);
                    TabFunctions.writeTabulatedFunction(func, fileSav);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        menuSaveAs.setOnAction(event -> {
            FileChooser fileSaves = new FileChooser();
            try {
                File choosed = fileSaves.showSaveDialog(stage);
                if(choosed != null){
                    FileWriter fileSav = new FileWriter(choosed);
                    TabFunctions.writeTabulatedFunction(func, fileSav);
                    checkSaves = true;
                    rowSaves = fileSaves + "";
                }
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e + "");
                alert.showAndWait();
            }
        });
        menuCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SavesChange newWin = new SavesChange();
                Stage stageCreateFunc = new Stage();
                stageCreateFunc.initModality(Modality.WINDOW_MODAL);
                stageCreateFunc.initOwner(stage);
                newWin.winSavesChange(stageCreateFunc);
            }
        });
        menuExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        menuCos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkSaves(stage, menuSaveAs.getOnAction());
                createFunc(stage, tableXY, new Cos());
            }
        });
        menuSin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                checkSaves(stage, menuSaveAs.getOnAction());
                createFunc(stage, tableXY, new Sin());
            }
        });
        menuTan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkSaves(stage, menuSaveAs.getOnAction());
                createFunc(stage, tableXY, new Tan());
            }
        });
        menuLog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkSaves(stage, menuSaveAs.getOnAction());
                createFunc(stage, tableXY, new Log(Math.E));
            }
        });
        menuExp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkSaves(stage, menuSaveAs.getOnAction());
                createFunc(stage, tableXY, new Exp());
            }
        });

        // Button Action
        buttonDeletePoint.setOnAction(event -> {
            try {
                if (!tableXY.getSelectionModel().isEmpty()){
                    func.deletePoint(tableXY.getSelectionModel().getFocusedIndex());
                }
            } catch (InappropriateFunctionPointException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e + "");
                alert.showAndWait();
            }
            pointData.clear();
            lastChanges = true;
            for (int i = 0; i < func.getPointsCount(); i++) {
                pointData.addAll(func.getPoint(i));
            }
            tableXY.setItems(pointData);
        });
        buttonCreatePoint.setOnAction(event -> {
            double timeX;
            double timeY;
            try{
                timeX = Double.parseDouble(xValue.getText());
                timeY = Double.parseDouble(yValue.getText());
                FunctionPoint timePoint = new FunctionPoint(timeX, timeY);
                if ((timeX > doubleLeftValue && timeX < doubleRightValue || Math.abs(timeX - doubleRightValue) == SpecialTools.esp || Math.abs(timeX - doubleLeftValue) == SpecialTools.esp) && !checkRepeatPoint(timePoint)){
                    func.addPoint(timePoint);
                    pointData.clear();
                    lastChanges = true;
                    for (int i = 0; i < func.getPointsCount(); i++) {
                        pointData.addAll(func.getPoint(i));
                    }
                    tableXY.setItems(pointData);
                }
                else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("Value is not included in the initial range or \n such point already exists");
                    error.setTitle("Error");
                    error.showAndWait();
                }
            } catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e + "");
                alert.showAndWait();
            }
        });

        stage.setOnCloseRequest(event -> {
            checkSaves(stage, menuSave.getOnAction());
        });

        //create scene and start stage
        Scene winInterfaceFunction = new Scene(root, 355, 447);
        stage.setTitle("Functions");
        stage.setScene(winInterfaceFunction);
        stage.setResizable(false);
        stage.show();
    }

    private void createFunc(Stage stage, TableView tableXY, Function function){
        func = null;
        CreatFunction newWin = new CreatFunction();
        Stage stageCreateFunc = new Stage();
        stageCreateFunc.initModality(Modality.WINDOW_MODAL);
        stageCreateFunc.initOwner(stage);
        newWin.wincreateFunction(stageCreateFunc);
        pointData.clear();
        stageCreateFunc.setOnHidden(event1 -> {
            doubleRightValue = newWin.getDoubleRightValue();
            doubleLeftValue = newWin.getDoubleLeftValue();
            intPointCount = newWin.getIntPointCount();
            try {
                func = TabFunctions.tabulate(function, doubleLeftValue, doubleRightValue, intPointCount);
                for (int i = 0; i < func.getPointsCount(); i++) {
                    pointData.addAll(func.getPoint(i));
                }
                tableXY.setItems(pointData);
            } catch (InappropriateFunctionPointException e) {
                e.printStackTrace();
            }
            if(func.getPointsCount() != 0){
                activateButton();
                checkSaves = false;
            }
        });
    }

    private void checkSaves(Stage stage, EventHandler<ActionEvent> eventHandler){
        Stage stageNeedForSaves = new Stage();
        ActionEvent event = new ActionEvent();
        stageNeedForSaves.initModality(Modality.WINDOW_MODAL);
        stageNeedForSaves.initOwner(stage);
        if (!pointData.isEmpty() || lastChanges || !checkSaves){

            if(needForSaves.winSavesChange(stageNeedForSaves)){
                eventHandler.handle(event);
            }
        }

    }

    private boolean checkRepeatPoint(FunctionPoint point){
        for (int i = 0; i < func.getPointsCount(); i++){
            if(point.equals(func.getPoint(i))){
                return true;
            }
        }
        return false;
    }

    private void activateButton(){
        buttonCreatePoint.setDisable(false);
        buttonDeletePoint.setDisable(false);
        menuSave.setVisible(true);
        menuSaveAs.setVisible(true);

    }

    @Deprecated
    public int getRowCount(){
        return func.getPointsCount();
    }

    @Deprecated
    public int getColumnCount(){
        return 2;
    }

    @Deprecated
    public String getColumnName(int index){
        if (index == 1){
            return "Y";
        }
        else {
            return "X";
        }
    }

    @Deprecated
    public Class getColumnClass(int index){
        return Double.class;
    }

    @Deprecated
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }

    @Deprecated
    public Object getValueAt(int rowIndex, int columnIndex){
        if (columnIndex == 1){
            return func.getPointY(rowIndex);
        }
        else return func.getPointX(rowIndex);
    }

    @Deprecated
    public void setValueAt(double value, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            func.setPointY(rowIndex, value);
        } else func.setPointX(rowIndex, value);

    }
}
