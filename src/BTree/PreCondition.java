package BTree;

/**
 * Created by jieqinxiong on 2017/5/12.
 */
public interface PreCondition {
    boolean Check();  //进入节点前进行检查，如果检查没有通过，那么会重新开始选节点
}
