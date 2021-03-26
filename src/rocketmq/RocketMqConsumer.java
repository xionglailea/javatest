package rocketmq;

import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * <p>
 * create by xiongjieqing on 2021/3/24 14:41
 */
public class RocketMqConsumer {

    private final DefaultMQPushConsumer mqConsumer;

    public RocketMqConsumer() {
        mqConsumer = new DefaultMQPushConsumer("test4");
        mqConsumer.setNamesrvAddr("10.227.18.178:9876");
        mqConsumer.setConsumeThreadMin(1);
        mqConsumer.setConsumeThreadMax(4);
        try {
            mqConsumer.subscribe("testTopic4", "");

            ////one queue consume by one thread
            //mqConsumer.registerMessageListener(new MessageListenerOrderly() {
            //    @Override
            //    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
            //        return null;
            //    }
            //});


            mqConsumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (var msg : msgs) {
                    }
                    System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            mqConsumer.start();
            System.out.println("Consumer start");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new RocketMqConsumer();
    }

}
