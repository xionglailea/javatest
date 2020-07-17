package trans;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by xiong on 2017/1/19.
 */
public class Compensation_2017_017{

    public static void main(String[] args) throws IOException {
        List<String> levels = Files.readAllLines(Paths.get(System.getProperty("user.dir") + "\\compensation.txt"));
        for(String s : levels){
            String[] all = s.split("\t");
            System.out.println(all[0] + " " + all[1]);
        }
    }

}
