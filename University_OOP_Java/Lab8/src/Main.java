import functions.*;
import functions.basic.Log;
import functions.basic.Sin;
import guiFinal.*;
import functions.basic.Cos;
import javafx.application.Application;
import javafx.stage.Stage;
import threads.Generator;
import threads.SimpleGenerator;
import threads.SimpleIntegrator;
import threads.Task;

import javax.imageio.IIOException;
import java.awt.*;
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



    public static void main(String[] args) throws InappropriateFunctionPointException {
        //simpleThreads();
        TabulatedFunction ff = null;
        try {
            ff = new ArrayTabulatedFunction(0, 10, 10);
        } catch (InappropriateFunctionPointException e) {
            e.printStackTrace();
        }
        for (FunctionPoint p : ff) {
            System.out.println(p);
        }

        Function f = new Cos();
        TabulatedFunction tf;
        tf = TabFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabFunctions.setTabulatedFunctionFactory(new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        tf = TabFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabFunctions.setTabulatedFunctionFactory(new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
        tf = TabFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());


        TabulatedFunction fff;

        fff = TabFunctions.createTabulatedFunction( ArrayTabulatedFunction.class, 0, 10, 3);
        System.out.println(fff.getClass());
        System.out.println(fff); //66/143

        fff = TabFunctions.createTabulatedFunction(ArrayTabulatedFunction.class, 0, 10, new double[] {0, 10});
        System.out.println(fff.getClass());
        System.out.println(fff);

        fff = TabFunctions.createTabulatedFunction( LinkedListTabulatedFunction.class,
                new FunctionPoint[] {
                        new FunctionPoint(0, 0),
                        new FunctionPoint(10, 10),
                        new FunctionPoint(15, 15)
                }
        );
        System.out.println(fff.getClass());
        System.out.println(fff);

        fff = TabFunctions.tabulate(LinkedListTabulatedFunction.class, new Sin(), 0, Math.PI, 11);
        System.out.println(fff.getClass());
        System.out.println(fff);
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
