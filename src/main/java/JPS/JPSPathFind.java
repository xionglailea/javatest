package JPS;



import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

import static JPS.JPSNode.*;
import static JPS.JPSSearchLookupTable.*;

public class JPSPathFind {

    public static class SimpleNode{
        public int r; //行
        public int c; //列
        public SimpleNode(int r, int c){
            this.r = r;
            this.c = c;
        }

        @Override
        public String toString() {
            return "row=" + r + ";col=" + c;
        }
    }

    public static class PathFindResult{
        public Map<Integer, JPSPathFindingNode> temp = new HashMap<>();
        // 需要做更新操作，不适用优先队列，查找操作是O(n)
        //public PriorityQueue<JPSPathFindingNode> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.finalCost));
        //跳表的复杂度为O(n);
        public ConcurrentSkipListSet<JPSPathFindingNode> queue = new ConcurrentSkipListSet<>(Comparator.comparingInt(o -> o.finalCost));
    }

    private JPSMapCompute mapPreInfo;

    public JPSPathFind(int[][] gridInfo){
        this.mapPreInfo = new JPSMapCompute(gridInfo);
    }

    public JPSPathFind(JPSMapCompute mapPreInfo){
        this.mapPreInfo = mapPreInfo;
    }

    public List<SimpleNode> findPathBycoordinate(){
        return null;
    }

    public List<SimpleNode> findPathByGrid(int startR, int startC, int endR, int endC){
        JPSPathFindingNode node = findPath(startR, startC, endR, endC);
        if(node == null){
            return null;
        }
        LinkedList<SimpleNode> result = new LinkedList<>();
        result.addFirst(new SimpleNode(node.row, node.col));
        while (node.parent != null){
            node = node.parent;
            result.addFirst(new SimpleNode(node.row, node.col));
        }
        //如果有在某个方向连续的，删除中间的点
        if(result.size() >= 3){
            List<SimpleNode> toDel = new ArrayList<>();
            for(int i = 0; i < result.size() - 2; i++){
                int slope1 = calculateSlope(result.get(i), result.get(i+1));
                int slope2 = calculateSlope(result.get(i+1), result.get(i+2));
                if(slope1 == slope2){
                    toDel.add(result.get(i+1));
                }
            }
            result.removeAll(toDel);
        }
        return result;
    }

    private int calculateSlope(SimpleNode first, SimpleNode second){
        int diffR = second.r - first.r;
        int diffC = second.c - first.c;
        if(diffC == 0){
            return INFINITY;
        }else {
            return diffR / diffC;
        }
    }

    private JPSPathFindingNode findPath(int startR, int startC, int endR, int endC){
        if(mapPreInfo.isBlocked(startR, startC) || mapPreInfo.isBlocked(endR, endC)){
            return null;
        }
        JPSPathFindingNode startNode = new JPSPathFindingNode();
        startNode.row = startR;
        startNode.col = startC;
        startNode.parent = null;
        startNode.state = JPSPathFindingNode.NodeState.NODESTATUS_OPEN;
        if(startR == endR && startC == endC){
            return startNode;
        }
        PathFindResult tempResult = new PathFindResult();
        JPSPathFindingNode endNode = new JPSPathFindingNode();
        endNode.row = endR;
        endNode.col = endC;
        endNode.parent = null;

        searchAll(startNode, mapPreInfo.getJPSNode(startR, startC), endNode, tempResult);
        startNode.state = JPSPathFindingNode.NodeState.NODESTATUS_CLOSED;

        while (!tempResult.queue.isEmpty()){
//            System.out.println(tempResult.queue);
            JPSPathFindingNode curNode = tempResult.queue.pollFirst(); //这里要成对的添加删除
//            tempResult.temp.remove(curNode.hashCode());
            if(curNode.equals(endNode)){
                return curNode;
            }
            JPSNode jpsNode = mapPreInfo.getJPSNode(curNode.row, curNode.col);
            //根据当前格子周围的格子情况，在有限的方向进行查找操作
            char[] lookUpTable = JPSSearchLookupTable[jpsNode.blockBitField * 8 + curNode.dirFromParent];
            for(int i = 1; i <= lookUpTable[0]; i++){
                char dir = lookUpTable[i];
                searchOneDir(curNode, endNode, dir, jpsNode.jumpDistance[dir], tempResult);
            }
            curNode.state = JPSPathFindingNode.NodeState.NODESTATUS_CLOSED;
        }
        return null;
    }

    private void searchAll(JPSPathFindingNode curNode, JPSNode jpsNode, JPSPathFindingNode endNode, PathFindResult result){
        searchOneDir(curNode, endNode, JPSDIR_DOWN, jpsNode.jumpDistance[JPSDIR_DOWN], result);
        searchOneDir(curNode, endNode, JPSDIR_DOWNLEFT, jpsNode.jumpDistance[JPSDIR_DOWNLEFT], result);
        searchOneDir(curNode, endNode, JPSDIR_LEFT, jpsNode.jumpDistance[JPSDIR_LEFT], result);
        searchOneDir(curNode, endNode, JPSDIR_UPLEFT, jpsNode.jumpDistance[JPSDIR_UPLEFT], result);
        searchOneDir(curNode, endNode, JPSDIR_UP, jpsNode.jumpDistance[JPSDIR_UP], result);
        searchOneDir(curNode, endNode, JPSDIR_UPRIGHT, jpsNode.jumpDistance[JPSDIR_UPRIGHT], result);
        searchOneDir(curNode, endNode, JPSDIR_RIGHT, jpsNode.jumpDistance[JPSDIR_RIGHT], result);
        searchOneDir(curNode, endNode, JPSDIR_DOWNRIGHT, jpsNode.jumpDistance[JPSDIR_DOWNRIGHT], result);
    }

    private void searchOneDir(JPSPathFindingNode curNode, JPSPathFindingNode endNode, int dir, int jumpDistance, PathFindResult result){
        //优先向目标方向进行查找
        if(isDirToEndNode(curNode, endNode, dir)){
            int detR = Math.abs(endNode.row - curNode.row);
            int detC = Math.abs(endNode.col - curNode.col);
            int diff = detC < detR ? ( detC != 0 ? detC : detR) : ( detR != 0 ? detR : detC);
            int absJumpDistance = Math.abs(jumpDistance);
            if(diff <= absJumpDistance){
                int nR = curNode.row + JPSOFFSETRC[dir][ROW] * diff;
                int nC = curNode.col + JPSOFFSETRC[dir][COL] * diff;
                int givenCost = curNode.givenCost + diff * JPSDirCost[dir];
                addNode(nR, nC, givenCost, curNode, endNode, dir, result);
                return;
            }
        }
        //其次按照跳点方向查找
        if(jumpDistance > 0){
            int nR = curNode.row + JPSOFFSETRC[dir][ROW] * jumpDistance;
            int nC = curNode.col + JPSOFFSETRC[dir][COL] * jumpDistance;
            int givenCost = curNode.givenCost + jumpDistance * JPSDirCost[dir];
            addNode(nR, nC, givenCost, curNode, endNode, dir, result);
        }
    }

    private void addNode(int nR, int nC, int givenCost, JPSPathFindingNode curNode, JPSPathFindingNode endNode, int dir, PathFindResult result){
        JPSPathFindingNode newNode = new JPSPathFindingNode();
        newNode.row = nR;
        newNode.col = nC;
        JPSPathFindingNode old = result.temp.get(newNode.hashCode()); //只要这个hashcode唯一，就能正确获取到node
        if(old == null){
            result.temp.put(newNode.hashCode(), newNode);
            int heuristicCost;
            int dr = Math.abs(endNode.row - nR);
            int dc = Math.abs(endNode.col - nC);
            if(dc < dr){
                heuristicCost = (dc * (JPS_DIAGONAL_COST - JPS_CARDINAL_COST)) + dr * JPS_CARDINAL_COST;
            }else {
                heuristicCost = (dr * (JPS_DIAGONAL_COST - JPS_CARDINAL_COST)) + dc * JPS_CARDINAL_COST;
            }
            newNode.parent = curNode;
            newNode.dirFromParent = (char) dir;
            newNode.givenCost = givenCost;
            newNode.finalCost = givenCost + heuristicCost;
            newNode.state = JPSPathFindingNode.NodeState.NODESTATUS_OPEN;
            result.queue.add(newNode);
        }else if(givenCost < old.givenCost && old.state == JPSPathFindingNode.NodeState.NODESTATUS_OPEN) {
            newNode.parent = curNode;
            newNode.dirFromParent = (char) dir;
            newNode.givenCost = givenCost;
            newNode.finalCost = givenCost + old.finalCost - old.givenCost;
            result.temp.put(newNode.hashCode(), newNode);//移除老的，加入新的
            result.queue.remove(old);
            result.queue.add(newNode);
        }
    }

    private boolean isDirToEndNode(JPSPathFindingNode curNode, JPSPathFindingNode endNode, int dir){
        switch (dir){
            case JPSDIR_LEFT:{
                return curNode.row == endNode.row && curNode.col > endNode.col;
            }
            case JPSDIR_RIGHT:{
                return curNode.row == endNode.row && curNode.col < endNode.col;
            }
            case JPSDIR_DOWN:{
                return curNode.row < endNode.row && curNode.col == endNode.col;
            }
            case JPSDIR_UP:{
                return curNode.row > endNode.row && curNode.col == endNode.col;
            }
            case JPSDIR_DOWNLEFT:{
                return curNode.row < endNode.row && curNode.col > endNode.col;
            }
            case JPSDIR_DOWNRIGHT:{
                return curNode.row < endNode.row && curNode.col < endNode.col;
            }
            case JPSDIR_UPLEFT:{
                return curNode.row > endNode.row && curNode.col > endNode.col;
            }
            case JPSDIR_UPRIGHT:{
                return curNode.row > endNode.row && curNode.col < endNode.col;
            }
            default:
                return false;
        }
    }



}
