package trans;

import java.io.*;

/**
 * Created by arctest on 2015/12/14.
 */
public class Serial {  //���л�
    public static void main(String[] args) throws FileNotFoundException {
        try{
            User user = new User("Xiong","Jieqing");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("user.txt"));
            output.writeObject(user);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("user.txt"));
            User user = (User) input.readObject();
            System.out.println(user);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
class User implements Serializable{
    public String firstName;
    public String SecondName;
    User(String first,String second) {
        this.firstName = first;
        this.SecondName = second;
    }

    @Override
    public String toString() {
        return firstName + " "+ SecondName;
    }
    /**
     * ��ͨ��ObjectOutputStream�� writeObject����д������ʱ����������������ж�����writeObject������
     * �ͻ���ø÷��������ѵ�ǰ ObjectOutputStream������Ϊ�������ݽ�ȥ��writeObject������һ�������Զ�������л��߼���
     * ������д��֮ǰ�޸����ֵ������д���������ݵȡ�����writeObject����ӵ��߼����ڶ�Ӧ��readObject�ж���Ҫ��ת��������֮��Ӧ��
     **/
    /**
     *���ӵ����л���ʽ
     *
     */
    private void writeObject(ObjectOutputStream output) throws IOException {
        output.defaultWriteObject();
        output.writeUTF("Hello World");
    }
    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        String value = input.readUTF();
        System.out.println(value);
    }
}