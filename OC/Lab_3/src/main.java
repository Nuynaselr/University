import java.io.BufferedWriter;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class main{
    static public void main(String[] argv){
        Administrator admin = new Administrator();
        System.out.println("Enter count process Writer: ");
        Scanner in = new Scanner(System.in);
        admin.setCountProcess(in.nextInt());
        admin.createWrtiter();
    }
}



class Administrator {
    class Writer extends Thread{
        private Semaphore sem;
        private String messageCloseWriter = "Well done!!";
        private int id;

        Writer(Semaphore sem,int id){
            this.sem = sem;
            this.id = id;
        }

        /* public void enterMessage(){
            System.out.println("Id Writer: " + this.id +" Enter your message for Writer: ");
            Scanner in = new Scanner(System.in);
            message = in.next();
        }*/

        public String getMessageClose(){
            return this.messageCloseWriter;
        }

        public void run()
        {
            try
            {
                sem.acquire();
                Semaphore semaph = new Semaphore(1, true);
                semaph.acquire();
                System.out.println("Id Writer: " + this.id +"\nEnter your message for Writer: ");
                Scanner in = new Scanner(System.in);
                message = in.next();
                System.out.println("Your message: " + message);
                System.out.println(this.messageCloseWriter);
                semaph.release();
                sem.release();
            }
            catch(InterruptedException e)
            {
                System.out.println ("Oooops!");
            }
        }
    }


    private int countProcess = 0;
    private String message = "";

    public void setCountProcess(int number) {
        this.countProcess = number;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void createWrtiter() {
        Semaphore sem = new Semaphore(2, true);
        int id = 1;
        for (int i = 0; i < this.countProcess; i++) {
            Writer check = new Writer(sem, id);
            check.start();
            id ++;
        }

        //System.out.println(check.getMessageClose());
    }
}