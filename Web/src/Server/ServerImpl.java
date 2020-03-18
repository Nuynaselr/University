package Server;

import Realization.Comments;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {

    public static void main(String[] args) {
        String addressHost = "127.0.0.1";
        int hostPort = 1099;
        String hostName = "Server_Comments";

        try{
            System.setProperty(hostName, addressHost);
            Server service = new ServerImpl();
            String serviceName = "DateBaseComments";
            System.out.println("Initializing: " + serviceName);

            Registry registry = LocateRegistry.createRegistry(hostPort);

            registry.rebind(serviceName, service);
            System.out.println("Start: " + serviceName);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            System.exit(2);
        }
    }

    protected ServerImpl() throws RemoteException {
    }

    @Override
    public Comments deleteDuplicateAndSortList(Comments listComments) {
//        listComments.checkDuplicate();
        System.out.println("File received");
        listComments.deleteDuplicate();
        listComments.listSort();
        return listComments;
    }

    @Override
    public void checkMessage(String message){
        System.out.println(message);
    }
}
