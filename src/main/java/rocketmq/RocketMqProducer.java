package rocketmq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public void sendOrder() throws Exception {
        // 订单列表
        List<OrderStep> orderList = buildOrders();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        for (int i = 0; i < 10; i++) {
            // 加个时间前缀
            String body = dateStr + " Hello RocketMQ " + orderList.get(i);
            Message msg = new Message("testTopic4", "", "KEY" + i, body.getBytes());

            SendResult sendResult = mqProducer.send(msg, (mqs, msg1, arg) -> {
                Long id = (Long) arg;  //根据订单id选择发送queue
                long index = id % mqs.size();
                return mqs.get((int) index);
            }, orderList.get(i).getOrderId());//订单id

            System.out.printf("SendResult status:%s, queueId:%d, body:%s",
                sendResult.getSendStatus(),
                sendResult.getMessageQueue().getQueueId(),
                body);
        }

        mqProducer.shutdown();
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

    /**
     * 生成模拟订单数据
     */
    private List<OrderStep> buildOrders() {
        List<OrderStep> orderList = new ArrayList<OrderStep>();

        OrderStep orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        return orderList;
    }

    /**
     * 订单的步骤
     */
    private static class OrderStep {
        private long orderId;
        private String desc;

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "OrderStep{" +
                "orderId=" + orderId +
                ", desc='" + desc + '\'' +
                '}';
        }
    }


}
