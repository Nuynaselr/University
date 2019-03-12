import functions.*;
import functions.basic.Log;
import guiFinal.*;
import functions.basic.Cos;
import javafx.application.Application;
import javafx.stage.Stage;
import threads.Generator;
import threads.SimpleGenerator;
import threads.SimpleIntegrator;
import threads.Task;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.concurrent.Semaphore;


public class Main{// extends Application {
/*
    @Override
    public void start(Stage primaryStage) throws Exception{
        InterfaceFunction mainWin = new InterfaceFunction();
        mainWin.winInterfaceFunction(primaryStage);
    }

    @Override
    public  void stop(){}

    public static void main(String[] args) {
        launch(args);
    }*/



    public static void main(String[] args) {
        simpleThreads();
    }

    public static void simpleThreads(){
        Thread thread = new Thread(new SimpleGenerator(new Task(), 2000));
        thread.start();
    }

    public static  void complicatedThreads(){
        Semaphore semaphore = new Semaphore(1);
        Thread thread = new Generator(new Task(), semaphore, 2000);
        thread.start();
        try {
            Thread.sleep(20);
            thread.interrupt();
            System.err.println("Thread interrupt signal");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
