package trans;

import msg.test.AddressBookProtos;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by xiong on 2017/3/17.
 */
public class NioTest {
    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(new PortEcho(20010));
        thread.start();
    }
    public static class PortEcho implements Runnable{
        private int portNum;
        Selector selector;
        public PortEcho(int portNum) throws IOException {
            this.portNum  = portNum;
            selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress(this.portNum);
            ServerSocket ss = ssc.socket();
            ss.bind(address);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Start to listen");
        }
        @Override
        public void run() {
            while(true){
                try {
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Server starts to select");
                    int a = selector.select();
                    if(a <= 0 ){//û�����ӱ�select��ʱ��ᱻ����������������̵߳���wakeup�������̷���
                        continue;
                    }
                    Set<SelectionKey> keys = selector.selectedKeys();
                    for(SelectionKey key : keys){
                        if (!key.isValid())
                            continue;
                        if((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT){
                            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                            SocketChannel sc = ssc.accept();
                            if(null != sc) {
                                sc.configureBlocking(false);
                                sc.write(ByteBuffer.wrap(new String("Hello World!").getBytes()));
                                sc.register(this.selector, SelectionKey.OP_READ);
                                System.out.println("Receive a connect from " + sc.getRemoteAddress());
                            }
                        }
                        if((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ){
                            SocketChannel sc = (SocketChannel)key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(10240);
                            try {
                                int length = sc.read(buffer); //���Զ�̿ͻ���ǿ�йر����ӣ���������쳣
                                if(length == -1){
                                    key.channel().close();
                                }
                            }catch (IOException e){
                                key.channel().close();//�����쳣��Ȼ��ر�����
                            }
                            buffer.flip();
                            AddressBookProtos.Person person = AddressBookProtos.Person.parseFrom(buffer);
                            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " Server Receive message " + person.toString());
                        }
                    }
                    keys.clear(); //ע�������֮��Ҫclear
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
