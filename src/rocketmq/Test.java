package rocketmq;

/**
 * <p>
 * create by xiongjieqing on 2021/3/23 18:11
 */
public class Test {

    public static void main(String[] args) throws Exception {
        RocketMqProducer rocketMq = new RocketMqProducer();
        //rocketMq.sendSyncMsgOneBroker();
        rocketMq.sendSyncMsgMultiBroker();
        //rocketMq.sendAsyncMsg();
    }
}
