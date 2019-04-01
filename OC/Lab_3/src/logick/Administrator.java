package logick;

import GUI.MessageBox;
import GUI.WriterBox;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Administrator{
    private static final Logger logger = Logger.getLogger(Administrator.class.getName());
    private FileHandler fh;

    private MessageBox win;
    private int countProcess = 0;
    private String message = "";
    private String messageClose = "";

    public Administrator() throws IOException {
        fh = new FileHandler("/home/np/Рабочий стол/Lab_OC/Lab_3/Admin.txt");
        logger.addHandler(fh);
    }

    public void setCountProcess(int number) {
        this.countProcess = number;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setMessageClose(String message) {
        this.messageClose = message;
    }

    public String getMessage() {
        return this.message;
    }
    public String getMessageClose() {
        return this.messageClose;
    }
    public int getCountProcess(){
        return this.countProcess;
    }

    public void print(){
        logger.info("enter message in console");
        System.out.println(this.message);
        System.out.println(this.messageClose);
    }


    public void printGUI(){
        logger.info("enter message in GUI");
        win.setAdminPane(win.getAdminPane() + "\n" + this.message + "\n" + this.messageClose);
    }

    public void runGUI() throws IOException {
        logger.info("Start GUI");
        win = new MessageBox(this);
        win.setAdminPane("Enter count process Writer: ");
    }


    public void run(){
        logger.info("Create Thread: console");
        Semaphore sem = new Semaphore(1, true);
        int id = 1;
        for (int i = 0; i < this.countProcess; i++) {
            new Writer(sem, id, this).start();
            id ++;
        }
        logger.info("Well done");
        //System.out.println(check.getMessageClose());
    }

    public void runGUIWriter() throws IOException {
        logger.info("Create Thread: GUI");
        Semaphore semW = new Semaphore(1, true);
        int id = 1;
        for (int i = 0; i < this.countProcess; i++) {
            new WriterBox(semW, id, this).run();
            id ++;
        }
        logger.info("Well done");
    }
}