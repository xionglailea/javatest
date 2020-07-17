package BTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jieqinxiong on 2017/5/12.
 */
public class Parallel extends Node {

    enum ParallenMode{
        And, //全部结束才结束，
        Or  //任意子节点结束就结束
    }

    private ParallenMode mode;
    private List<Node> children = new ArrayList<>();
    private List<Result> eachChildResult = new ArrayList<>();

    /**
     *
     * @param preCondition
     * @param mode 全完成才结束还是完成一个就结束
     * @param nodes
     */
    public Parallel(PreCondition preCondition, ParallenMode mode, List<Node> nodes) {
        super(preCondition);
        this.mode = mode;
        this.children.addAll(nodes);
        resetResult();
    }

    @Override
    public Result run() {
        if(mode == ParallenMode.And){
            int endedCount = 0;
            for(int i = 0; i < children.size(); i++){
                if(eachChildResult.get(i) == Result.Running){
                    Result indexI = children.get(i).run();
                    eachChildResult.set(i, indexI);
                }
                if(eachChildResult.get(i) != Result.Running){
                    endedCount++;
                }
            }
            if(endedCount == children.size()){
                resetResult();
                return Result.Ended;
            }
        }else {
            for(int i = 0; i < children.size(); i++){
                if(eachChildResult.get(i) == Result.Running){
                    Result indexI = children.get(i).run();
                    eachChildResult.set(i, indexI);
                }
                if(eachChildResult.get(i) == Result.Ended){
                    resetResult();
                    return Result.Ended;
                }
            }
        }
        return Result.Running;
    }

    //需要所有的都通过检查才能开始做
    @Override
    protected boolean doEvaluate() {
        return children.stream().allMatch(e -> e.evaluate());
    }

    @Override
    protected void onInit(Object object) {
        children.forEach(n -> n.init(object));
    }

    private void resetResult(){
        eachChildResult.clear();
        for(int i = 0; i < children.size(); i++){
            eachChildResult.add(Result.Running);
        }
    }

    @Override
    protected void clear() {
        resetResult();
        children.forEach(n -> n.clear());
    }
}
