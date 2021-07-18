package BTree;

/**
 * Created by jieqinxiong on 2017/5/12.
 */
public abstract class ActionNode extends Node {

    enum ActionStatus {
        Ready,
        Running
    }

    private ActionStatus curActionStatus = ActionStatus.Ready;

    //实际工作的子节点,具体的行为节点都继承自它
    public ActionNode(PreCondition preCondition) {
        super(preCondition);
    }


    public abstract Result execute();

    @Override
    protected void clear() {
        if(curActionStatus != ActionStatus.Ready){
            exit();
            curActionStatus = ActionStatus.Ready;
        }
    }

    @Override
    public Result run() {
        Result result = Result.Ended;
        if(curActionStatus == ActionStatus.Ready){
            enter();
            curActionStatus = ActionStatus.Running;
        }
        if(curActionStatus == ActionStatus.Running){
            result = execute();
            if(result == Result.Ended){
                exit();
                curActionStatus = ActionStatus.Ready; // 执行结束后，重置状态
            }
        }
        return result;
    }

}
