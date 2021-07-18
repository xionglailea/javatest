package trans;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xiong on 2016/10/27.
 */
public class MD5 {
    public static void main(String[] args) {
        System.out.println(getMD5("Perfect World!"));
        String a = "3000" + "1217" + "217954794688284672" + "20639649" + "699f018105c748c58a4e55510b0edae3" + "9_10022" + "mkssxggjy3rzmwxkvkgvhyiwofjqjj8e";
        System.out.println(getMD5(a));
        StringBuilder sb = new StringBuilder();
        sb.append(new String("3000".getBytes(), Charset.forName("UTF-8")));
        sb.append(new String("1217".getBytes(), Charset.forName("UTF-8")));
        sb.append(new String("217954794688284672".getBytes(), Charset.forName("UTF-8")));
        sb.append(new String("20639649".getBytes(), Charset.forName("UTF-8")));
        sb.append(new String("699f018105c748c58a4e55510b0edae3".getBytes(), Charset.forName("UTF-8")));
        sb.append(new String("9_10022".getBytes(), Charset.forName("UTF-8")));
        sb.append(new String("mkssxggjy3rzmwxkvkgvhyiwofjqjj8e".getBytes(), Charset.forName("UTF-8")));
        System.out.println(getMD5(sb.toString()));
    }

    /**
     ¼ÆËã×Ö·û´®µÄMD5Öµ
     **/
    public static String getMD5(String original) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original.getBytes());
            byte b[] = md.digest();

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                int i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
