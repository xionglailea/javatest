package netty;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.util.ResourceLeakDetector;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 测试netty
 *
 * <p>
 * create by xiongjieqing on 2021/2/5 16:43
 */
public class TestNetty {

    private static Executor executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        System.setProperty("io.netty.recycler.ratio", "0");
        System.setProperty("io.netty.recycler.delayedQueue.ratio", "0");
//        设置该属性会严重影响内存（正式时使用 simple）（调试时使用 paranoid）
        System.setProperty("io.netty.leakDetectionLevel", "simple");
        int num = 8;
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(1 * 1000);
                    byteBuf.writeInt(10);
                    executor.execute(() -> {
                        try {
                            Thread.sleep(new Random().nextInt(4));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        byteBuf.release();
                    });
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Field field = AbstractByteBuf.class.getDeclaredField("leakDetector");
                    field.setAccessible(true);
                    ResourceLeakDetector<ByteBuf> detector = (ResourceLeakDetector<ByteBuf>) field.get(null);
                    Field target = ResourceLeakDetector.class.getDeclaredField("allLeaks");
                    target.setAccessible(true);
                    Set allLeaks = (Set<?>)target.get(detector);

                    while (true) {
                        System.out.println(allLeaks.size());
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }


}
