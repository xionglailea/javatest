package AStar;

import java.util.*;

/**
 * Created by jieqinxiong on 2017/5/13.
 */
public class Node {
    public int x;
    public int y;

    public double F;
    public double G;
    public double H;

    public Node parent;
    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void calcF(){
        this.F = this.G + this.H;
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }

    @Override
    public int hashCode() {
        return (x << 16) + y;
    }

    @Override
    public boolean equals(Object obj) {  //对象比较，需要重写该方法,而且一般情况下都要重写hashcode方法
        if (!(obj instanceof Node)){
            return false;
        }
//        System.out.println("aaa");
        Node temp = (Node) obj;
        return hashCode() == temp.hashCode() && temp.x == x && temp.y == y;
    }



    public static void main(String[] args) {
        List<Node> test = new ArrayList<Node>();
        Node a = new Node(1,2);
        test.add(a);
        Node b = new Node(1,2);
        System.out.println(a == b);
        System.out.println(test.contains(b));
        Set<Node> s = new TreeSet<>();
        s.add(a);
        s.add(b);
    }
}
