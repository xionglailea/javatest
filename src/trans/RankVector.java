package trans;

/**
 * Created by arctest on 2015/12/25.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public final class RankVector {
    public final static class Record {
        public final long id;
        public int rank;
        public long value;

        public Record(long id, long value, int rank) {
            this.id = id;
            this.value = value;
            this.rank = rank;
        }

        @Override
        public String toString() {
            return String.format("{rank=%d,id=%d,value=%d}", rank, id, value);
        }
    }


    private final ArrayList<Record> records = new ArrayList<>();
    private final HashMap<Long, Record> recordMap = new HashMap<>();
    private final int capacity;

    public RankVector(int capacity) {
        this.capacity = capacity;
    }

    public RankVector(int capacity, List<Record> rs) {
        this.capacity = capacity;
        init(rs);
    }

    public void init(List<Record> rs) {
        for(Record r : rs) {
            final Record nr = new Record(r.id, r.value, r.rank);
            this.records.add(nr);
            this.recordMap.put(nr.id, nr);
        }
    }

    public int getSize() {
        return records.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<Record> getRanks() {
        return records;
    }

    public Record getRank(long id) {
        return recordMap.get(id);
        //return r != null ? r.toData() : null;
    }

    @Override
    public RankVector clone() {
        return new RankVector(this.capacity, this.records);
    }

    public void update(long id, long value) {
        final Record r = recordMap.get(id);
        if (r != null) {
            r.value = value;
            int cur = r.rank;
            Record cr = null;
            if (value > r.value) {
                for (; cur > 0 && (cr = records.get(cur - 1)).value < value; cur--) {
                    cr.rank++;
                    records.set(cur, cr);
                }
                r.rank = cur;
                records.set(cur, r);
            } else if (value < r.value) {
                for (int maxrank = records.size() - 1 ; cur < maxrank && (cr = records.get(cur + 1)).value > value; cur++) {
                    cr.rank--;
                    records.set(cur, cr);
                }
            }
            r.rank = cur;
            records.set(cur, r);
        } else {
            final int count = records.size();
            if(count < capacity) {
                records.add(new Record(id, value, count));
            } else {
                final Record last = records.get(count - 1);
                if(value <= last.value)
                    return;
                recordMap.remove(last.id);
                records.set(count - 1, new Record(id, value, count - 1));
            }

            int cur = records.size() - 1;
            final Record nr = records.get(cur);
            for (Record cr ; cur > 0 && (cr = records.get(cur - 1)).value < value; cur--) {
                cr.rank++;
                records.set(cur, cr);
            }
            nr.rank = cur;
            records.set(cur, nr);
        }
    }


    public void ajustRankByPrevRank(RankVector prevData) {
        int maxRank = records.size() - 1;
        for (int k = 0; k < maxRank; k++) {
            final int firstRank = k;
            final long value = records.get(k).value;

            while (k < maxRank && records.get(k + 1).value == value)
                k++;

            final int lastRank = k;
            // 依据prevData表对相同value的的id排序
            // 规则 为 在prevData中的id排在不在的之前
            // 如果都在,则rank小的排在大的之前
            for (int i = firstRank; i < lastRank; i++) {
                for (int j = i + 1; j <= lastRank; j++) {
                    final Record mi = records.get(i);
                    final Record mj = records.get(j);
                    final Record ri = prevData.recordMap.get(mi.id);
                    final Record rj = prevData.recordMap.get(mj.id);
                    boolean swap = false;
                    if (ri == null) {
                        swap = rj != null;// || mi.id > mj.id;
                    } else {
                        swap = rj != null && ri.rank > rj.rank;
                    }
                    if (swap) {
                        mj.rank = i;
                        mi.rank = j;
                        records.set(i, mj);
                        records.set(j, mi);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Random r = new Random(1218);
        RankVector v = new RankVector(20);

        for(int i = 0 ; i < 100 ; i++) {
            v.update(i, r.nextInt(10000));
        }
        System.out.println(v.getRanks());
    }

}
