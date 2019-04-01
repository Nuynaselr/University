package logick;
import GUI.MessageBox;

import java.security.MessageDigest;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Writer extends Thread {
    private MessageBox win;
    private Administrator admin;
    private Semaphore sem;
    private String message;
    private String messageCloseWriter;
    private int id;

    Writer(Semaphore sem,int id, Administrator admin){
        this.sem = sem;
        this.id = id;
        this.messageCloseWriter = "Writer " + this.id + " Text: " +  "Work complete";
        this.admin = admin;
        this.win = win;
    }

    private boolean check_row(String row){
        return row.equals("+") || row.equals("-") || row.equals("/") || row.equals("*");
    }

    public String getMessageClose(){
        return this.messageCloseWriter;
    }

    public String getMessage() {
        return this.message;
    }

    public void run()
    {
        try
        {
            sem.acquire();

            System.out.println("Id Writer: " + this.id +"\nEnter your message for Writer: ");
            Scanner in = new Scanner(System.in);
            this.message = in.next();
            while (!check_row(this.message)){
                System.out.println("Not allowed symbols. Allowed: +, -, /, *");
                this.message = in.next();
            }
            this.message = "Writer " + this.id + " Text: " + message;
            admin.setMessage(this.message);
            admin.setMessageClose(this.messageCloseWriter);
            admin.printGUI();
            admin.print();
            sem.release();
        }
        catch(InterruptedException e)
        {
            System.out.println ("Oooops!");
        }
    }
}