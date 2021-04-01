package rocketmq;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * <p>
 * create by xiongjieqing on 2021/3/24 14:41
 */
public class RocketMqConsumer {

    private final DefaultMQPushConsumer mqConsumer;

    public RocketMqConsumer() {
        mqConsumer = new DefaultMQPushConsumer("test3");
        mqConsumer.setNamesrvAddr("10.227.18.178:9876");

        try {
            mqConsumer.subscribe("testTopic4", "");

            //one queue consume by one thread
            mqConsumer.registerMessageListener(new MessageListenerOrderly() {

                Random random = new Random();

                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                    context.setAutoCommit(true);
                    for (MessageExt msg : msgs) {
                        // 可以看到每个queue有唯一的consume线程来消费, 订单对每个queue(分区)有序
                        System.out.println("consumeThread=" + Thread.currentThread().getName() + " queueId=" + msg.getQueueId() + ", " +
                            "content:" + new String(msg.getBody()));
                    }

                    try {
                        //模拟业务逻辑处理中...
                        TimeUnit.SECONDS.sleep(random.nextInt(5));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });


            //mqConsumer.registerMessageListener(new MessageListenerConcurrently() {
            //    @Override
            //    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            //        for (var msg : msgs) {
            //        }
            //        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
            //        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            //    }
            //});
            mqConsumer.start();
            System.out.println("Consumer start");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.setProperty("rocketmq.client.logUseSlf4j", "true");
        new RocketMqConsumer();
    }

}
