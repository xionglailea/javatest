package trans;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiong on 2016/9/21.
 */
public class RefTest {
    public static void main(String[] args) {
        Map<Integer, Integer> test = new HashMap<>();
        test.put(2, 2);
        System.out.printf("%s", test);
        changeValue(test);
        System.out.printf("%s", test);
    }

    public static void changeValue(Map<Integer, Integer> dest){
        dest.clear();
        dest.put(1,1);
    }
}
