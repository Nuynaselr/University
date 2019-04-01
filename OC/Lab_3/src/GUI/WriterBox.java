package GUI;

import javax.swing.*;
import logick.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class WriterBox extends  JFrame implements Runnable{
    private static final Logger logger = Logger.getLogger(Administrator.class.getName());
    private FileHandler fh;

    private JTextPane writerPane;
    private JPanel panelWriter;
    private JButton sendButton;
    private JTextField textFieldWriter;

    private String message;
    private Administrator admin;
    private Semaphore sem;
    private String messageCloseWriter;
    private int id;

    public WriterBox(Semaphore sem, int id, Administrator admin) throws IOException {
        super("Writer");
        this.sem = sem;
        this.id = id;
        this.messageCloseWriter = "Writer " + this.id + " Text: " +  "Work complete";
        this.admin = admin;

        fh = new FileHandler("/home/np/Рабочий стол/Lab_OC/Lab_3/WriterBox" + id + ".txt");
        logger.addHandler(fh);

        setMinimumSize(new Dimension(300, 200));
        setContentPane(panelWriter);
        setVisible(true);
        writerPane.setEditable(false);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private boolean check_row(String row){
        return row.equals("+") || row.equals("-") || row.equals("/") || row.equals("*");
    }

    public String getMessageClose(){
        return this.messageCloseWriter;
    }


    public void run()
    {
        try
        {
            logger.info("Create window");
            sem.acquire();
            writerPane.setText("Id Writer: " + this.id +"\nEnter your message for Writer: ");


            sendButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    logger.info("Read message");
                    message = textFieldWriter.getText();
                    admin.setMessage("Writer " + id + " Message: " + textFieldWriter.getText());
                    logger.info("Send message");

                    admin.setMessageClose(messageCloseWriter);
                    logger.info("Send message close");
                    admin.printGUI();
                    logger.info("Enter message in admin");
                    setVisible(false);
                    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    logger.info("Close window");
                }
            });

            sem.release();
        }
        catch(InterruptedException e)
        {
            System.out.println ("Oooops!");
        }
    }
}

