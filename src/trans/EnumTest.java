package trans;

/**
 * Created by arctest on 2015/12/25.
 */
public enum EnumTest {
    ONE,
    TWO;

    public static void main(String[] args) {
        System.out.println(ONE.ordinal()); //��0��ʼ
        System.out.println(TWO.ordinal());
    }
}
