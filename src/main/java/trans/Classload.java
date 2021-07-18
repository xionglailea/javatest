package trans;

import gsd.ReDefineClass;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by arctest on 2016/3/25.
 */
public class Classload{
        public static String getBasePath(){
            return System.getProperty("user.dir");
        }

    public static void main(String[] args) throws Exception{
       ClassLoader myLoader = new ClassLoader(){//自定义的类加载器
           @Override
           public Class<?> loadClass(String name) throws ClassNotFoundException {
               try {
                   String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                   InputStream is = getClass().getResourceAsStream(fileName);
                   if(is == null){
                       return super.loadClass(name);
                   }
                   byte[] b = new byte[is.available()];
                   is.read(b);
                   return defineClass(name,b,0,b.length);//按读进来的字节流返回一个Class对象，这是.class文件在虚拟机中的实例
               }catch (IOException e){
                   throw new ClassNotFoundException(name);
               }
           }
       };
        Object object = myLoader.loadClass("trans.EffectJava").newInstance();//实例化一个对象
        System.out.println(object.getClass().getClassLoader());
        System.out.println(object instanceof trans.EffectJava);//和系统加载器加载的类不是同一个类
        System.out.println(Classload.class.getClassLoader().toString());
//        Test1 test1 = new Test1();
//        while (true){
//            test1.print();
//            try {
//                File file = new File(getBasePath() + "\\newclass\\" + "Test1.class");//这里是放改变后的.class文件
//
//                if(file.exists()) {
//                    System.out.println(file.getPath());
//                    InputStream is = new FileInputStream(file);
//                    byte[] b = new byte[is.available()];
//                    is.read(b);
//                    ReDefineClass.get().redefineClasses(new ClassDefinition(TestFile.Test1.class, b));//热替换，改变了类中方法的定义
//                }
//                //但是这种方法也有限制，只能做轻量级的改动，The redefinition may change method bodies, the constant pool and attributes.
//                // The redefinition must not add, remove or rename fields or methods,
//                // change the signatures of methods, or change inheritance.
//            }catch (IOException e){
//                throw new ClassNotFoundException("null");
//            }
//            Thread.sleep(5000);
//        }

    }


}
