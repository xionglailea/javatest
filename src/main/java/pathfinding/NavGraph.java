package pathfinding;


import java.util.*;

public final class NavGraph {

    final double NEARBY_DISTANCE = 0.05;
    private final List<EdgeInfo> edges = new ArrayList<>();
    private final ArrayList<ConvexInfo> convexs = new ArrayList<ConvexInfo>();
    private final ArrayList<Node> allNodes = new ArrayList<Node>();
    private final short[][] nodePaths; //
    private final int COST_OF_NODE = 1;
    private final long INFINITY = 1000000L;
    public double PAD_DISTANCE = 3;
    private float minSearchRangeX = 2f;
    private float minSearchRangeZ = 2f;
    private int maxConvexInOneNode = 3; //一个搜索区域内最多有几个多边形
    private SearchManager searchManager;

    public NavGraph(Mesh g) {
        g.check();

        final HashMap<Edge, EdgeInfo> edgeInfoMap = new HashMap<>();
        // 构造出多边形所有边
        int nextConvexId = 0;
        for (Convex convex : g.convexs) {
            final ConvexInfo newConvex = new ConvexInfo(nextConvexId++, convex);
            convexs.add(newConvex);

            final ArrayList<Vertex> vertexs = convex.vertexs;
            for (int i = 0, n = vertexs.size(); i < n; i++) {
                final Edge edge = new Edge(vertexs.get(i), vertexs.get((i + 1) % n));
                EdgeInfo einfo = edgeInfoMap.get(edge);
                if (einfo == null) {
                    einfo = new EdgeInfo(edge);
                    edgeInfoMap.put(edge, einfo);
                }
                newConvex.edges.add(einfo);
                einfo.convexs.add(newConvex);
            }
        }
        // 构造出所有路径结点
        for (EdgeInfo e : edgeInfoMap.values()) {
            final Vector3 v1 = e.edge.v1.position;
            final Vector3 v2 = e.edge.v2.position;
            Vector3 ab = v2.sub(v1);
            final double edgeLength = ab.getMagnitude();
            // 每条边至少拆分成两段
            final int splitNum = Math.max((int) (edgeLength / g.edgeNodeSplitLength) + 1, 2);
            for (int i = 1, n = splitNum; i < n; i++) {
                final Node newNode = new Node(allNodes.size(),
                    new Vector3(v1.x + i * ab.x / n, v1.y + i * ab.y, v1.z + i * ab.z / n),
                    e.edge);
                allNodes.add(newNode);
                e.nodes.add(newNode);
            }
            // 记录多边形所有结点,每个结点所在的多边形
            for (ConvexInfo c : e.convexs) {
                c.nodes.addAll(e.nodes);
                for (Node n : e.nodes) {
                    c.nodeIds.add(n.id);
                    n.convexs.add(c);
                }
            }
        }
        this.edges.addAll(edgeInfoMap.values());

        // 构造初始的连接图
        for (ConvexInfo c : convexs) {
            final ArrayList<Node> nodes = c.nodes;
            final int nodeNum = nodes.size();
            for (int i = 0; i < nodeNum - 1; i++) {
                for (int j = i + 1; j < nodeNum; j++) {
                    final Node inode = nodes.get(i);
                    final Node jnode = nodes.get(j);
                    final int cost = calcCost(inode.position, jnode.position);
                    inode.connections.put(jnode.id, new Connection(jnode, cost));
                    jnode.connections.put(inode.id, new Connection(inode, cost));
                }
            }
        }

        final int nodeNum = allNodes.size();
        System.out.printf("navmesh:%s total node num:%d\n", g.name, nodeNum);

        // Floyd 算法. 准备路径表与距离表

        final int[][] nodeDistances = new int[nodeNum][nodeNum];
        this.nodePaths = new short[nodeNum][nodeNum];

        final long HINT_TIME = 5000;
        long prevt = System.currentTimeMillis();
        for (int i = 0; i < nodeNum; i++) {
            Node inode = allNodes.get(i);
            nodeDistances[i][i] = 0;
            nodePaths[i][i] = (short) i;
            for (int j = i + 1; j < nodeNum; j++) {
                nodeDistances[i][j] = (int) INFINITY;
                nodePaths[i][j] = -1;
                nodeDistances[j][i] = (int) INFINITY;
                nodePaths[j][i] = -1;
            }

            for (Connection c : inode.connections.values()) {
                final int j = c.to.id;
                nodeDistances[i][j] = c.cost;
                nodePaths[i][j] = (short) j;
            }
        }

        //if(!load(cacheFile)) {

        // 三重循环,计算出两两之间最短距离与最短路径
        for (int k = 0; k < nodeNum; k++) {
            final long now = System.currentTimeMillis();
            // 计算时间有点长,每隔一段时间提示一下
            if (now > prevt + HINT_TIME) {
                prevt = now;
                System.out.printf("mesh:%s calc path and distance. %d/%d\n", g.name, k, nodeNum);
            }
            for (int i = 0; i < nodeNum; i++) {
                if (nodePaths[i][k] < 0) {
                    continue;
                }
                for (int j = 0; j < nodeNum; j++) {
                    if (nodePaths[i][k] < 0 || nodePaths[k][j] < 0) {
                        continue;
                    }
                    final int newDistance = nodeDistances[i][k] + nodeDistances[k][j] + COST_OF_NODE;
                    if (newDistance < nodeDistances[i][j]) {
                        nodeDistances[i][j] = newDistance;
                        nodePaths[i][j] = nodePaths[i][k];
                    }
                }
            }
        }
        //calcCompatRate(nodePaths);
        //save(cacheFile);
        // }
        searchManager = new SearchManager(g.range, convexs);
    }

    private int calcCost(Vector3 v1, Vector3 v2) {
        final double dx = v1.x - v2.x;
        final double dz = v1.z - v2.z;
        return (int) (Math.sqrt(dx * dx + dz * dz) * 10);
    }

    private double calcCost(Vector3 v) {
        return v.getXZMagnitude();
    }

    private double calcSquareCost(Vector3 d) {
        return d.x * d.x + d.z * d.z;
    }

    private void calcCompatRate(short[][] data) {
        int row = data.length;
        int col = data[0].length;
        int n = 0;
        short last = -100;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (data[i][j] != last) {
                    last = data[i][j];
                    n++;
                }
            }
        }
        final int total = row * col;
        System.out.printf("row:%s line:%s total:%s n:%s compatrate:%.2f\n", row, col, total, n, (n * 2.0 / total));
    }

    private Node chooseNearestNode(Vector3 pos, Collection<Node> nodes) {
        Node node = null;
        double distance = INFINITY;
        for (Node n : nodes) {
            final double d = calcCost(pos, n.position);
            if (d < distance) {
                node = n;
                distance = d;
            }
        }
        return node;
    }

    private void chooseNearestPositionAndNode(Vector3 pos, Collection<EdgeInfo> edges, PositionInfo pi) {
        Vector3 nearestPos = null;
        EdgeInfo nearestEdge = null;
        double minSquareDistance = INFINITY * INFINITY;
        for (EdgeInfo e : edges) {
            final Vector3 v1 = e.edge.v1.position;
            final Vector3 v2 = e.edge.v2.position;
            final Vector3 v12 = v1.sub(v2);
            final Vector3 p1 = pos.sub(v1);
            final Vector3 p2 = pos.sub(v2);
            double newSquareDistance;
            if (p1.dotXZ(v12) >= 0) {
                newSquareDistance = calcSquareCost(p1);
                if (newSquareDistance < minSquareDistance) {
                    minSquareDistance = newSquareDistance;
                    nearestPos = v1;
                    nearestEdge = e;
                }
            } else if (p2.dotXZ(v12) <= 0) {
                newSquareDistance = calcSquareCost(p2);
                if (newSquareDistance < minSquareDistance) {
                    minSquareDistance = newSquareDistance;
                    nearestPos = v2;
                    nearestEdge = e;
                }
            } else {
                newSquareDistance = MathUtil.vertex2lineXZSquareDistance(v1, v2, pos);
                if (newSquareDistance < minSquareDistance) {
                    minSquareDistance = newSquareDistance;
                    double delta = Math.sqrt(calcSquareCost(p2) - newSquareDistance);
                    nearestPos = v2.plus(v12.multi(delta / calcCost(v12)));
                    nearestEdge = e;
                }
            }

        }
        pi.nearbyReachablePosition = nearestPos;
        pi.node = chooseNearestNode(nearestPos, nearestEdge.convexs.get(0).nodes);
    }

    private Vector3 findNearestPosition(Vector3 pos, Collection<EdgeInfo> edges) {
        Vector3 nearestPos = null;
        double minSquareDistance = INFINITY * INFINITY;
        for (EdgeInfo e : edges) {
            final Vector3 v1 = e.edge.v1.position;
            final Vector3 v2 = e.edge.v2.position;
            final Vector3 v12 = v1.sub(v2);
            final Vector3 p1 = pos.sub(v1);
            final Vector3 p2 = pos.sub(v2);
            double newSquareDistance;
            if (p1.dotXZ(v12) >= 0) {
                newSquareDistance = calcSquareCost(p1);
                if (newSquareDistance < minSquareDistance) {
                    minSquareDistance = newSquareDistance;
                    nearestPos = v1;
                }
            } else if (p2.dotXZ(v12) <= 0) {
                newSquareDistance = calcSquareCost(p2);
                if (newSquareDistance < minSquareDistance) {
                    minSquareDistance = newSquareDistance;
                    nearestPos = v2;
                }
            } else {
                newSquareDistance = MathUtil.vertex2lineXZSquareDistance(v1, v2, pos);
                if (newSquareDistance < minSquareDistance) {
                    minSquareDistance = newSquareDistance;
                    double delta = Math.sqrt(calcSquareCost(p2) - newSquareDistance);
                    nearestPos = v2.plus(v12.multi(delta / calcCost(v12)));
                }
            }

        }
        return nearestPos;
    }

    private boolean lineCrossSegment(Vector3 a, Vector3 b, Edge e) {
        return MathUtil.lineCrossSegment(a, b, e.v1.position, e.v2.position);
    }

    private boolean lineCrossAllSegments(Vector3 a, Vector3 b, ArrayList<Node> ns) {
        return ns.stream().allMatch(n -> lineCrossSegment(a, b, n.edge));
    }

    public void dumpPath() {
        System.out.println("==========dumpPath. begin=============");
        StringBuilder s = new StringBuilder();
        s.append(String.format("%10s", ""));
        final int nodeNum = allNodes.size();
        for (int i = 0; i < nodeNum; i++) {
            s.append(String.format("%10s", i));
        }
        s.append("\n");
        for (int i = 0; i < nodeNum; i++) {
            s.append(String.format("%10s", i));
            for (int j = 0; j < nodeNum; j++) {
                s.append(String.format("%10s", nodePaths[i][j]));
            }
            s.append("\n");
        }
        System.out.println(s);
        System.out.println("==========dumpPath. end=============");
    }

    public PositionInfo getPositionInfo(Vector3 pos) {
        final PositionInfo pi = new PositionInfo();
        final ConvexInfo convex = convexs.stream().filter(c -> c.convex.contains(pos)).findFirst().orElse(null);
        if (convex != null) {
            pi.convex = convex;
            pi.node = chooseNearestNode(pos, convex.nodes);
        } else {
            chooseNearestPositionAndNode(pos, this.edges, pi);
        }
        return pi;
    }

    public ConvexInfo findConvexByPoint(Vector3 point) {
        return searchManager.search(point);
    }

    public boolean isValidPosition(Vector3 pos) {
        for (ConvexInfo convex : convexs) {
            final Convex c = convex.convex;
            c.check();
            if (c.contains(pos)) {
                return true;
            }
        }
        return false;
    }

    public Path findPath(Vector3 from, Vector3 to) {
        final PositionInfo fromPi = getPositionInfo(from);
        final PositionInfo toPi = getPositionInfo(to);

        ConvexInfo fromConvex = fromPi.convex;
        ConvexInfo toConvex = toPi.convex;

        if (fromConvex == toConvex && fromConvex != null) {
            final Path path = new Path();
            path.vertexs.add(from);
            path.vertexs.add(to);
            return path;
        }

        final Node nearbyFromNode = fromPi.node;//this.allNodes.get(fromPi.nearbyNodeId);
        final Node nearbyToNode = toPi.node;//this.allNodes.get(toPi.nearbyNodeId);

        int fromNodeId = nearbyFromNode.id;
        int toNodeId = nearbyToNode.id;
        if (this.nodePaths[fromNodeId][toNodeId] < 0) {
            return null;
        }

        if (fromConvex == null) {
            //System.out.printf("from:%s can't reach. relocate:%s \n", from, fromPi.nearbyReachablePosition);
            from = fromPi.nearbyReachablePosition;
            fromConvex = nearbyFromNode.convexs.get(0);
        }

        if (toConvex == null) {
            //System.out.printf("to:%s can't reach. relocate:%s \n", to, toPi.nearbyReachablePosition);
            to = toPi.nearbyReachablePosition;
            toConvex = nearbyToNode.convexs.get(0);
        }

        // 如果起始结点与第二个结点在同一个多边形内
        // 可以跳过第二个结点,而不影响寻路的正确性
        // 不可删除这段代码,否则可能走出折返的路线
        int nextFromNodeId = this.nodePaths[fromNodeId][toNodeId];
        if (fromConvex.nodeIds.contains(nextFromNodeId)) {
            fromNodeId = nextFromNodeId;
        }

        // 如果结束结点与倒数第二个结点在同个多边形内
        // 可以跳过倒数第二个结点,而不影响寻路正确性
        // 不可删除这段代码,否则可能走出折返的路线
        int prevToNodeId = this.nodePaths[toNodeId][fromNodeId];
        if (toConvex.nodeIds.contains(prevToNodeId)) {
            toNodeId = prevToNodeId;
        }

        final ArrayList<Node> ns = new ArrayList<Node>();
        for (int curNodeId = fromNodeId;
             curNodeId != toNodeId;
             curNodeId = nodePaths[curNodeId][toNodeId]) {
            ns.add(allNodes.get(curNodeId));
        }
        ns.add(allNodes.get(toNodeId));

        return convertNodePathToVertexPath(from, to, ns);
    }

    boolean nearby(Vector3 v1, Vector3 v2) {
        return v1.sub(v2).getSquare() < NEARBY_DISTANCE * NEARBY_DISTANCE;
    }

    boolean nearby(Vector3 dis) {
        return dis.getSquare() < NEARBY_DISTANCE;
    }

    // k is the weight of b,  c = b + k(a-b)
    double calcK(Vector3 a, Vector3 b, Vector3 c) {
        //return  (b.x * (a.z - b.z) - b.z * (a.x - b.x)) / (c.x * (a.z - b.z) - c.z * (a.x - b.x));
        return 1 - (-b.x * c.z + b.z * c.x) / (c.z * (a.x - b.x) - c.x * (a.z - b.z));
    }

    Vector3 choosePosition(Vector3 viewPosition, Vector3 legA, Vector3 legB, Vector3 nodeLeg) {
        Vector3 legC = legA.sub(legB);
        double len = legC.getXZMagnitude();
        if (len <= PAD_DISTANCE) {
            //double s1 = legA.getXZMagnitude() + legD.sub(legA).getXZMagnitude();
            //double s2 = legB.getXZMagnitude() + legD.sub(legB).getXZMagnitude();
            return viewPosition.plus(legB).plus(legC.multi(0.5));
        } else {
            Vector3 leg1 = legA.plus(legC.scale(-PAD_DISTANCE));
            double s1 = leg1.getXZMagnitude() + nodeLeg.sub(leg1).getXZMagnitude();
            Vector3 leg2 = legB.plus(legC.scale(PAD_DISTANCE));
            double s2 = leg2.getXZMagnitude() + nodeLeg.sub(leg2).getXZMagnitude();
            return viewPosition.plus(s1 < s2 ? leg1 : leg2);
        }
    }

    double bound(double a, double b, double v) {
        if (v <= a) {
            return a;
        }
        if (v >= b) {
            return b;
        }
        return v;
    }

    Vector3 choosePosition2(Vector3 preViewPosition, Vector3 viewPosition, Vector3 nodePosition,
                            Vector3 posA, Vector3 posB,
                            Vector3 legLeft, Vector3 legRight, boolean turnLeft) {
        Vector3 legA = posA.sub(viewPosition);
        Vector3 legB = posB.sub(viewPosition);

        Vector3 legAB = posA.sub(posB);
        final double kLeft = calcK(legA, legB, legLeft);
        final double kRight = calcK(legA, legB, legRight);

        Vector3 pos1 = posA.plus(legAB.multi(-kLeft));
        Vector3 pos2 = posA.plus(legAB.multi(-kRight));
        Vector3 pos21 = pos2.sub(pos1);

        if (turnLeft) {
            final Vector3 leg1 = legA.plus(legAB.multi(-kLeft));
            //PAD_DISTANCE 离拐点距离，尽量不走顶点边，
            //leg1.getXZMagnitude() / 2 当前视点离拐点距离如果太近，就偏合适位置，不参考PAD_DISTANCE。
            //pos21.getXZMagnitude()/2 路窄时候不会走到对面顶点
            final double padDistance = Math
                .min(Math.min(PAD_DISTANCE, leg1.getXZMagnitude() / 2), pos21.getXZMagnitude() / 2);
            final Vector3 delta = pos21.scaleXZ(padDistance);
            //System.out.println("turn left  delta:" + delta + ", paddistance:" + padDistance);
            return pos1.plus(delta);
            //			return viewPosition.plus(leg1);
        } else {
            final Vector3 leg2 = legA.plus(legAB.multi(-kRight));
            final double padDistance = Math
                .min(Math.min(PAD_DISTANCE, leg2.getXZMagnitude() * 0.5), pos21.getXZMagnitude() / 2);
            final Vector3 delta = pos21.scaleXZ(-padDistance);
            //System.out.println("turn right delta:" + delta + ", paddistance:" + padDistance);
            //			return viewPosition.plus(leg2.plus());
            return pos2.plus(delta);
        }
    }

    // 根据路径点,作拉直处理,生成最优异路径
    // 非完美的3d空间路径拉直,而是简化的xz平面拉直
    public Path convertNodePathToVertexPath(final Vector3 from, final Vector3 to, final ArrayList<Node> pathNodes) {
        final Path path = new Path();
        final ArrayList<Vector3> vertexs = path.vertexs;
        vertexs.add(from);
        // 如果无法直线到达
        if (!lineCrossAllSegments(from, to, pathNodes)) {
            pathNodes.add(new Node(-1, to, new Edge(new Vertex(-1, to), new Vertex(-1, to))));
            //夹边视点
            Vector3 preViewPosition = null;
            Vector3 viewPosition = from;
            int minLeftIndex = 0;
            int minRightIndex = 0;
            Vector3 minLegLeft = null;//最左边
            Vector3 minLegRight = null;//最右边
            for (int i = 0, n = pathNodes.size(); i < n; i++) {
                final Node cur = pathNodes.get(i);
                Vector3 nodeLeg = cur.position.sub(viewPosition);
                Vector3 legLeft = cur.edge.v1.position.sub(viewPosition);
                Vector3 legRight = cur.edge.v2.position.sub(viewPosition);
                //				System.out.printf("minleft:%s minright:%s left:%s right:%s\n", minLegLeft, minLegRight, legLeft,
                //				legRight);
                //				// 合并太近的结点
                if (nearby(legLeft) || nearby(legRight)) {
                    continue;
                }

                // 夹边法,找到能直接到达的最远位置(边)
                if (MathUtil.rightOf(legLeft, legRight)) {
                    Vector3 temp = legLeft;
                    legLeft = legRight;
                    legRight = temp;
                }

                if (minLegLeft == null) {
                    minLegLeft = legLeft;
                    minLegRight = legRight;
                    minLeftIndex = i;
                    minRightIndex = i;
                    continue;
                }
                //如果碰到了拐点，看是往左拐还是右怪
                if (!MathUtil.rightOf(nodeLeg, minLegLeft)) {
                    i = minLeftIndex;
                    final Node lastNode = pathNodes.get(i);
                    final Vector3 temp = viewPosition;
                    viewPosition = choosePosition2(preViewPosition, viewPosition, cur.position,
                        lastNode.edge.v1.position, lastNode.edge.v2.position,
                        minLegLeft, minLegRight, true);
                    preViewPosition = temp;
                    vertexs.add(viewPosition);
                    //					System.out.printf("==minleft:%s minright:%s left:%s right:%s nodeleg:%s\n", minLegLeft,
                    //					minLegRight, legLeft, legRight, nodeLeg);
                    minLegLeft = minLegRight = null;
                } else if (!MathUtil.leftOf(nodeLeg, minLegRight)) {
                    i = minRightIndex;
                    final Node lastNode = pathNodes.get(i);
                    final Vector3 temp = viewPosition;
                    viewPosition = choosePosition2(preViewPosition, viewPosition, cur.position,
                        lastNode.edge.v1.position, lastNode.edge.v2.position,
                        minLegLeft, minLegRight, false);
                    preViewPosition = temp;
                    vertexs.add(viewPosition);
                    //					System.out.printf("==minleft:%s minright:%s left:%s right:%s nodeleg:%s\n", minLegLeft,
                    //					minLegRight, legLeft, legRight, nodeLeg);
                    minLegLeft = minLegRight = null;
                } else {
                    if (MathUtil.rightOf(legLeft, minLegLeft)) {
                        minLegLeft = legLeft;
                        minLeftIndex = i;
                    }
                    if (MathUtil.leftOf(legRight, minLegRight)) {
                        minLegRight = legRight;
                        minRightIndex = i;
                    }
                    //					System.out.printf("minleft:%s minright:%s left:%s right:%s\n", minLegLeft, minLegRight, legLeft,
                    //					legRight);
                }

            }
            if (!nearby(to, viewPosition)) {
                vertexs.add(to);
            }
        } else {
            vertexs.add(to);
        }
        return path;
    }

    private static class Connection {

        public final int cost;
        public final Node to;

        Connection(Node t, int c) {
            to = t;
            cost = c;
        }

        @Override
        public String toString() {
            return "{" + to.id + ":" + cost + "}";
        }
    }

    private static class PositionInfo {

        public ConvexInfo convex;
        public Node node;
        public Vector3 nearbyReachablePosition;

        PositionInfo() {
            this.convex = null;
            this.node = null;
            this.nearbyReachablePosition = null;
        }
    }

    public static final class Path {

        public final ArrayList<Vector3> vertexs = new ArrayList<Vector3>();

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append("path{");
            for (Vector3 v : vertexs) {
                s.append(v).append(",");
            }
            s.append("}");
            return s.toString();
        }
    }

    /**
     * 每一个寻路点信息
     */
    private final class Node {

        public final int id;
        public final Vector3 position;
        public final HashMap<Integer, Connection> connections = new HashMap<Integer, Connection>();
        public final ArrayList<ConvexInfo> convexs = new ArrayList<ConvexInfo>();
        public final Edge edge;

        Node(int i, Vector3 p, Edge e) {
            id = i;
            position = p;
            edge = e;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append("Node[" + id + "]{");
            s.append("position:").append(position).append(",");
            s.append("connections:{[" + connections.size() + "]");
            for (Connection c : connections.values()) {
                s.append(c).append(",");
            }
            s.append("},");
            s.append("edge:").append(edge);
            s.append("}");
            return s.toString();
        }
    }

    /**
     * 边信息
     */
    private class EdgeInfo {

        public final Edge edge;
        public final ArrayList<Node> nodes = new ArrayList<Node>();
        public final ArrayList<ConvexInfo> convexs = new ArrayList<ConvexInfo>();

        EdgeInfo(Edge e) {
            edge = e;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append("EdgeInfo{");
            s.append("edge:").append(edge).append(",");
            s.append("nodes:{");
            for (Node n : nodes) {
                s.append(n.id).append(",");
            }
            s.append("}");
            s.append("}");
            return s.toString();
        }
    }

    /**
     * 多边形信息
     */
    private class ConvexInfo {

        public final int id;
        public final Convex convex;
        public final ArrayList<EdgeInfo> edges = new ArrayList<EdgeInfo>();
        public final ArrayList<Node> nodes = new ArrayList<Node>();
        public final HashSet<Integer> nodeIds = new HashSet<Integer>();
        public final Rect box;

        ConvexInfo(int id, Convex c) {
            this.id = id;
            convex = c;
            box = Rect.makeBox(c.vertexs);
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append("ConvexInfo{\n");
            s.append("id:" + id + ",\n");
            s.append("convex:{").append(convex).append("},\n");
            s.append("edges:{");
            for (EdgeInfo e : edges) {
                s.append(e).append("\n");
            }
            s.append("},\n");
            s.append("nodes:{");
            for (Node n : nodes) {
                s.append(n).append("\n");
            }
            s.append("}");
            s.append("}");
            return s.toString();
        }
    }

    /**
     * 快速查找任意点落在哪个多边形区域内
     */
    private class SearchManager {

        private final Rect mapRange;
        private final SearchNode root;

        public SearchManager(Rect range, List<ConvexInfo> convexInfos) {
            this.mapRange = range;
            root = new SearchNode(mapRange);
            for (var convexInfo : convexInfos) {
                root.addConvex(convexInfo);
            }
        }

        public ConvexInfo search(Vector3 point) {
            return root.findConvexContainPoint(point);
        }

    }

    private class SearchNode {
        private final Rect searchRange;
        private SearchNode[] children;
        private List<ConvexInfo> curNodeConvex; //当前节点内的多边形

        public SearchNode(Rect searchRange) {
            this.searchRange = searchRange;
            curNodeConvex = new ArrayList<>();
        }

        /**
         * 一个节点内多边形太多，划分成更小的区域
         */
        private void split() {
            children = new SearchNode[4];
            double centerX = searchRange.xRange() / 2 + searchRange.minx;
            double centerZ = searchRange.zRange() / 2 + searchRange.minz;
            Rect leftDown = new Rect(searchRange.minx, centerX, searchRange.minz, centerZ);
            children[0] = new SearchNode(leftDown);
            Rect rightDown = new Rect(centerX, searchRange.maxx, searchRange.minz, centerZ);
            children[1] = new SearchNode(rightDown);
            Rect leftUp = new Rect(searchRange.minx, centerX, centerZ, searchRange.maxz);
            children[2] = new SearchNode(leftUp);
            Rect rightUp = new Rect(centerX, searchRange.maxx, centerZ, searchRange.maxz);
            children[3] = new SearchNode(rightUp);
            this.curNodeConvex.removeIf(this::addToChildren);
        }

        public void addConvex(ConvexInfo convexInfo) {
            //优先往子节点放,成功了就返回
            if (children != null) {
                boolean result = addToChildren(convexInfo);
                if (result) {
                    return;
                }
            }
            //子节点放不进去
            this.curNodeConvex.add(convexInfo);
            //如果当前节点数目超过了上限，而且还能继续拆分，构造子节点
            if (this.curNodeConvex.size() >= maxConvexInOneNode && children == null && (searchRange.xRange() > minSearchRangeX && searchRange.zRange() > minSearchRangeZ)) {
                System.out.println("Rect = " + searchRange + " split!");
                split();
            }
        }

        private boolean addToChildren (ConvexInfo convexInfo) {
            for (var child : children) {
                if (child.contains(convexInfo)) {
                    child.addConvex(convexInfo);
                    return true;
                }
            }
            return false;
        }

        //判断一个多边形顶点是否都在当前搜索节点范围内
        private boolean contains(ConvexInfo convexInfo) {
            for (var vertex : convexInfo.convex.vertexs) {
                if (!searchRange.contains(vertex.position)) {
                    return false;
                }
            }
            return true;
        }

        //todo 处理不在任何多边形区域的情况
        public ConvexInfo findConvexContainPoint(Vector3 point) {
            //优先从当前节点中找
            for (var convexInfo : this.curNodeConvex) {
                if (convexInfo.convex.contains(point)) {
                    return convexInfo;
                }
            }
            //找不到，再去子节点中找，
            for (var child : children) {
                if (child.searchRange.contains(point)) {
                    return child.findConvexContainPoint(point);
                }
            }
            return null;
        }

    }

}
