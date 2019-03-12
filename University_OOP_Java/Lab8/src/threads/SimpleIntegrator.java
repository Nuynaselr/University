package threads;

public class SimpleIntegrator implements Runnable{
    private Task task;

    public SimpleIntegrator(Task newTask){
        this.task = newTask;
    }

    @Override
    public void run() {
        System.out.println("Result:  " + this.task.decision() + "\n");
    }


}
