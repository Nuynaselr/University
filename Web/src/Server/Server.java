package Server;

import Realization.Comments;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    public Comments deleteDuplicateAndSortList(Comments listComments) throws RemoteException;
    public void checkMessage(String message) throws RemoteException;
}
