package BTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jieqinxiong on 2017/5/12.
 */
public class PrioritySelector extends Node {

    private Node activeNode;
    private List<Node> children = new ArrayList<>();

    /**
     * 只要有一个子节点成功了，就返回成功
     * @param preCondition
     */
    public PrioritySelector(PreCondition preCondition, List<Node> nodes) {
        super(preCondition);
        this.children.addAll(nodes);
    }

    @Override
    public Result run() {
        if(activeNode == null){
            return Result.Ended;
        }
        Result result = activeNode.run();
        if(result == Result.Ended){
            activeNode.clear();
            activeNode = null;
        }
        return result;
    }

    //找出第一个满足条件的
    @Override
    protected boolean doEvaluate() {
        for(Node child : children){
            if(child.evaluate()){
                if(activeNode != null && activeNode != child){
                    activeNode.clear();
                }
                activeNode = child;
                return true;
            }
        }
        //如果全部检查失败
        if(activeNode != null){
            activeNode.clear();
            activeNode = null;
        }
        return false;
    }

    @Override
    protected void clear() {
        if(activeNode != null){
            activeNode.clear();
            activeNode = null;
        }
    }

    @Override
    protected void onInit(Object object) {
        children.forEach(n -> n.init(object));
    }

}
