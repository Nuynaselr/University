package Client;

import Realization.Comment;
import Realization.Comments;
import Server.Server;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class Client {
    private static Comments createList(){
        Comment com = new Comment("Usy");
        Comment comRead = new Comment();
        System.out.println(com.toString());
        Comment.writeFile(com);
        Comment.readFile("/home/np/University/Web/JsonSave.json", comRead);
        System.out.println(comRead.toString());
        comRead.setCommentsText("AAAAAAA");
        com.setPostTime(LocalDateTime.now());


        Comments listCom = new Comments();
        listCom.addElement(com);
        listCom.addElement(comRead);

        return listCom;
    }

    public static void main(String[] args) {
        String filePathRead = "/home/np/University/Web/JsonSaveList.json";
        String filePathWrite = "/home/np/University/Web/JsonSaveList2.json";

        String hostAddress = "127.0.0.1";
        int hostPort = 1099;
        String hostName = "Server_Comments";
        String hostPath = "rmi://localhost/DateBaseComments";

        try{

            System.setProperty(hostName, hostAddress);

            Server serv = (Server) Naming.lookup(hostPath);

            Comments testComments = new Comments();
            testComments.readFile(filePathRead);

            System.out.println("File read. Sending to server.");
            testComments = serv.deleteDuplicateAndSortList(testComments);
            if (testComments.isCheckError()) {
                System.out.println("!!Error!!\nEnd of the program!!");
                try (Writer fileSave = new FileWriter(filePathWrite)) {
                    fileSave.write(testComments.getRowError());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("File received. Write on your device.");
                testComments.writeFile(filePathWrite);
            }



        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("NotBoundException : " +
                    e.getMessage());
        }
    }
}
