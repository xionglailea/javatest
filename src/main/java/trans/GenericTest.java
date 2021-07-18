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

        //����ͨ���  PECS��Producer Extends Consumer Super��ԭ��
        //Ϊ�˽��    List<Integer> ���� List<Object>�����͵�����
        //��������ָ��֮�����
        //super  ��������
        List<B> a = new ArrayList<>();
        List<? super C> b = a ;   //���ﶨ���b �ܴ������о���������C�ĸ���� List���������ͨ��������޷���ô�����
        b.add(new D());  //������������κ���C������ľ������ͣ��������C�ĸ������ͣ����򣬻ᱨ��
        b.add(new C());
//        b.add(new B());
        Object c  = b.get(0); //��ȡ���ݵ�ʱ�� ֻ����object���������޷��þ����C�ĸ�������������,��Ϊ������޷�ȷ�����״�ŵ�C���ĸ�����
        if(c.getClass() == D.class){  //ֻ���������ڲ���֪��c�����class����
            System.out.println(c.getClass());
            System.out.println("ddd");
        }
        if(c.getClass() == C.class){
            System.out.println("ccc");
        }

        //extends ���ȡ����
        List<C> m = new ArrayList<>();
        m.add(new C());
        m.add(new D());
        List<? extends B> n = m;//���ﶨ���n �ܴ������о���������B�������List
//        n.add(new C());  ���ﲻ����n�������Ԫ�أ���Ϊ���޷�֪������n���list�д�ŵ���B���ĸ��������࣬������������ת������
        B o = n.get(0); //ȡ���������� ��B����������Ϊ��ŵ����� �϶���B������
        if(o instanceof C ){
            System.out.println("oooccc");
        }
        if(o instanceof D){
            System.out.println("oooddd");
        }
    }
}
