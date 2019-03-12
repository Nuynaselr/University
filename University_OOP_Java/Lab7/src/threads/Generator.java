package threads;

import java.util.concurrent.Semaphore;

public class Generator extends Thread{
    private Task task;
    private Semaphore semaphore;
    private int n;

    public Generator(Task newTask, Semaphore semaphore, int n ){
        this.task = newTask;
        this.semaphore = semaphore;
        this.n = n;
    }

    @Override
    public void run(){
        for(int i = 0; i < n; i++) {
            this.task = new Task();
            task.createParam();
            System.out.println("Task received.Number task " + (i + 1));

            Integrator integrator = new Integrator(this.task, this.semaphore);

            Thread thread = new Thread(integrator);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
