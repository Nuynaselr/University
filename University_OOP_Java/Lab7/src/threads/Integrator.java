package threads;

import java.util.concurrent.Semaphore;

public class Integrator extends Thread {
    private Task task;
    private Semaphore semaphore;

    public Integrator(Task newTask, Semaphore semaphore){
        this.task = newTask;
        this.semaphore = semaphore;
    }

    @Override
    public void run(){
        System.out.println("Result:  " + this.task.decision() + "\n");
    }

}
