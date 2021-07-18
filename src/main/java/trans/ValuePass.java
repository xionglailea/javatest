package trans;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by xiong on 2016/6/7.
 */
public class ValuePass {
    public static class A {
        int a ;
        public A(int a){
            this.a = a;
        }
        public void print(){
            System.out.println(this.a);
        }
    }
    public static Executor executor = Executors.newCachedThreadPool();
    public static void main(String[] args) {

        int b = 1;
        add(b);
        System.out.println(b);
        A a = new A(1);
        add(a);
        System.out.println(a.a);
        for(int i = 0; i < 10 ; i++){
            print(i);
        }
    }

    public static void add(int par1){
        par1 += 1;
        System.out.println(par1);
    }

    public static void add(A par1){
        par1.a = par1.a + 1;
        System.out.println(par1.a);
    }

    public static void print(int par1){
        executor.execute(() -> System.out.println(par1));
    }

}
