package trans;

import java.util.*;

/**
 * Created by xiong on 2016/12/14.
 */
public class RoundMatch {
    public static void main(String[] args) {
        RoundMatch r = new RoundMatch();
        r.rank();
    }

    List<Pair> all = new ArrayList<>();
    Map<Integer, List<Pair>> result = new HashMap<>();

    public static class Pair{
        int f1;
        int f2;
        public Pair(int f1, int f2){
            this.f1 = f1;
            this.f2 = f2;
        }

        @Override
        public String toString() {
            return f1 + "-" + f2;
        }
    }

    public void rank(){
        List<Integer> allnums = Arrays.asList(1,2,3,4,5,6);
        for(int i = 0; i < allnums.size(); i++){
            for(int j = i + 1; j < allnums.size() ; j++){
                all.add(new Pair(allnums.get(i), allnums.get(j)));
            }
        }
        System.out.println(all);
        daily(all, allnums.size());
        System.out.println(result);
    }

    public void daily(List<Pair> allpair, int length){
        int group = allpair.size()/(length/2);
        for(int i = 1 ; i<=group; i++){
            List<Pair> temp = new ArrayList<>();
            for (Pair p : allpair) {
                if (!groupdup(temp, p)) {
                    List<Pair> totest = new ArrayList<>();
                    totest.addAll(temp);
                    totest.add(p);
                    if (totest.size() < length / 2) {
                        if (!resultdup(totest)) {
                            temp.add(p);
                        }
                    }else {
                        temp.add(p);
                    }
                    if (temp.size() == length / 2) {
                        result.put(i, temp);
                        allpair.removeAll(temp);
                        break;
                    }
                }
            }
        }
    }

    public boolean groupdup(List<Pair> temp, Pair toput){
        for(Pair p : temp){
            if(p.f1 == toput.f1 || p.f2 == toput.f2 || p.f1 == toput.f2 || p.f2 == toput.f1){
                return true;
            }
        }
        return false;
    }

    public boolean resultdup(List<Pair> toput) {
        for (List<Pair> temp : result.values()) {
            if(listdup(temp, toput)){
                return true;
            }
        }
        return false;
    }

    public boolean listdup(List<Pair> in, List<Pair> toput){
        int size = toput.size();
        Set<Integer> nums = new HashSet<>();
        for(Pair p : toput){
            nums.add(p.f1);
            nums.add(p.f2);
        }
        int i = 0;
        for(Pair p : in){
            if(nums.contains(p.f1) && nums.contains(p.f2)){
                i++;
            }
        }
        if(i == toput.size()){
            return true;
        }
        return false;
    }

}
