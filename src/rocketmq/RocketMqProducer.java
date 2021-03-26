package rocketmq;

import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * rocket message queue
 * <p>
 * create by xiongjieqing on 2021/3/22 20:33
 */

// TestTopic -> broker-a 8 messageQueue
// TestTopic2 -> broker-a 4 queue
// testTopic3 -> broker-a 8 queue broker-b 8 queue
// testTopic4 -> broker-a 4 queue broker-b 4 queue
// recommend pre create topic by mqadmin
public class RocketMqProducer {

    private final DefaultMQProducer mqProducer;

    public RocketMqProducer() throws MQClientException {
        mqProducer = new DefaultMQProducer("test");
        mqProducer.setNamesrvAddr("10.227.18.178:9876"); //use dev box
        mqProducer.start();
        mqProducer.setRetryTimesWhenSendAsyncFailed(0);
    }

    //
    public void sendSyncMsgOneBroker() throws Exception {
        for (int i = 0; i < 6; i++) {
            Message msg = new Message("TestTopic2", "", ("Hello " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
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

    public void sendSyncMsgMultiBroker() throws Exception {
        for (int i = 0; i < 15; i++) {
            Message msg = new Message("testTopic5", "", ("Hello " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
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

    public void sendAsyncMsg() throws Exception {
        int count = 5;
        final CountDownLatch2 countDownLatch2 = new CountDownLatch2(count);
        for (int i = 0; i < count; i++) {
            final int index = i;
            Message msg = new Message("TestTopic", "TestTag", ("Hello Async " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            mqProducer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("send " + index + " result = " + sendResult);
                    countDownLatch2.countDown();
                }

                @Override
                public void onException(Throwable e) {

                }
            });
        }
        countDownLatch2.await(5000, TimeUnit.SECONDS);
        mqProducer.shutdown();
    }

}
