package reactor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

/**
 * jdk自带的响应式接口
 *
 * <p>
 * create by xiongjieqing on 2021/7/15 14:46
 */
public class FlowApiTest {

    public static void main(String[] args) throws Exception {
        //new FlowApiTest().testCompletableFuture();
        new FlowApiTest().testFlow();
    }

    public void testFlow() throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;
            private int costNums;
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                costNums++;
                //if (costNums >= 10) {
                //    subscription.cancel();
                //    return;
                //}
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscription.request(1);

                System.out.println(Thread.currentThread().getName() + " sub1 cost item " + item);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("cost error");
                subscription.cancel();
            }

            @Override
            public void onComplete() {
                System.out.println("sub1 cost complete! " + costNums);
            }
        };

        Flow.Subscriber<String> subscriber2 = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;
            private int costNums;
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                //costNums++;
                //if (costNums >= 10) {
                //    subscription.cancel();
                //    return;
                //}
                costNums++;
                subscription.request(1);

                System.out.println(Thread.currentThread().getName() + " sub2 cost item " + item);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("cost error");
                subscription.cancel();
            }

            @Override
            public void onComplete() {
                System.out.println("sub2 cost complete! " + costNums);
            }
        };

        publisher.subscribe(subscriber);
        publisher.subscribe(subscriber2);
        System.out.println("subscriber num = " + publisher.getSubscribers().size());
        for (int i = 1; i < 400; i++) {
            String item = i + " ";
            publisher.submit(item);  //会阻塞，保证每一个消费者都消费到
            //publisher.offer(item, null); //不会阻塞，如果消费者的缓冲区满了，就扔掉 or 尝试一次
            System.out.println("publisher create " + i);
        }
        //Thread.sleep(200);
        //for (int i = 100; i < 200; i++) {
        //    String item = i + " ";
        //    publisher.submit(item);
        //    System.out.println("publisher create " + i);
        //}
        publisher.close();
        Thread.sleep(1000);
    }

    public void testCompletableFuture() {
        try {
            CompletableFuture.runAsync(this::do1).thenRun(this::do2).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        CompletableFuture.supplyAsync(this::getString).thenApply((Function<String, String>) s -> {
            System.out.println("receive " + s);
            return s + " haha";
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end");
    }

    public void do1() {
        System.out.println("do 1");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getString() {
        System.out.println("get string");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "xiong";
    }

    public void do2() {
        System.out.println("do 2");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
