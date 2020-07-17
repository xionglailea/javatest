package trans;


import java.util.ArrayList;
import java.util.List;

public class GenericTest {
    public static class A <T extends A<T>> {
        public List<T> f = new ArrayList<>();
        public void show(){
            System.out.println();
            for(T t : f){
                System.out.println(t.getClass());
            }
        }

    }

    public static class subA extends A<subA>{

    }

    public static class subB extends  A<subB>{

    }

    public static void Show(A<? extends A> test){

    }


    public static class E{

    }

    public static class B extends E {
    }


    public static class C extends B {

    }
    public static class D extends C{

    }

    public static void main(String[] args) {


        subA subATest = new subA();

        Show(subATest);

        subB subBTest = new subB();

        Show(subBTest);

        //泛型通配符  PECS（Producer Extends Consumer Super）原则
        //为了解决    List<Integer> 不是 List<Object>子类型的问题
        //父类引用指向之类对象
        //super  随便存数据
        List<B> a = new ArrayList<>();
        List<? super C> b = a ;   //这里定义的b 能代表所有具体类型是C的父类的 List，如果不用通配符，是无法这么定义的
        b.add(new D());  //能往里面添加任何是C的子类的具体类型，不能添加C的父类类型，否则，会报错
        b.add(new C());
//        b.add(new B());
        Object c  = b.get(0); //在取数据的时候 只能用object来声明，无法用具体的C的父类类型来声明,因为你根本无法确定到底存放的C的哪个父类
        if(c.getClass() == D.class){  //只有在运行期才能知道c具体的class类型
            System.out.println(c.getClass());
            System.out.println("ddd");
        }
        if(c.getClass() == C.class){
            System.out.println("ccc");
        }

        //extends 随便取数据
        List<C> m = new ArrayList<>();
        m.add(new C());
        m.add(new D());
        List<? extends B> n = m;//这里定义的n 能代表所有具体类型是B的子类的List
//        n.add(new C());  这里不能往n里面添加元素，因为你无法知道到底n这个list中存放的是B的哪个具体子类，否则会出现类型转换错误
        B o = n.get(0); //取出来的数据 用B来声明，因为存放的数据 肯定是B的子类
        if(o instanceof C ){
            System.out.println("oooccc");
        }
        if(o instanceof D){
            System.out.println("oooddd");
        }
    }
}
