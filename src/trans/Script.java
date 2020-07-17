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
        ScriptEngine engine = manager.getEngineByName("nashorn"); //根据名称得到脚本引擎
        Double hour = (Double)engine.eval("var date = new Date();" + "date.getHours();");
        System.out.println(hour);
        engine.put("name","huang");//
        String result =(String)engine.eval("name");
        System.out.println(result);
        Context ctx = new Context();
        ctx.bind("name","xiong");
        result = (String)engine.eval("name",ctx); //ScriptContext里面可以绑定对象，然后在脚本里面取出来
        System.out.println(result);
        engine.eval("var ref = Java.type('trans.Zipfile'); ref.dozip('C:\\zip','C:\\a.zip')"); //通过脚本调用类中的函数，注意要将写上包名;
    }
    //ScriptEngineManager 中的 Bindings，ScriptEngine 实例对应的 ScriptContext 中含有的 Bindings，
    // 以及调用 eval 函数时传入的 Bingdings。离函数调用越近，其作用域越小，优先级越高,Bingdings中存放的
    // 都是一些键值对，值可以是java对象
    public static class Context extends SimpleScriptContext{
        public void bind(String key, Object value){
            engineScope.put(key,value);
        }
    }

}
