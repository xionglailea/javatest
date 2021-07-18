package BTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jieqinxiong on 2017/5/12.
 */
public class ParallelFlexible extends Node {

    private List<Node> children = new ArrayList<>();
    private List<Boolean> activeList = new ArrayList<>();

    //同时开始多个任务，检查的时候只要有一个任务通过  就开始做, 但是结束时，要求所有active的节点都结束，该节点才会结束
    public ParallelFlexible(PreCondition preCondition, List<Node> nodes) {
        super(preCondition);
        children.addAll(nodes);
        reset();
    }

    @Override
    public Result run() {
        int runningNum = 0;
        for(int i = 0; i < children.size(); i++){
            boolean isactive = activeList.get(i);
            if(isactive){
                Result result = children.get(i).run();
                if(result == Result.Running){
                    runningNum++;
                }
            }
        }
        return runningNum > 0 ? Result.Running : Result.Ended; //所有任务结束 该节点结束
    }

    @Override
    protected boolean doEvaluate() {
        int numActiveChildren = 0;
        for(int i = 0; i < children.size(); i++){
            boolean check = children.get(i).evaluate();
            if(!activeList.get(i)){
                if(check){
                    activeList.set(i, true);
                }
            }else {
                if(!check){
                    activeList.set(i, false);
                    children.get(i).clear();
                }
            }
            if(check)
                numActiveChildren++;
        }
        return numActiveChildren > 0;
    }

    public void reset(){
        activeList.clear();
        for(int i = 0; i < children.size(); i++){
            activeList.add(false);
        }
    }

    @Override
    protected void onInit(Object object) {
        children.forEach(n -> n.init(object));
    }

    @Override
    protected void clear() {
        reset();
        children.forEach(n -> n.clear());
    }

}
