package trans;

/**
 * Created by arctest on 2016/5/3.
 */
public class OverrideTest {
    public static void main(String[] args) {
        son ins = new son();
        ins.start();
    }
    public static class father{
        public void start(){
            init();
        }
        public void init(){
            System.out.println("father init");
        }
    }
    public static class son extends father{

        public void init() {
            super.init();
            System.out.println("son init");
        }
    }
}
