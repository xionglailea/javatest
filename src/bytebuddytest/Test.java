package bytebuddytest;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class Test {

  public static class Source {
    public String hello(String name) { return "source hello"; }
    public String hello(int i){
      return Integer.toString(i);
    }
  }

  public static class Target {
    public static String intercept(String name) { return "Hello " + name + "!"; }
    public static String intercept(int i) { return "Hello " + i; } //按参数类型匹配
    public static String intercept(Object o) { return o.toString(); }
  }

  public static class Login{
    public void login(){
      System.out.println("im login");
    }
  }

  public static class LoginLogger{
    public static void intercept(@SuperCall Callable<Void> callable) throws Exception {
      System.out.println("before login");
      callable.call();
      System.out.println("after login");
    }
  }


  public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException, IOException {
    Object toString = new ByteBuddy()
            .subclass(Object.class)
            .name("example.Type")
            .defineField("qux", String.class) // we learn more about defining fields later
            .make()
            .load(Test.class.getClassLoader())
            .getLoaded()
            .newInstance(); // Java reflection API
    System.out.println(toString.getClass().getDeclaredField("qux").getType().getName());

    String helloWorld = new ByteBuddy()
            .subclass(Source.class)
            .method(named("hello")).intercept(MethodDelegation.to(Target.class))
            .make()
            .load(Test.class.getClassLoader())
            .getLoaded()
            .newInstance()
            .hello(1);
    System.out.println(helloWorld);

     new ByteBuddy()
            .subclass(Login.class)
            .method(named("login"))
            .intercept(MethodDelegation.to(LoginLogger.class))
            .make()
            .load(Test.class.getClassLoader())
            .saveIn(new File("bytebuddygen"));
//            .getLoaded()
//            .newInstance();
      //拦截方法，会生成一个子类，子类在callable中会调用父类的方法

//    login.Login();
  }
}
