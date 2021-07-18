package trans;

/**
 * Created by arctest on 2016/1/21.
 */
public class Fanxing {
    static <T> void superType(Holeder<? super T> holeder, T arg){
    }

    static <T> void subType(Holeder<? extends T> holeder, T arg){

    }
    public static void main(String[] args){
        Long l = 1L;
        Holeder<Long> unbounded = new Holeder<Long>();
        superType(unbounded, l);
        subType(unbounded,l);
    }
}
class Holeder<T>{
    private T value;
    public void set(T val){
        value = val;
    }
    public T get(){
        return value;
    }



}
