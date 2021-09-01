import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

/**
 * 测试
 *
 * <p>
 * create by xiongjieqing on 2021/7/18 11:51
 */
public class TestReactor {

    private static Logger logger = LoggerFactory.getLogger(TestReactor.class);

    //map是同步处理数据，flatMap是异步处理数据。
    //
    //map()返回的是最终订阅的对象。
    //
    //而flatMap()是个方法，返回的是一个流，也就是一个Flux或者Mono。

    @Test
    public void test1() {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4)
            .log()
            .map(i -> {
                return i * 2;
            });
        flux.subscribe(e -> logger.info("get:{}", e));

    }

    @Test
    public void testFlatMap() {
        Flux.just(1, 2, 3, 4)
            .log()
            .flatMap(e -> Flux.just(e * 2))
            .subscribe(e -> logger.info("get:{}", e));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        Flux.just(1, 2, 3, 4).log().sort().takeLast(1).subscribe(e -> System.out.println(e));
    }

    @Test
    public void test3() {
        Flux.generate(
            () -> 0,
            (state, sink) -> {
                sink.next("3 x " + state + " = " + 3 * state);
                if (state == 10) {
                    sink.complete();
                }
                return state + 1;
            }).log().subscribe(e -> System.out.println("get value = " + e));
    }

    @Test
    public void test4() {
        System.out.println(Thread.currentThread().getName());
        Mono<String> blockWrapper = Mono.fromCallable(() -> {
            System.out.println("task complete in thread :" + Thread.currentThread().getName());
            Thread.sleep(2000);
            return "xiong";
        }).log();
        //Run subscribe, onSubscribe and request on a specified Scheduler's Scheduler.Worker.
        // As such, placing this operator anywhere in the chain will also
        // impact the execution context of onNext/onError/onComplete signals
        // from the beginning of the chain up to the next occurrence of a publishOn.

        blockWrapper.subscribeOn(Schedulers.boundedElastic()).doOnSubscribe(e -> {
            System.out.println("subscribe " + Thread.currentThread().getName());
        }).subscribe(s -> Mono.just(s).log().publishOn(Schedulers.single()).subscribe(e -> {
            System.out.println("last accept " + Thread.currentThread().getName() + " " + e);
        }, null, new Runnable() {
            @Override
            public void run() {
                System.out.println("last complete " + Thread.currentThread().getName());
            }
        }));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test5() {
        Flux.just("xiong", "jie", "qing").zipWith(Flux.just(1, 2)).subscribe(new Consumer<Tuple2<String, Integer>>() {
            @Override
            public void accept(Tuple2<String, Integer> objects) {
                System.out.println("accept " + objects.getT1() + " " + objects.getT2());
            }
        });
    }

    @Test
    public void test6() {

        System.out.println(Thread.currentThread().getName());

        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux1 = Flux
            .range(1, 2)
            .map(i -> {
                System.out.println(Thread.currentThread().getName() + " map " + i);
                return 10 + i;
            })
            .publishOn(s).log()
            .map(i -> {
                System.out.println(Thread.currentThread().getName() + " map " + i);
                return "value " + i;
            });

        flux1.subscribe(i -> System.out.println(Thread.currentThread().getName() + " accept " + i));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testMultiSubscribers() throws InterruptedException {
        Flux<Integer> source = Flux.range(1, 3).log()
            .doOnSubscribe(s -> System.out.println("subscribed to source"));

        ConnectableFlux<Integer> co = source.publish();

        co.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(Thread.currentThread().getName() + " sub1 receive " + integer);
            }
        }, e -> {
        }, () -> {
        });
        co.publishOn(Schedulers.boundedElastic()).log().subscribe(integer -> {
            try {
                System.out.println(Thread.currentThread().getName() + " sub2 receive " +  integer);
                Thread.sleep(1000);
                System.out.println(integer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, e -> {
        }, () -> {
        });

        System.out.println("done subscribing");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("will now connect");

        co.connect();

        Thread.sleep(5000);
    }

}
