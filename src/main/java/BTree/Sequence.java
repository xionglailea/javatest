package BTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jieqinxiong on 2017/5/12.
 */
public class Sequence extends Node {

    private List<Node> children = new ArrayList<>();
    private int curIndex = 0;
    private Node activeNode;

    public Sequence(PreCondition preCondition, List<Node> nodes) {
        super(preCondition);
        children.addAll(nodes);
    }

    @Override
    public Result run() {
        if(activeNode == null){
            activeNode = children.get(0);
            curIndex = 0;
        }
        Result result = activeNode.run();
        if(result == Result.Ended){
            curIndex++;
            if(curIndex >= children.size()){
                activeNode.clear();
                activeNode = null;
                curIndex = -1;
            }else {
                activeNode.clear();
                activeNode = children.get(curIndex);
                result = Result.Running;
            }
        }
        return result;
    }

    @Override
    protected boolean doEvaluate() {
        if(activeNode != null){
            boolean result = activeNode.evaluate();
            if(!result){
                activeNode.clear();
                activeNode = null;
                curIndex = 0;
            }
            return result;
        }else {
            return children.get(0).evaluate();
        }
    }

    @Override
    protected void clear() {
        if(activeNode != null){
            activeNode = null;
            curIndex = 0;
        }
        children.forEach(n -> n.clear());
    }

    @Override
    protected void onInit(Object object) {
        this.children.forEach(n -> n.init(object));
    }

}
