package AStar;


import java.util.*;

/**
 * Created by jieqinxiong on 2017/5/15.
 */
public class AStar {

    public static void main(String[] args) {
        int[][] array = {
                { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
                { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                { 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1},
                { 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                { 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1},
                { 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1},
                { 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        AStar test = new AStar(array);
//        Random random = new Random();
//        long startTime = System.currentTimeMillis();
//        for(int i = 0 ; i < 10000000; i++){
//            Node start = new Node(random.nextInt(8), random.nextInt(12));
//            Node end = new Node(random.nextInt(8), random.nextInt(12));
//            test.findPath(start, end);
//            if(i % 10000 == 0){
//                System.out.println("-------------");
//            }
//        }
//        long endTime = System.currentTimeMillis();
//        float cost = (float) ((endTime - startTime) / 1000.0);
//        System.out.println("cost time is " + cost);
        Node start = new Node(0, 5);
        Node end = new Node(4, 10);
        Node path = test.findPath(start, end);
        if(path == null){
            System.out.println("Not find legal path");
            return;
        }
        List<Node> result = new ArrayList<>();
        result.add(path);
        while(path.parent != null){
            result.add(path.parent);
            path = path.parent;
        }
        System.out.println(result);
        for(Node node : result){
            array[node.x][node.y] = 9;
        }
        for(int i = 0; i < array.length; i++){
            int[] temp = array[i];
            for(int j = 0; j < temp.length; j++){
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static final int step = 10;

    private int[][] nodeMap;

    /**
     * A星寻路算法
     * @param nodes
     */
    public AStar(int[][] nodes){
        this.nodeMap = nodes;
    }

    public Node findPath(Node startNode, Node endNode){
        if(!canReach(startNode) || !canReach(endNode)){
            return null;
        }
        if(startNode.x == endNode.x && startNode.y == endNode.y){
            return startNode;
        }
        Map<Integer, Node> openList = new HashMap<>();  //用map提高检索速度
        Set<Node> closeList = new HashSet<>();
        openList.put(startNode.hashCode(), startNode);
        while(!openList.isEmpty()){
            Node currentNode = finaMinNodeInOpenList(openList);  //open列表是可达列表，每次在open列表里面找路径最短的开始计算,如果一直找不到，最后会从最坏的情况开始找
//            System.out.println(currentNode);                //如果把open列表都遍历完了还没找到，说明目标点不可达
            openList.remove(currentNode.hashCode());
            closeList.add(currentNode);
            List<Node> neighborNodes = findNeighborNodes(currentNode);
            for (Node node : neighborNodes) {
                if (closeList.contains(node) || !canReach(node)) {
                } else if (!openList.containsKey(node.hashCode())) {
                    openList.put(node.hashCode(), node);
                    calcFGHNotFind(currentNode, node, endNode);
                } else {
                    updateFGHFind(currentNode, node);
                }
            }
            Node find = openList.get(endNode.hashCode());
            if(null != find)
                return find;
        }
        return null;
    }

    public Node finaMinNodeInOpenList(Map<Integer, Node> openList){
        return openList.values().stream().min(Comparator.comparingDouble(o -> o.F)).get();//比较器
    }

    public List<Node> findNeighborNodes(Node currentNode){  //找到周围的八个相邻点
        List<Node> result = new ArrayList<>();
        for(int i = currentNode.x - 1; i <= currentNode.x + 1; i++){
            for(int j = currentNode.y - 1; j <= currentNode.y + 1; j++){
                try{
                    if(i == currentNode.x && j == currentNode.y)
                        continue;
                    int temp = nodeMap[i][j];
                    result.add(new Node(i, j));
                }catch (Exception e){
                    //do nothing  数组越界的异常捕捉，但是这里并不需要做什么 其实这种写法不好
                }
            }
        }
        return result;
    }

    public boolean canReach(Node node){
        return nodeMap[node.x][node.y] == 0;
    }

    public void calcFGHNotFind(Node current, Node toAdd, Node end){
        toAdd.parent = current;
        toAdd.H = calcH(toAdd, end);
        toAdd.G = calcG(current, toAdd);
        toAdd.calcF();
    }

    public void updateFGHFind(Node current, Node toAdd){
        double newG = current.G + sqrt(current, toAdd) * step;
        if(newG < toAdd.G){
            toAdd.parent = current;
            toAdd.G = newG;
            toAdd.calcF();
        }
    }

    public double calcG(Node current, Node toAdd){
        double sqrt = sqrt(current, toAdd);
        return sqrt * step + toAdd.parent.G;
    }

    public double calcH(Node toAdd, Node end){
        double sqrt = sqrt(toAdd, end);
        return sqrt * step;
    }

    public double sqrt(Node A, Node B){
        int dx = Math.abs(A.x - B.x);
        int dy = Math.abs(A.y - B.y);
        return Math.sqrt(dx * dx + dy * dy);
    }
}
