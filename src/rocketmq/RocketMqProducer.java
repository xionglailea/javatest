package rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * rocket message queue
 * <p>
 * create by xiongjieqing on 2021/3/22 20:33
 */
public class RocketMqProducer {

    private final DefaultMQProducer mqProducer;

    public RocketMqProducer() throws MQClientException {
        mqProducer = new DefaultMQProducer("test");
        mqProducer.setNamesrvAddr("10.227.18.178:9876"); //use dev box
        mqProducer.start();
        mqProducer.setSendMsgTimeout(60000);
    }

    //
    public void sendSyncMsg() throws Exception {
        for (int i = 0; i < 2; i++) {
            Message msg = new Message("TestTopic", "TestTag", ("Hello " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult result = mqProducer.send(msg);
            System.out.println("send " + i + " result = " + result);
        }
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mqProducer.shutdown();
        }).start();

    }
}
