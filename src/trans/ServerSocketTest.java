package trans;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by xiong on 2017/3/9.
 */
public class ServerSocketTest {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(10000)) {

        }catch (IOException e){
            throw new RuntimeException("Can not open port");
        }
    }
}
