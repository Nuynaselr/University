package guiFinal;

import functions.ArrayTabulatedFunction;
import functions.InappropriateFunctionPointException;
import functions.TabFunctions;
import functions.TabulatedFunction;
import functions.basic.Cos;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        InterfaceFunction mainWin = new InterfaceFunction();
        mainWin.winInterfaceFunction(primaryStage);
    }

    @Override
    public  void stop(){}

    public static void main(String[] args) {
        launch(args);
    }
}
