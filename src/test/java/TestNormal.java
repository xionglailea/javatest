import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * <p>
 * create by xiongjieqing on 2021/9/1 17:27
 */
public class TestNormal {

    @Test
    public void test1() {
        int[] a = {1, 2, 3};
        var b = new ArrayList<Integer>();
        b.add(2);
        b.add(4);
        b.add(5);
        var c = b.stream().filter(e ->
        {
            System.out.println("intput " + e);
            return e > 2;
        });
        c.forEach(e -> System.out.println(e));
        var split = b.spliterator(); //每次返回一个新的
        while (split.tryAdvance(e -> System.out.println("spliter " + e))) {

        }
    }

    @Test
    public void test2() {
        System.out.println(-8 >>> 2);
        System.out.println(-8 >> 2);
    }

}
