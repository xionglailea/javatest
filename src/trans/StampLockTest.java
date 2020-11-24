package trans;

/**
 * 乐观读写锁
 *  https://segmentfault.com/a/1190000015808032
 *  java aqs队列中使用的算法
 *  CLH 锁与 MCS 锁的原理大致相同，都是各个线程轮询各自关注的变量，来避免多个线程对同一个变量的轮询，从而从 CPU 缓存一致性的角度上减少了系统的消耗。
 * CLH 锁与 MCS 锁最大的不同是，MCS 轮询的是当前队列节点的变量，而 CLH 轮询的是当前节点的前驱节点的变量，来判断前一个线程是否释放了锁。
 * 都是公平的实现方式
 * <p>
 * create by xiongjieqing on 2020/11/23 16:55
 */
public class StampLockTest {

}
