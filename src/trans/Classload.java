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
       ClassLoader myLoader = new ClassLoader(){//�Զ�����������
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
                   return defineClass(name,b,0,b.length);//�����������ֽ�������һ��Class��������.class�ļ���������е�ʵ��
               }catch (IOException e){
                   throw new ClassNotFoundException(name);
               }
           }
       };
        Object object = myLoader.loadClass("trans.EffectJava").newInstance();//ʵ����һ������
        System.out.println(object.getClass().getClassLoader());
        System.out.println(object instanceof trans.EffectJava);//��ϵͳ���������ص��಻��ͬһ����
        System.out.println(Classload.class.getClassLoader().toString());
//        Test1 test1 = new Test1();
//        while (true){
//            test1.print();
//            try {
//                File file = new File(getBasePath() + "\\newclass\\" + "Test1.class");//�����ǷŸı���.class�ļ�
//
//                if(file.exists()) {
//                    System.out.println(file.getPath());
//                    InputStream is = new FileInputStream(file);
//                    byte[] b = new byte[is.available()];
//                    is.read(b);
//                    ReDefineClass.get().redefineClasses(new ClassDefinition(TestFile.Test1.class, b));//���滻���ı������з����Ķ���
//                }
//                //�������ַ���Ҳ�����ƣ�ֻ�����������ĸĶ���The redefinition may change method bodies, the constant pool and attributes.
//                // The redefinition must not add, remove or rename fields or methods,
//                // change the signatures of methods, or change inheritance.
//            }catch (IOException e){
//                throw new ClassNotFoundException("null");
//            }
//            Thread.sleep(5000);
//        }

    }


}
