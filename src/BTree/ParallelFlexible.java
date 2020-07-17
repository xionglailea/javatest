package BTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jieqinxiong on 2017/5/12.
 */
public class ParallelFlexible extends Node {

    private List<Node> children = new ArrayList<>();
    private List<Boolean> activeList = new ArrayList<>();

    //ͬʱ��ʼ������񣬼���ʱ��ֻҪ��һ������ͨ��  �Ϳ�ʼ��, ���ǽ���ʱ��Ҫ������active�Ľڵ㶼�������ýڵ�Ż����
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
        return runningNum > 0 ? Result.Running : Result.Ended; //����������� �ýڵ����
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
