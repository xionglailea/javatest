package TestFile;

import cn.hutool.core.util.HexUtil;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test3 {

  public static void main(String[] args) {


      var result = HexUtil.decodeHex("e5bc80e5a78be6a380e69fa5e280a6e280a6");
      System.out.println(new String(result, StandardCharsets.UTF_8));
    //String text = "thrift��������<at user_id="6691831330075836680" open_id="ou_4d7c6dbcebd3cac8fb8e38c965c57364">@�ܽ���</at>";
    //
    //Pattern openIdPat = Pattern.compile("ou_[A-Za-z0-9]+");
    //Matcher matcher = openIdPat.matcher(text);
    //if (matcher.find()) {
    //  System.out.println(matcher.group());
    //}
    //
    //Pattern openIdPat1 = Pattern.compile(" [u4e00-u9fa5]{1,10}");
    //Matcher matcher1 = openIdPat1.matcher(text);
    //while (matcher1.find()) {
    //  System.out.println(matcher1.group());
    //}
    //
    //Pattern openIdPat3 = Pattern.compile("d{12,}");
    //Matcher matcher3 = openIdPat3.matcher(text);
    //while (matcher3.find()) {
    //  System.out.println(matcher3.group());
    //}
    //
    //Pattern openIdPat4 = Pattern.compile("^[A-Za-z0-9u4e00-u9fa5]+");
    //Matcher matcher4 = openIdPat4.matcher(text);
    //while (matcher4.find()) {
    //  System.out.println(matcher4.group());
    //}

  }

}
