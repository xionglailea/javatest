package rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * rocket message queue
 * <p>
 * create by xiongjieqing on 2021/3/22 20:33
 */
public class RocketMq {

    private final DefaultMQProducer mqProducer;

    public RocketMq() throws MQClientException {
        mqProducer = new DefaultMQProducer("test");
        mqProducer.setNamesrvAddr("localhost:9876");
        mqProducer.start();
    }

    public void sendSyncMsg() throws Exception {
        for (int i = 0; i < 100; i++) {
            Message msg = new Message("TestTopic", "TestTag", ("Hello " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            var result = mqProducer.send(msg);
            System.out.println("send " + i + " result = " + result);
        }
        mqProducer.shutdown();
    }
}
