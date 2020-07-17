package trans;

import javax.script.*;
import java.util.List;

/**
 * Created by arctest on 2015/10/29.
 */
public class Script {
    public static void main(String[] args) throws ScriptException {
        // TODO Auto-generated method stub
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> factory = manager.getEngineFactories();
//        factory.forEach(k -> System.out.printf("%s", k.getNames()));
        ScriptEngine engine = manager.getEngineByName("nashorn"); //�������Ƶõ��ű�����
        Double hour = (Double)engine.eval("var date = new Date();" + "date.getHours();");
        System.out.println(hour);
        engine.put("name","huang");//
        String result =(String)engine.eval("name");
        System.out.println(result);
        Context ctx = new Context();
        ctx.bind("name","xiong");
        result = (String)engine.eval("name",ctx); //ScriptContext������԰󶨶���Ȼ���ڽű�����ȡ����
        System.out.println(result);
        engine.eval("var ref = Java.type('trans.Zipfile'); ref.dozip('C:\\zip','C:\\a.zip')"); //ͨ���ű��������еĺ�����ע��Ҫ��д�ϰ���;
    }
    //ScriptEngineManager �е� Bindings��ScriptEngine ʵ����Ӧ�� ScriptContext �к��е� Bindings��
    // �Լ����� eval ����ʱ����� Bingdings���뺯������Խ������������ԽС�����ȼ�Խ��,Bingdings�д�ŵ�
    // ����һЩ��ֵ�ԣ�ֵ������java����
    public static class Context extends SimpleScriptContext{
        public void bind(String key, Object value){
            engineScope.put(key,value);
        }
    }

}
