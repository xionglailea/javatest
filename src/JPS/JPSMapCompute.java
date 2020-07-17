package JPS;
import trans.RefObject;

import static JPS.JPSNode.*;


public class JPSMapCompute {

    //存储地图的一些信息
    private int[][] mapData; //0表示可走；1表示是阻挡
    private int height;
    private int width;
    private JPSNode[][] jpsNodes;

    public JPSMapCompute(int[][] mapData) {
        this.mapData = mapData;
        this.height = mapData.length;
        this.width = mapData[0].length;
        jpsNodes = new JPSNode[height][width];
        for(int r = 0; r < height; r++){
            for(int c = 0; c < width; c++){
                jpsNodes[r][c] = new JPSNode();
            }
        }
        calculateJumpPoint();
        calculateJumpDistanceMap();
    }

    private void calculateJumpPoint(){
        for(int r = 0; r < height; r++){
            for(int c = 0; c < width; c++){
                if(mapData[r][c] == 0){
                    JPSNode jpsNode = jpsNodes[r][c];
                    if(isJumpPoint(r, c, JPSOFFSETRC[JPSDIR_DOWN][ROW], JPSOFFSETRC[JPSDIR_DOWN][COL])){
                        jpsNode.jumpBitField |= ( 1 << JPSDIR_DOWN );
                    }
                    if(isJumpPoint(r, c, JPSOFFSETRC[JPSDIR_UP][ROW], JPSOFFSETRC[JPSDIR_UP][COL])){
                        jpsNode.jumpBitField |= ( 1 << JPSDIR_UP );
                    }
                    if(isJumpPoint(r, c, JPSOFFSETRC[JPSDIR_LEFT][ROW], JPSOFFSETRC[JPSDIR_LEFT][COL])){
                        jpsNode.jumpBitField |= ( 1 << JPSDIR_LEFT );
                    }
                    if(isJumpPoint(r, c, JPSOFFSETRC[JPSDIR_RIGHT][ROW], JPSOFFSETRC[JPSDIR_RIGHT][COL])){
                        jpsNode.jumpBitField |= ( 1 << JPSDIR_RIGHT );
                    }
                }
            }
        }
    }

    private boolean isJumpPoint(int r, int c, int rowdir, int coldir){
        return isEmpty(r - rowdir, c - coldir) &&  //前置节点一定不能为阻挡
                ((isEmpty(r + coldir, c + rowdir) &&
                isBlocked(r + coldir - rowdir, c + rowdir - coldir)) ||
                (isEmpty(r - coldir, c - rowdir) &&
                 isBlocked(r - coldir - rowdir, c - rowdir - coldir)));
    }

    boolean isBlocked(int r, int c){
//        System.out.println("detect: " + "r=" + r + " c=" + c);
        if(r >= 0 && r < height && c>= 0 && c < width){
            return mapData[r][c] != 0;
        }else {
            return true;
        }
    }

    boolean isEmpty(int r, int c){
        return r >=0 && r < height && c >= 0 && c < width && mapData[r][c] == 0;
    }

    private void calculateJumpDistanceMap(){
        RefObject<Integer> countMovingDir = new RefObject<>(-1);
        RefObject<Boolean> jumpPointLastSeen = new RefObject<>(false);
        for(int r = 0; r < height; r++){
            {
                countMovingDir.value = -1;
                jumpPointLastSeen.value = false;
                for(int c = 0; c < width; c++){
                    calCardinal(r, c, countMovingDir, jumpPointLastSeen, JPSDIR_LEFT);
                }
                countMovingDir.value = -1;
                jumpPointLastSeen.value = false;
                for(int c = width - 1; c >= 0; c--){
                    calCardinal(r, c, countMovingDir, jumpPointLastSeen, JPSDIR_RIGHT);
                }
            }
        }

        for(int c = 0; c < width; c++){
            countMovingDir.value = -1;
            jumpPointLastSeen.value = false;
            for(int r = 0; r < height; r++){
                calCardinal(r, c, countMovingDir, jumpPointLastSeen, JPSDIR_UP);
            }
            countMovingDir.value = -1;
            jumpPointLastSeen.value = false;
            for(int r = height - 1; r >= 0; r--){
                calCardinal(r, c, countMovingDir, jumpPointLastSeen, JPSDIR_DOWN);
            }
        }

        for(int r = 0; r < height; r++){
            for(int c = 0; c < width; c++){
                calDiagonal(r, c, JPSDIR_UPLEFT);
                calDiagonal(r, c, JPSDIR_UPRIGHT);
            }
        }

        for(int r =  height - 1; r >= 0; r--){
            for(int c= 0; c < width; c++){
                calDiagonal(r, c, JPSDIR_DOWNLEFT);
                calDiagonal(r, c, JPSDIR_DOWNRIGHT);
            }
        }

    }

    //横向
    private void calCardinal(int r, int c, RefObject<Integer> countMovingDir, RefObject<Boolean> jumpPointLastSeen, int dir){
        JPSNode jpsNode = jpsNodes[r][c];
        if(isBlocked(r,c)){
            countMovingDir.value = -1;
            jumpPointLastSeen.value = false;
            jpsNodes[r][c].blockBitField |= (1 << dir);
            jpsNode.jumpDistance[dir] = 0;
            return;
        }
        countMovingDir.value++;
        if(jumpPointLastSeen.value){
            jpsNode.jumpDistance[dir] = countMovingDir.value.shortValue();
        }else {
            jpsNode.jumpDistance[dir] = (short) -countMovingDir.value;
            if(countMovingDir.value == 0){
                jpsNode.blockBitField |= (1 << dir);
            }
        }
        if((jpsNode.jumpBitField & (1 << dir)) > 0){
            countMovingDir.value = 0;
            jumpPointLastSeen.value = true;
        }
    }

    //斜方向,每个只跟它的周围格子跳距有关
    private void calDiagonal(int r, int c, int dir){
        JPSNode jpsNode = jpsNodes[r][c];
        if(isBlocked(r,c)){
            jpsNode.blockBitField |= (1 << dir);
        }else {
            int[]offSet = JPSOFFSETRC[dir];
            int preR = r + offSet[ROW];
            int preC = c + offSet[COL];
            if(r == (((offSet[ROW] + 1) >> 1) * (height - 1)) ||
                c == (((offSet[COL] + 1) >> 1) * (width - 1)) ||
                    isBlocked(preR, c) || isBlocked(r, preC) || isBlocked(preR, preC)){
                jpsNode.jumpDistance[dir] = 0;
                jpsNode.blockBitField |= (1 << dir);
            }else {
                JPSNode preNode = jpsNodes[preR][preC];
                //取了直线方向的点，这里大于零说明会碰到强制邻点，所以这时对角方向是一步步搜索的
                if(preNode.jumpDistance[dir -1] > 0 || preNode.jumpDistance[(dir + 1) % JPSDIR_ALL] > 0){
                    jpsNode.jumpDistance[dir] = 1;
                }else {
                    short preJumpDist = preNode.jumpDistance[dir];
                    if(preJumpDist > 0){
                        jpsNode.jumpDistance[dir] = (short) (1 + preJumpDist);
                    }else {
                        jpsNode.jumpDistance[dir] = (short) (-1 + preJumpDist);
                        if(jpsNode.jumpDistance[dir] == 0){
                            jpsNode.blockBitField |= (1 << dir);
                        }
                    }
                }
            }
        }
    }


    private boolean load(String cacheFile){
        return false;
    }

    private void save(String cacheFile){

    }

    JPSNode getJPSNode(int r, int c){
        return jpsNodes[r][c];
    }

    int getHeight(){
        return height;
    }

    int getWidth(){
        return width;
    }

    //检测经过的所有格子是否会有阻挡，如果有，那么在阻挡前的一个格子停下来，
    //画直线的时候，用每个格子的中心点, 这里会检测该直线经过的所有的格子(严格查找方式)
    public void detecCollision(int startR, int startC, int endR, int endC){
        if(startR == endR && startC == endC){
            System.out.println("Start and End is the same grid");
            return;
        }

        int diffC = endC - startC;
        int diffR = endR - startR;
        if(diffR == 0){  //如果是在同一行
            detectARow(startR, startC, endC);
        }else {
            float startRX= (float) (startR + 0.5);
            float startCY = (float)(startC + 0.5);
//            float endRX = (float)(endR + 0.5);
//            float endCY = (float)(endC + 0.5);

            float slope = diffC / (float)diffR;
            float b = startCY - startRX * slope;
            int firstR = diffR > 0 ? startR + 1 : startR;
            float firstC = slope * firstR + b;
            int firstCFloor = (int) Math.floor(firstC);
            if(!detectARow(startR, startC, firstCFloor)){ //检测起始点的一小半
                return;
            }
            int step = diffR > 0 ? 1 : -1;
            int testNum = Math.abs(diffR) - 1;
            int temp = firstR;
            int preC = firstCFloor;
            while (testNum > 0){
                temp = temp + step;
                int tempC = (int) Math.floor(slope * temp + b);
                if(!detectARow(step > 0 ? temp - 1 : temp, preC, tempC)){
                    return;
                }
                preC = tempC;
                testNum--;
            }
            if(!detectARow(endR, preC, endC)){ //检测终点的一小半
                return;
            }
            System.out.println("OK");
        }
    }


    //检测在某一行的start col和 end col之间是否存在阻挡，如果有，直接返回
    private boolean detectARow(int row, int startC, int endC){
        System.out.println("start detect row: " + row);
        if(isBlocked(row, startC)){
            System.out.println("blocked at: Row=" + row + " Col=" + startC);
            return false;
        }
        int diffC = endC - startC;
        int step = diffC > 0 ? 1 : -1;
        int limit = Math.abs(diffC);
        int nC = startC;
        while (limit > 0){
            nC = nC + step;
            if(isBlocked(row, nC)){
                System.out.println("blocked at: Row=" + row + " Col=" + nC);
                return false;
            }
            limit--;
        }
        return true;
    }

}
