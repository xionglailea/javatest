package BTree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by jieqinxiong on 2017/5/13.
 */
public class BTreeTest {
    public static int status = 1;
    public static int updatetime = 0;
    private static final ScheduledExecutorService scheduleExecutor = Executors.newScheduledThreadPool(1);

    public static class IdleAction extends ActionNode{

        public IdleAction(PreCondition preCondition) {
            super(preCondition);
        }

        @Override
        protected void enter() {
            System.out.println("IdleAction enter");
        }

        @Override
        protected void exit() {
            System.out.println("IdleAction exit");
        }

        @Override
        public Result execute() {
            System.out.println("Idle Action execute");
            return Result.Ended;
        }

        @Override
        protected void onInit(Object object) {  //也可以在这里做preCondition的初始化，通过传进来的object
            super.onInit(object);
        }
    }

    public static class RunAction extends ActionNode{

        private int runtime;

        public RunAction(PreCondition preCondition) {
            super(preCondition);
        }

        @Override
        public Result execute() {
            System.out.println("Run Action execute " + runtime);
            runtime--;
            return runtime > 0 ? Result.Running : Result.Ended;
        }

        @Override
        protected void enter() {
            runtime = 3;
            System.out.println("RunAction enter");
        }

        @Override
        protected void exit() {
            status++;
            System.out.println("RunAction exit");

        }
    }

    public static void main(String[] args) {

        List<Node> children = new ArrayList<>();
        children.add(new RunAction(() -> status <= 3));
        children.add(new IdleAction(null));
//        Node root = new Parallel(null, Parallel.ParallenMode.And, children);  //同时执行
//        Node root = new PrioritySelector(null, children);
        Node root = new ParallelFlexible(null, children);
        root.init(null);
        scheduleExecutor.scheduleAtFixedRate(() -> {
            updatetime++;
            System.out.println("update time " + updatetime);
            if(root.evaluate()){
                System.out.println(root.run());
            }
        }, 0,1, TimeUnit.SECONDS);
    }
}
