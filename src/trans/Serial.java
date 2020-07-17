package trans;

import java.io.*;

/**
 * Created by arctest on 2015/12/14.
 */
public class Serial {  //序列化
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
     * 在通过ObjectOutputStream的 writeObject方法写入对象的时候，如果这个对象的类中定义了writeObject方法，
     * 就会调用该方法，并把当前 ObjectOutputStream对象作为参数传递进去。writeObject方法中一般会包含自定义的序列化逻辑，
     * 比如在写入之前修改域的值，或是写入额外的数据等。对于writeObject中添加的逻辑，在对应的readObject中都需要反转过来，与之对应。
     **/
    /**
     *复杂的序列化方式
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