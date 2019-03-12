package threads;

import functions.basic.Cos;

public class SimpleGenerator implements Runnable {
    private Task task;
    private int n;

    public SimpleGenerator(Task newTask, int n){
        this.task = newTask;
        this.n = n;
    }

    @Override
    public void run() {
        for(int i = 0; i < n; i++) {
            this.task = new Task();
            task.createParam();
            System.out.println("Task received. Number task " + (i + 1));

            SimpleIntegrator integrator = new SimpleIntegrator(this.task);

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
