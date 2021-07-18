package BTree;

/**
 * Created by jieqinxiong on 2017/5/12.
 * 控制节点的流程还有待优化
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


    //检查条件
    public final boolean evaluate(){
        return (preCondition == null || preCondition.Check()) && doEvaluate();
    }

    protected boolean doEvaluate(){  //逻辑节点去重写，行为节点一般用precondition判断
        return true;
    }

    public final void init(Object object){
        holder = object;
        onInit(object);
    }

    protected void onInit(Object object){}
    //enter和exit是一对
    protected void enter(){};
    protected void exit(){};
    protected void clear(){}
    public abstract Result run();

    public final void setCondition(PreCondition con){
        this.preCondition = con;
    }

}
