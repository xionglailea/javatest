package trans;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by arctest on 2015/12/14.
 */
public class Rmi { //远程接口调用
    public static void main(String[] args) throws MalformedURLException {
        try{
            LocateRegistry.getRegistry(1099);
            AddServer add = new AddServerImpl();
            Naming.rebind("Hello", add);
            System.out.println("ready...");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
interface AddServer extends Remote{
    int AddNumbers(int first,int second)throws RemoteException;
}
class AddServerImpl extends UnicastRemoteObject implements AddServer {
    public AddServerImpl() throws RemoteException {
        super();
    }
    public int AddNumbers(int first,int second) throws RemoteException {
        return first + second;
    }
}
