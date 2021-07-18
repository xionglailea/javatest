package JPS;

public class JPSNode {

    final static char JPSDIR_DOWN = 0;
    final static char JPSDIR_DOWNRIGHT = 1;
    final static char JPSDIR_RIGHT = 2;
    final static char JPSDIR_UPRIGHT = 3;
    final static char JPSDIR_UP = 4;
    final static char JPSDIR_UPLEFT = 5;
    final static char JPSDIR_LEFT = 6;
    final static char JPSDIR_DOWNLEFT = 7;
    final static char JPSDIR_ALL = 8;

    final static char ROW = 0;
    final static char COL = 1;
    final static int INFINITY = Integer.MAX_VALUE;

    public static int[][] JPSOFFSETRC = {  //八方向的坐标偏移，这里定好了坐标系的方向
            {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}
    };

    public short jumpBitField;
    public short blockBitField;
    public short[] jumpDistance;

    public JPSNode() {
        jumpDistance = new short[8];
    }
}
