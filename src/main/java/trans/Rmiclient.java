package trans;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by arctest on 2015/12/14.
 */
public class Rmiclient {
    public static void main(String args[]) throws RemoteException, MalformedURLException, NotBoundException {
        String url="rmi://127.0.0.1/Hello";
        AddServer add;
        add = (AddServer) Naming.lookup(url);
        int result=0;
        for (int i =0;i<10;i++){
            result = add.AddNumbers(10,i);
            System.out.println(result);
        }
    }
}
