package freemarkertest;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class generator {

  public static void main(String[] args) throws Exception {
    test();
  }

  public static void test() throws Exception {
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
    cfg.setDirectoryForTemplateLoading(new File("src/freemarkertest/template"));
    cfg.setDefaultEncoding("UTF-8");
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

    Map data = new HashMap();
    data.put("name", "xiong");
    Template template = cfg.getTemplate("test.ftl");
    Writer out = new OutputStreamWriter(System.out);
    template.process(data, out);
  }

}
