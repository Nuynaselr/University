package guiFinal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CreatFunction {
    private double doubleLeftValue;
    private double doubleRightValue;
    private int intPointCount;

    private String winStatus = "";

    public void wincreateFunction(Stage stage){
        AnchorPane root = new AnchorPane();

        Button buttonOK = new Button("OK");
        Button buttonCancel = new Button("Cancel");

        Text textLeftValue = new Text("Left Value");
        Text textRightValue = new Text("Right Value");
        Text textPointCount = new Text("Point Count");

        TextField leftValue = new TextField("0");
        TextField rightValue = new TextField("10");
        leftValue.setPrefWidth(200);
        rightValue.setPrefWidth(200);

        Spinner pointCount = new Spinner();
        pointCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 1000, 1));

        //Button layout
        AnchorPane.setRightAnchor(buttonOK, 19.0);
        AnchorPane.setTopAnchor(buttonOK, 180.0);
        AnchorPane.setLeftAnchor(buttonCancel, 100.0);
        AnchorPane.setTopAnchor(buttonCancel, 180.0);

        //TextField layout
        AnchorPane.setLeftAnchor(leftValue, 26.0);
        AnchorPane.setTopAnchor(leftValue, 40.0);
        AnchorPane.setLeftAnchor(rightValue, 26.0);
        AnchorPane.setTopAnchor(rightValue, 93.0);

        //Spinner layout
        AnchorPane.setLeftAnchor(pointCount, 26.0);
        AnchorPane.setTopAnchor(pointCount, 146.0);

        //Text layout
        AnchorPane.setLeftAnchor(textLeftValue, 26.0);
        AnchorPane.setTopAnchor(textLeftValue, 24.0);
        AnchorPane.setLeftAnchor(textRightValue, 26.0);
        AnchorPane.setTopAnchor(textRightValue, 77.0);
        AnchorPane.setLeftAnchor(textPointCount, 26.0);
        AnchorPane.setTopAnchor(textPointCount, 130.0);

        root.getChildren().addAll(buttonOK, buttonCancel, leftValue, rightValue, pointCount, textLeftValue, textRightValue, textPointCount);


        //Action
        buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                winStatus = "Cancel";
                stage.close();
            }
        });

        buttonOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    doubleLeftValue = Double.parseDouble(leftValue.getText());
                    doubleRightValue = Double.parseDouble(rightValue.getText());
                    intPointCount = (int)pointCount.getValue();
                } catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e + "");
                    alert.showAndWait();
                }
                winStatus = "OK";
                stage.hide();
            }
        });
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                winStatus = "Cancel";
                stage.close();
            }
        });

        Scene winCreateFunction = new Scene(root,245, 212);
        stage.setTitle("Functions");
        stage.setScene(winCreateFunction);
        stage.setResizable(false);
        stage.show();
    }

    public double getDoubleLeftValue(){
        return doubleLeftValue;
    }
    public double getDoubleRightValue(){
        return doubleRightValue;
    }
    public int getIntPointCount(){
        return intPointCount;
    }
    public String getWinStatus(){ return winStatus; }
}
