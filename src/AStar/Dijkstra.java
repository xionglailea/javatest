package AStar;

import java.util.Arrays;

//���ĵ�Դ���·���㷨 ��֧��Ȩ��Ϊ����
public class Dijkstra {

    public static int MAX = 10000;
    public static int[][] graph = {
        {0, 10, 12, 7, MAX, MAX, MAX},
        {10, 0, MAX, 6, 3, MAX, 5},
        {12, MAX, 0, 16, MAX, 14, MAX},
        {7, 6, 16, 0, MAX, 9, 2},
        {MAX, 3, MAX, MAX, 0, MAX, 4},
        {MAX, MAX, 14, 9, MAX, 0, 8},
        {MAX, 5, MAX, 2, 4, 8, 0}
    };

    public static int[] calculate(int[][] graph, int start) {
        int length = graph.length;
        int[] minDis = new int[length];
        System.arraycopy(graph[start], 0, minDis, 0, length);
        int[] minArray = new int[length];
        minArray[start] = 1;
        for (int i = 1; i < length; i++) {//�ܵ�ѭ������
            int min = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < length; j++) {
                if (minArray[j] != 0) {
                    continue;
                }
                if (min > minDis[j]) {  //�ҳ�open�б��� ������С�ģ�����close��
                    min = minDis[j];
                    minIndex = j;
                }
            }
            minArray[minIndex] = 1;
            System.out.println("min index = " + minIndex);
            for (int j = 0; j < length; j++) {
                if (minArray[j] == 1) {
                    continue;
                }
                //�Ե�ǰ�ҳ�����С�ĵ���Ϊ��ת�㣬����open�б��е�ֵ
                var temp = minDis[minIndex] + graph[minIndex][j];
                if (minDis[j] > temp) {
                    minDis[j] = temp;
                }
            }
            System.out.println("update dis = " + Arrays.toString(minDis));
        }
        return minDis;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(calculate(graph, 2)));
    }

}
