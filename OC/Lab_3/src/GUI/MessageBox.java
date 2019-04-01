package GUI;

import logick.Administrator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class MessageBox extends JFrame{
    private static final Logger logger = Logger.getLogger(Administrator.class.getName());
    private FileHandler fh;

    private Administrator admin;
    private JTextPane adminPane;
    private JTextField adminFiled;
    private JButton sendButtonAdmin;
    private JPanel mainPanel;


    public MessageBox(Administrator admin) throws IOException {
        super("MyLiteConsole");
        this.admin = admin;
        fh = new FileHandler("/home/np/Рабочий стол/Lab_OC/Lab_3/MessageBox.txt");
        logger.addHandler(fh);

        logger.info("Create window");
        setMinimumSize(new Dimension(500, 250));
        setContentPane(mainPanel);
        setVisible(true);
        tapButton();
        adminPane.setEditable(false);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public String getAdminPane() {
        return adminPane.getText();
    }

    public void setAdminPane(String text){
        adminPane.setText(text);
    }

    public String getAdminFiled() {
        return adminFiled.getText();
    }

    private void tapButton(){
        sendButtonAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("Enter count writer");
                admin.setCountProcess(Integer.parseInt(adminFiled.getText()));
                logger.info("Recorded count writer");
                logger.info("Count output");
                adminPane.setText(adminPane.getText() + "\n" + "" + admin.getCountProcess());

                try {
                    logger.info("Create Writer");
                    admin.runGUIWriter();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

}
