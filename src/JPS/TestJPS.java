package JPS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

public class TestJPS {
    public static void main(String[] args) {
        int[][] array1 = {
                {0, 0, 1, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 1, 1, 0, 0, 0, 1, 1, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 1, 0, 0}
        };

        int[][] array2 = {
                { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
                { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                { 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1},
                { 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                { 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1},
                { 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1},
                { 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        int[][] array3 = {
                { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
                { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                { 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1},
                { 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1},
                { 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1},
                { 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        int[][] array4 = {
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
        };
        int[][] mazeArray;
        try {
            List<String> mazes = Files.readAllLines(new File("src/JPS/maze.map").toPath());
            int length = mazes.size();
            int width = mazes.get(0).length();
            mazeArray = new int[length][width];
            for(int i = 0; i < length; i++){
                String line = mazes.get(i);
                char[] chars = line.toCharArray();
                for(int j = 0; j < chars.length; j++){
                    mazeArray[i][j] = chars[j] == '.' ? 0 : 1;
                }
            }
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }

        JPSMapCompute preCompute = new JPSMapCompute(mazeArray);
//        preCompute.detecCollision(0,0,4,1);
//        int height = array.length;
//        int width =  array[0].length;
//        for(int r = 0; r < height; r++){
//            for(int c = 0; c < width; c++){
//                JPSNode node = preCompute.getJPSNode(r, c);
//                System.out.println("row: " + r + ";col: " + c);
//                System.out.println("JumpPoint: " + Integer.toBinaryString(node.jumpBitField));
//                System.out.println("JumpDistance: " + Arrays.toString(node.jumpDistance));
//            }
//        }
        JPSPathFind pathFindTool = new JPSPathFind(preCompute);
//        List<JPSPathFind.SimpleNode> result = pathFindTool.findPathByGrid(4, 0, 0, 7);   //array1
//        List<JPSPathFind.SimpleNode> result = pathFindTool.findPathByGrid(0, 5, 4, 10); //array2
//         List<JPSPathFind.SimpleNode> result = pathFindTool.findPathByGrid(1, 1, 5, 5); //array3
         List<JPSPathFind.SimpleNode> result = pathFindTool.findPathByGrid(1, 1, 1, 5); //array3
        if(result == null){
            System.out.println("can not reach");
        }else {
            System.out.println(result);
        }
        Random random = new Random();
        long startTime =  System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++){
            if(i % 10000 == 0) {
                System.out.println("----------------");
            }
            pathFindTool.findPathByGrid(random.nextInt(100), random.nextInt(100), random.nextInt(100), random.nextInt(100));
        }
        long endTime = System.currentTimeMillis();
        float costTime = (float) ((endTime - startTime) / 1000.0);
        System.out.println("cost time is " + costTime);
    }
}
