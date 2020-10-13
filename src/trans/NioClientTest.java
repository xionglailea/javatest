package trans;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by xiong on 2017/3/17.
 */
public class NioClientTest {

    public static int i = 0;

    public static void main(String[] args) throws IOException {
        EchoClient client = new EchoClient(20010);
        Thread thread = new Thread(client);
        thread.start();
        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (client.getKey() != null) {
                        client.getKey().interestOps(client.getKey().interestOps() | SelectionKey.OP_WRITE);
                        client.getKey().selector().wakeup(); //�������¼���д�¼���Ҫwawkeup��ʹselector��select�����������з���
                    }
                }
            }
        });
        sendThread.start();
    }

    public static class EchoClient implements Runnable {

        private int portNum;
        Selector selector;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SelectionKey key = null;

        public EchoClient(int portNum) throws IOException {
            this.portNum = portNum;
            selector = Selector.open();
            SocketChannel sc = SocketChannel.open();
            sc.configureBlocking(false);
            InetSocketAddress remotePoint = new InetSocketAddress("127.0.0.1", this.portNum);
            if (!sc.connect(remotePoint)) {
                key = sc.register(selector, SelectionKey.OP_CONNECT); //�����¼��Ͷ��¼�Ҫ�ֿ�ע��
            } else {
                System.out.println("First connect success");
            }
            System.out.println("Start to connect " + remotePoint);
        }

        public SelectionKey getKey() {
            return key;
        }

        public void run() {
            while (true) {
                try {
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " client starts to select");
                    if (selector.select() <= 0) {//û�����ӱ�select��ʱ��ᱻ����������������̵߳���wakeup�������̷���
                        continue;
                    }
                    Set<SelectionKey> keys = selector.selectedKeys();
                    for (SelectionKey key : keys) {
                        if (key.isConnectable()) {
                            System.out.println("client starts to connect");
                            SocketChannel sc = (SocketChannel) key.channel();
                            if (sc.finishConnect()) {
                                this.key = key;
                                sc.register(selector, SelectionKey.OP_READ); //������������ֻ�������¼�
                                System.out.println("Connect success!");
                            }
                        }
                        if (key.isReadable()) {
                            System.out.println("client starts to read");
                            SocketChannel sc = (SocketChannel) key.channel();
                            if (buffer == null) {
                                buffer = ByteBuffer.allocate(1024);
                            }
                            while (true) {
                                try {
                                    int length = sc.read(buffer);
                                    if (length <= 0) {
                                        break;
                                    }
                                } catch (IOException e) { //�������Զ�̷������ر������ӣ������������������
                                    key.channel().close();
                                    break;
                                }
                            }
                            System.out.println("Client Receive message " + new String(buffer.array()));
                            buffer.clear();
                        }
                        if (key.isWritable()) {
                            SocketChannel sc = (SocketChannel) key.channel();
                            //AddressBookProtos.Person.Builder personBuild = AddressBookProtos.Person.newBuilder();
                            //AddressBookProtos.Person person = personBuild.setId(i).setName("xiong").setEmail("530309882").build();
                            i++;
                            //byte[] tosendByte = person.toByteArray();
                            //ByteBuffer buffer = ByteBuffer.wrap(tosendByte);
                            //sc.write(buffer);
                            //key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE); //����д�¼�����Ҫ�ֶ�����д����Ҫȡ����д�¼��ļ�������Ϊ�������д�¼�����ôÿ��
                        }                                                                 //select��ʱ�򶼻ᴥ��д�¼�
                    }
                    keys.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
