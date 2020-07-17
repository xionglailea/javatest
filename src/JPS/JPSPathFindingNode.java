package JPS;


public class JPSPathFindingNode {
    public int row;
    public int col;
    public int givenCost;
    public int finalCost;
    public char dirFromParent;
    public JPSPathFindingNode parent;
    NodeState state;

    public enum NodeState {
        NODESTATUS_NONE,
        NODESTATUS_OPEN,
        NODESTATUS_CLOSED
    }

    public JPSPathFindingNode() {
        this.state = NodeState.NODESTATUS_NONE;
    }

    @Override
    public int hashCode() {  //按目前的情况看 这个key基本是唯一的
        return (row << 16) + col;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof JPSPathFindingNode))
            return false;
        JPSPathFindingNode temp = (JPSPathFindingNode) obj;
        return hashCode() == temp.hashCode() && temp.row == row && temp.col == col;
    }

    @Override
    public String toString() {
        return "row=" + row + ";col=" + col;
    }

}
