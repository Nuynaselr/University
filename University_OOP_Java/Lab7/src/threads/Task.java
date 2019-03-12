package threads;

import functions.Function;
import functions.TransformFunctions;
import functions.basic.Log;
import javafx.scene.control.Tab;

public class Task {
    private Function func;
    private double leftBorder;
    private double rightBorder;
    private double sampleRate;
    private int countTask;

    public Task(){

    }

    public Task(Function func, double leftBorder, double rightBorder, double sampleRate){
        this.func = func;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.sampleRate = sampleRate;
    }

    public void nonThread(){
        this.countTask = 120;
        for (int i = 0; i < this.countTask; i++){
            createParam();
            System.out.println("Iteration number  --->  " + (i + 1));
            System.out.println("Source: " + "Left border: " + this.leftBorder + " Right border: " + this.rightBorder + " Sample rate: " + this.sampleRate);
            System.out.println("Result:  " + "Left border " + this.leftBorder + " Right border: " + this.rightBorder + " Sample rate: " + this.sampleRate + " Answer: " + TransformFunctions.integral(this.func, this.leftBorder, this.rightBorder, this.sampleRate));
        }
    }

    public void createParam(){
        this.func = new Log(1 + (int) (Math.random() * 10));
        this.leftBorder = (int) (Math.random() * 100);
        this.rightBorder = 100 + (int) (Math.random() * 200);
        this.sampleRate = Math.random();
    }

    public double decision(){
        return TransformFunctions.integral(this.func, this.leftBorder, this.rightBorder, this.sampleRate);
    }
}
