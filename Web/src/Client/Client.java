package Client;

import Realization.Comment;
import Realization.Comments;
import Server.Server;

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
        String filePathRead = args[0];
        String filePathWrite = args[1];

        String hostAddress = "127.0.0.1";
        int hostPort = 1099;
        String hostName = "Server_Comments";
        String hostPath = "rmi://localhost/DateBaseComments";

        try{
            System.setProperty(hostName, hostAddress);

            Server serv = (Server) Naming.lookup(hostPath);

            System.out.println("Send message");
            serv.checkMessage("Check contactAAAA");
            System.out.println("Send message complete");

            Comments testComments = createList();

            System.out.println(testComments.toString());
            testComments = serv.deleteDuplicateAndSortList(testComments);
            System.out.println(testComments.toString());


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
