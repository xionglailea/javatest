package trans;
import java.util.*;
/**
 * 小根堆，第一个元素始终是数组中最小的那一个
 */
public class MinPQ<Key> implements Iterable<Key> {
    private Key[] pq;                    // store items at indices 1 to N
    private int N;                       // number of items on priority queue
    private Comparator<Key> comparator;  // optional comparator
    private Map<Long,Key> pqMap;

    @SuppressWarnings("unchecked")
    public MinPQ(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        N = 0;
        pqMap = new HashMap<>();
    }

    public MinPQ() {
        this(1);
    }


    public MinPQ(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[initCapacity + 1];
        N = 0;
        pqMap = new HashMap<>();
    }


    public MinPQ(Comparator<Key> comparator) { this(1, comparator); }


    public void init(Key[] keys) {
        N = keys.length;
        pq = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < N; i++)
            pq[i+1] = keys[i];
        for (int k = N/2; k >= 1; k--)
            sink(k);
        assert isMinHeap();
    }

    public void set(MinPQ origin){
        int length = origin.size();
        pq = (Key[]) new Object[length + 1];
        for (int i = 1; i <= length; i++) {
            pq[i] =(Key) origin.getKeys()[i];
        }
        N = length;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }


    private void resize(int capacity) { //调整容量
        assert capacity > N;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= N; i++) temp[i] = pq[i];
        pq = temp;
    }


    public void insert(Key x) {
        if (N == pq.length - 1) resize(2 * pq.length); //如果容量不够了，就进行扩容
        pq[++N] = x;
        swim(N);
        assert isMinHeap();
    }

    /**
     * 移除并返回最小的元素
     */
    public Key delMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        exch(1, N);
        Key min = pq[N--];
        sink(1);
        pq[N+1] = null;         // avoid loitering and help with garbage collection
        if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length  / 2);
        assert isMinHeap();
        return min;
    }

    public Key delMinWithOutResize(){
        exch(1, N);
        Key min = pq[N--];
        sink(1);
        pq[N+1] = null;
        return min;
    }

    private MinPQ copy(){
        MinPQ newPQ = new MinPQ(this.comparator);
        newPQ.set(this);
        return  newPQ;
    }

    /**
     *  使用这种排序方式和使用迭代器相比减少了插入和重置数组大小的过程
     * @return
     */
    public Key[] sort(){
        MinPQ toSort = copy();
        if(toSort.isEmpty())
            return null;
        Key[] sorted = (Key[]) new Object[toSort.size() + 1];
        int i = 0;
        while (!toSort.isEmpty()){
            sorted[++i] = (Key) toSort.delMinWithOutResize();
        }
        return sorted; //记住，返回的仅仅是一个object
    }

    @SuppressWarnings("unchecked")
    public Key[] getKeys(){
        return pq;
    }
    /***********************************************************************
    * 辅助函数,上浮，下沉，比较，交换
    **********************************************************************/

    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }


    private boolean greater(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) > 0;
        }
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    private boolean isMinHeap() {
        return isMinHeap(1);
    }

    private boolean isMinHeap(int k) {
        if (k > N) return true;
        int left = 2*k, right = 2*k + 1;
        if (left  <= N && greater(k, left))  return false;
        if (right <= N && greater(k, right)) return false;
        return isMinHeap(left) && isMinHeap(right);
    }


   /***********************************************************************
    * 构建一个迭代器，可以按升序返回整个序列，性能待定
    **********************************************************************/

    public Iterator<Key> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Key> {
        // create a new pq
        private MinPQ<Key> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (comparator == null) copy = new MinPQ<Key>(size());
            else                    copy = new MinPQ<Key>(size(), comparator);
            for (int i = 1; i <= N; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }


    public static void main(String[] args){
        Integer[] testNum = {8,7,9,10,5,3,4,1,2,6};
        MinPQ<Integer> pq = new MinPQ<>();
        pq.init(testNum);
        System.out.println("sorted");
//        Iterator itea = pq.iterator();
//        while (itea.hasNext()){
//            System.out.print(itea.next() + " ");
//        }
        Object[] sorted = pq.sort();
        for(Object i : sorted){
            System.out.print(i+ " ") ;
        }

        System.out.println("\n" + "not sorted ");
        try {
            Integer[] notsorted = pq.getKeys();
            for (Object i : notsorted) {
                System.out.print(i + " ");
            }
        }catch (ClassCastException ex){
//            System.out.println(ex.toString());
            ex.printStackTrace(); //这里是直接捕捉了异常，并进行了处理，如果是使用throw 抛出异常，那么必须在上层的某个地方进行处理
        } //只有进行了正确的处理程序才能继续运行下去，否者就直接中断了

        MinPQ<Record> test = new MinPQ<>((o1, o2) -> o1.value > o2.value ? 1 : -1);
        test.insert(new Record(1,8));
        test.insert(new Record(1,7));
        test.insert(new Record(1,9));
        test.insert(new Record(1,10));
        test.insert(new Record(1,5));
        test.insert(new Record(1,3));
        test.insert(new Record(1,4));
        test.insert(new Record(1,1));
        test.insert(new Record(1,2));
        test.insert(new Record(1, 6));
        System.out.println("\n");
        Iterator it = test.iterator(); //使用迭代器
        while(it.hasNext()){
            System.out.println(it.next() + " ");
        }
        Object[] a = test.getKeys();
        for(Object i : a){
            System.out.println(i);
        }
        Object[] b = test.sort(); //使用直接删除，然后收集就是一个有序数组了
        for(Object i : b){
            System.out.println(i);
        }

    }

    public static class Record{
        public int id;
        public int value;
        public Record(int id, int value){
            this.id = id;
            this.value = value;
        }
        @Override
        public String toString() {
            return "id: " + id + ", value" + value;
        }
    }
}
