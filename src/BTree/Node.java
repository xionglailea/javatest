package BTree;

/**
 * Created by jieqinxiong on 2017/5/12.
 * ���ƽڵ�����̻��д��Ż�
 */
public abstract class Node {
    enum Result {
        Running,
        Ended,
    }
    ;
    private PreCondition preCondition;
    protected Object holder;

    public Node(PreCondition preCondition){
        this.preCondition = preCondition;
    }


    //�������
    public final boolean evaluate(){
        return (preCondition == null || preCondition.Check()) && doEvaluate();
    }

    protected boolean doEvaluate(){  //�߼��ڵ�ȥ��д����Ϊ�ڵ�һ����precondition�ж�
        return true;
    }

    public final void init(Object object){
        holder = object;
        onInit(object);
    }

    protected void onInit(Object object){}
    //enter��exit��һ��
    protected void enter(){};
    protected void exit(){};
    protected void clear(){}
    public abstract Result run();

    public final void setCondition(PreCondition con){
        this.preCondition = con;
    }

}
