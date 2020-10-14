package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayProblem {

    /**
     *
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        ArrayProblem a1 = new ArrayProblem();
        int[] A = {0, 1, 2, 0, 1, 4, 2, 5};
        int[] B = {2, 0, 2};
        // double i=a1.findMedianSortedArrays(A, B);
        int[][] c = new int[2][3];
        int a = 1;
        System.out.print(Integer.SIZE);
        ListNode d = new ListNode(1);
        ListNode e = new ListNode(2);
        ListNode f = new ListNode(3);
        ListNode g = new ListNode(4);
        d.next = e;
        e.next = f;
        f.next = g;
        a1.reorderList(d);
    }

    public int removeDuplicates(int[] A) {
        if (A.length == 0) {
            return 0;
        }
        int i = 0;
        for (int j = 1; j < A.length; j++) {
            if (A[i] != A[j]) {
                A[++i] = A[j];
            }
        }
        return i + 1;
    }

    public int removeDuplicatestwice(int[] A) {
        if (A.length <= 2) {
            return A.length;
        }
        int i = 1;
        for (int j = 2; j < A.length; j++) {
            if (A[i - 1] != A[j]) {
                A[++i] = A[j]; // 每次隔一个数找一个不一样的
            }
        }
        return i + 1;
    }

    public int search(int[] A, int target) {
        if (A.length == 1) {
            return A[0] == target ? 0 : -1;
        }
        int i = 0;
        while (i < A.length - 1) {
            if (A[i] > A[i + 1]) {
                break;
            }
            i++;
        }
        int a = BinarySearch(0, i, target, A);
        int b = BinarySearch(i + 1, A.length - 1, target, A);
        if (a < 0 && b < 0) {
            return -1;
        } else {
            return a > b ? a : b;
        }

    }

    public int BinarySearch(int low, int high, int key, int[] A) { // 二分查找法
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (key < A[mid]) {
                high = mid - 1;
            } else if (key > A[mid]) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public double findMedianSortedArrays(int A[], int B[]) { // 找中值
        int m = A.length;
        int n = B.length;
        if ((m + n) % 2 != 0) {
            return findMedian(A, B, (m + n) / 2 + 1);
        } else {
            return (findMedian(A, B, (m + n) / 2) + findMedian(A, B,
                (m + n) / 2 + 1)) / 2.0;
        }
    }

    public double findMedian(int A[], int[] B, int k) { // 递归查找第k大的数
        // 先假设A的长度是比B的长度要小或者等的
        int m = A.length;
        int n = B.length;
        if (m == 0) {
            return B[k - 1]; // 递归终止条件 当某数组长度为0，直接在另一个里面找
        }
        if (m > n) {
            return findMedian(B, A, k);// 如果A的长度长，直接交换顺序即可
        }
        if (k == 1) {
            return Math.min(A[0], B[0]); // 只有一个，直接找
        }

        int ia = Math.min(m, k / 2);
        int ib = k - ia; // 思路还是二分查找
        if (A[ia - 1] < B[ib - 1]) {
            return findMedian(Arrays.copyOfRange(A, ia, m), B, k - ia);// 每次递归减小查找范围
        }
        if (A[ia - 1] > B[ib - 1]) {
            return findMedian(A, Arrays.copyOfRange(B, ib, n - ib), k - ib);
        } else {
            return A[ia - 1];
        }
    }

    public int longestConsecutive(int[] num) {
        HashMap<Integer, Boolean> usedNum = new HashMap<>();
        for (int i : num) {
            usedNum.put(i, false);
        }
        int length = 1;

        for (int i : num) {
            if (!usedNum.get(i)) { // 利用哈希表，向俩端延伸找最长的,
                int localLength = 0;
                int temp = i - 1;
                while (usedNum.containsKey(i) && !usedNum.get(i)) {// 注意while中出现为null的情况会运行错误
                    ++localLength;
                    usedNum.put(i, true);
                    i++;
                }
                while (usedNum.containsKey(temp) && !usedNum.get(temp)) {
                    ++localLength;
                    usedNum.put(temp, true);
                    temp--;
                }
                length = Math.max(length, localLength);
            }
        }
        return length;
    }

    public int[] twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> usedNumbers = new HashMap<>();
        int j = 0;
        int[] result = new int[2];
        for (int i : numbers) {
            usedNumbers.put(i, j); // 注意哈希表的键不能重复
            j++;
        }
        for (int i = 0; i < numbers.length; i++) {
            int anotherNum = target - numbers[i];
            if (usedNumbers.containsKey(anotherNum)
                && usedNumbers.get(anotherNum) > i) {
                result[0] = i + 1;
                result[1] = usedNumbers.get(anotherNum) + 1;
                return result;
            }
        }
        return null;
    }

    public List<List<Integer>> threeSum(int[] num) {
        Arrays.sort(num);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (num.length < 3) {
            return result;
        }
        for (int i = 0; i < num.length - 2; i++) {
            int second = i + 1;
            int third = num.length - 1;
            while (second < third) {
                if (num[i] + num[second] + num[third] > 0) {
                    third--;
                } else if (num[i] + num[second] + num[third] < 0) {
                    second++;
                } else {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(num[i]);
                    temp.add(num[second]);
                    temp.add(num[third]);
                    result.add(temp);
                    third--;
                    second++;
                }
            }
        }
        for (int i = result.size() - 1; i >= 1; i--) { // 都添加进去后在移除重复的,比添加的时候检查耗费时间短
            for (int j = i - 1; j >= 0; j--) {
                if (result.get(i).get(0) == result.get(j).get(0)
                    && result.get(i).get(1) == result.get(j).get(1)
                    && result.get(i).get(2) == result.get(j).get(2)) {
                    result.remove(j);
                    i--; // 每移除一个，外层循环就减小一个；
                }
            }
        }
        return result;
    }

    public int threeSumClosest(int[] num, int target) {
        Arrays.sort(num);
        int sum = num[0] + num[1] + num[2];
        for (int i = 0; i < num.length - 2; i++) {
            int second = i + 1;
            int third = num.length - 1;
            while (second < third) {
                int temp = num[i] + num[second] + num[third];
                if (temp < target) {
                    if (Math.abs(temp - target) <= Math.abs(sum - target)) { // 每次找到接近的就更新
                        sum = temp;
                    }
                    second++;
                } else if (temp > target) { // 注意三重判断的时候，if ，else
                    // if，else一定不能写错，不然就会出现逻辑错误
                    if (Math.abs(temp - target) <= Math.abs(sum - target)) {
                        sum = temp;
                    }
                    third--;
                } else {
                    return temp;
                }
            }
        }
        return sum;
    }

    public List<List<Integer>> fourSum(int[] num, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (num.length < 4) {
            return result;
        }
        Arrays.sort(num);
        HashMap<Integer, List<Integer>> twosum = new HashMap<>();
        for (int i = 0; i < num.length - 1; i++) {
            for (int j = i + 1; j < num.length; j++) {
                if (twosum.containsKey(num[i] + num[j]) == true) {
                    List<Integer> twoSumList = twosum.get(num[i] + num[j]);
                    twoSumList.add(i);
                    twoSumList.add(j);
                    twosum.put(num[i] + num[j], twoSumList); // 和相同的所有可能都存储起来
                } else {
                    List<Integer> index = new ArrayList<Integer>();
                    index.add(i);
                    index.add(j);
                    twosum.put(num[i] + num[j], index);
                }// 哈希表的键不能重复，值能重复
            }
        }

        for (int i = 0; i < num.length - 1; i++) {
            for (int j = i + 1; j < num.length; j++) {
                int x = target - num[i] - num[j];
                if (twosum.containsKey(x) == true) {
                    List<Integer> temp = twosum.get(x);
                    int n = 0;
                    while (2 * n < temp.size()) {
                        List<Integer> indexList = Arrays.asList(
                            temp.get(2 * n), temp.get(2 * n + 1));
                        if (indexList.contains(i) || indexList.contains(j)) {
                            n++;
                            continue;
                        } else {
                            int[] tempNum = {num[i], num[j],
                                num[temp.get(2 * n)],
                                num[temp.get(2 * n + 1)]};
                            Arrays.sort(tempNum);
                            List<Integer> printout = Arrays.asList(tempNum[0],
                                tempNum[1], tempNum[2], tempNum[3]);
                            if (result.contains(printout)) { // 边添加边检查有没有重复
                                n++;
                                continue;
                            } else {
                                result.add(printout);
                                n++;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public int removeElement(int[] A, int elem) {
        int newLength = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] != elem) {
                A[newLength++] = A[i];
            }
        }
        return newLength;
    }

    public void nextPermutation(int[] num) {     //全排列生成算法，字典序法
        int a = -1;                                //从排列的右端开始，找出第一个比右边数字小的数字pi
        for (int i = num.length - 1; i >= 1; i--) {   //在pi的右边的数字中，找出所有比pi大的数中最小的数字pk
            if (num[i - 1] < num[i]) {            //对换pi，pk再将pi+1......pk-1pkpk+1pn倒转得到排列p
                a = i - 1;
                break;
            }
        }
        if (a == -1) {
            reverseNum(num, 0);
        } else {
            int b = num[a + 1];
            int c = 0;
            for (int i = a + 1; i < num.length; i++) {
                if (num[i] > num[a]) {
                    if (num[i] <= b) {
                        b = num[i];
                        c = i;
                    }
                }
            }
            int d = num[a];
            num[a] = num[c];
            num[c] = d;
            reverseNum(num, a + 1);
        }
    }

    public int[] reverseNum(int[] num, int start) {  //将数组中从start开始的元素翻转
        int a = start + (num.length - 1 - start) / 2;
        int b = start;
        while (start <= a) {
            int c = num[start];
            num[start] = num[b + num.length - 1 - start];
            num[b + num.length - 1 - start] = c;
            start++;
        }
        return num;
    }

    public boolean isValidSudoku(char[][] board) {
        char[] checkedChar = new char[9];
        for (int i = 0; i < 9; i++) {   //检查行
            for (int j = 0; j < 9; j++) {
                checkedChar[j] = board[i][j];
            }
            if (!isSingleUsed(checkedChar)) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {  //检查列
            for (int j = 0; j < 9; j++) {
                checkedChar[j] = board[j][i];
            }
            if (!isSingleUsed(checkedChar)) {
                return false;
            }
        }
        for (int i = 0; i < 3; i++) { //检查小方框
            for (int j = 0; j < 3; j++) {
                int m = 0;
                for (int r = 3 * i; r < 3 * i + 3; r++) {
                    for (int q = 3 * j; q < 3 * j + 3; q++) {
                        checkedChar[m++] = board[r][q];
                    }
                }
                if (!isSingleUsed(checkedChar)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSingleUsed(char[] board) {  //检查是否有重复数字出现
        Set<Character> used = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            if (board[i] != '.') {
                if (used.contains(board[i])) {
                    return false;
                } else {
                    used.add(board[i])
                    ;
                }
            }
        }
        return true;
    }

    public int trap(int[] A) {
        int trapWater = 0;
        int maxIndex = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] > A[maxIndex]) {
                maxIndex = i;      //找出最大的数
            }
        }
        int left_max = 0, right_max = 0;  //在左右分别找俩个基准，计算增加的值
        for (int i = 0; i < maxIndex; i++) {
            if (A[i] > left_max) {
                left_max = A[i];
            } else {
                trapWater += left_max - A[i];
            }
        }
        for (int i = A.length - 1; i > maxIndex; i--) {
            if (A[i] > right_max) {
                right_max = A[i];
            } else {
                trapWater += right_max - A[i];
            }
        }
        return trapWater;
    }

    public void rotate(int[][] matrix) {   //旋转矩阵，将大的分成四个小的
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n - n / 2; j++) {
                int temp = matrix[i][j];     //发生旋转的4个点要对应准确，按一定的规律旋转四次后又回到初始的位置
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = temp;
            }
        }
    }

    public void rotateAnother(int[][] matrix) {  //一层一层往里面走，总共进行n/2次
        int n = matrix.length;
        int m = n;
        int i = 0;
        while (m > 0) {
            for (int j = i; j < n - i - 1; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = temp;
            }
            i++;
            m = m - 2;
        }
    }

    public int[] plusOne(int[] digits) {     //一个数的各个位上的数存放在数组中，加任意一个数
        int c = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i] = digits[i] + c;
            c = digits[i] / 10;
            digits[i] = digits[i] % 10;
        }
        if (c == 1) {
            int[] result = new int[digits.length + 1];
            result[0] = 1;
            for (int i = 0; i < digits.length; i++) {
                result[i + 1] = digits[i];
            }
            return result;
        } else {
            return digits;
        }
    }

    public int climbStairs(int n) {    //使用递归出现了超时
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        } else {
            return climbStairs(n - 1) + climbStairs(n - 2);
        }
    }

    public int climbStairs1(int n) {   //此处用迭代的方法,一级一级往前迭代
        int pre = 0;
        int current = 1;
        for (int i = 1; i <= n; i++) {
            int temp = current;
            current = current + pre;
            pre = temp;
        }
        return current;
    }

    public List<Integer> grayCode(int n) {   //输入一个n，表示n比特,列出所有的格雷码，即从0-2的n次方-1
        List<Integer> result = new ArrayList<>();
        result.add(0);
        for (int i = 1; i <= n; i++) {     //一层一层往后推，找到对应的规律
            int temp = (int) Math.pow(2, i - 1);
            for (int j = (int) Math.pow(2, i - 1); j < Math.pow(2, i); j++) { //每层增加的个数为2的（i-1）次方
                result.add(j, result.get(temp - 1) + (int) Math.pow(2, i - 1));//值的增加量是按一个轴对称的
                temp--;
            }
        }
        return result;
    }

    public void setZeroes(int[][] matrix) {
        int[] row = new int[matrix.length];    //用来记录行列是否有0出现
        int[] col = new int[matrix[0].length];
        for (int i = 0; i < row.length; i++) {
            for (int j = 0; j < col.length; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = col[j] = 1;    //找出所有的为0的元素，并记录其行列
                }
            }
        }
        for (int i = 0; i < row.length; i++) {
            if (row[i] == 1) {            //将行或列出现0的全部标记为0
                for (int m = 0; m < col.length; m++) {
                    matrix[i][m] = 0;
                }
            }
        }
        for (int j = 0; j < col.length; j++) {
            if (col[j] == 1) {
                for (int n = 0; n < row.length; n++) {
                    matrix[n][j] = 0;
                }
            }
        }
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int curr = 0, total = 0;
        int j = -1;
        for (int i = 0; i < gas.length; i++) {
            curr += gas[i] - cost[i];
            total += gas[i] - cost[i];
            if (curr < 0) {
                curr = 0;
                j = i;
            }
        }
        return total >= 0 ? j + 1 : -1;
    }

    public int candy(int[] ratings) {   //发糖果，只要比旁边任意一个高就要比他多发糖果，所以从前往后，从后往前分别遍历一遍
        int incr = 1;
        int n = ratings.length;
        int[] increase = new int[n];
        int total = 0;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                increase[i] = Math.max(incr++, increase[i]);
            } else {
                incr = 1;
            }
        }
        incr = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                increase[i] = Math.max(incr++, increase[i]);
            } else {
                incr = 1;
            }
        }
        for (int i : increase) {
            total += i;
        }
        return total + n;
    }

    public int singleNumber(int[] A) {//一个数组中除了一个数其余都是成对出现的，异或所有，结果就是单独的数
        int result = 0;
        for (int i : A) {
            result ^= i;
        }
        return result;
    }

    public int singleNumber2(int[] A) {//位运算，计算在某位上1出现的次数，如果是3的倍数，表示single num在该位上为0，否则为1
        int[] count = new int[Integer.SIZE];
        int result = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < Integer.SIZE; j++) {
                count[j] += (A[i] >> j) & 1;   //右移位
            }
        }
        for (int j = 0; j < Integer.SIZE; j++) {
            count[j] %= 3;
            result += count[j] << j;   //左移位，计算出该数组表示的数
        }
        return result;

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {  //将两个数存储在单链表中，成对取出相加
        ListNode head = new ListNode(-1);
        ListNode dup = head;
        int add = 0;
        while (l1 != null || l2 != null) {
            int a = l1 == null ? 0 : l1.val;
            int b = l2 == null ? 0 : l2.val;
            int value = (a + b + add) % 10;
            add = (a + b + add) / 10;
            head.next = new ListNode(value);
            head = head.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (add > 0) {
            head.next = new ListNode(add);
        }
        return dup.next;
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode start = new ListNode(-1);    //自己建一个链表头，所有的顺序都变化肯定都在它后面进行,
        start.next = head;                    //可以很方便的找到表头，方便输出
        ListNode dup = start;
        ListNode cur = null;
        for (int i = 0; i < m - 1; i++) {
            start = start.next;
        }
        ListNode pre = start.next;
        if (pre != null) {               //判断是否到链表尾部
            cur = pre.next;
        }
        for (int i = m; i < n; i++) {
            pre.next = cur.next;
            cur.next = start.next;
            start.next = cur;
            cur = pre.next;
        }
        return dup.next;
    }

    public ListNode partition(ListNode head, int x) {
        ListNode small = new ListNode(-1);
        ListNode dup1 = small;
        ListNode big = new ListNode(-1);
        ListNode dup2 = big;
        while (head != null) {
            if (head.val < x) {
                small.next = head;
                small = small.next;
            } else {
                big.next = head;
                big = big.next;
            }
            head = head.next;
        }
        small.next = dup2.next;
        big.next = null;    //注意加上这句防止成环
        return dup1.next;
    }

    public ListNode deleteDuplicates(ListNode head) {
        ListNode dup = head;
        if (head != null && head.next != null) {       //注意判断意外情况
            ListNode cur = head.next;
            while (cur != null) {
                if (head.val == cur.val) {
                    head.next = cur.next;
                    cur = cur.next;
                } else {
                    head = cur;
                    cur = cur.next;
                }
            }
        }
        return dup;
    }

    public ListNode deleteDuplicatesII(ListNode head) {//非递归的方式
        if (head == null || head.next == null) {
            return head;
        } else {
            ListNode start = new ListNode(-1);
            ListNode dup = start;
            ListNode cur = head.next;
            while (cur != null) {   //每次循环都判断一个新的数head
                if (head.val == cur.val) {
                    while (head.val == cur.val) { //将某个相同的值全找出来，全部跳过，从下一个值开始判断
                        cur = cur.next;
                        if (cur == null) {    //如果达到边界条件，说明链表最后有一些值相等，则在返回的链表补上空
                            start.next = null;
                            return dup.next;
                        }
                    }
                    head = cur;
                    cur = cur.next;
                    if (cur == null) {
                        start.next = head;
                    }
                } else {
                    start.next = head;    //没有和head相等的数的话，标记，往下继续找
                    start = start.next;
                    head = head.next;
                    cur = cur.next;
                }
            }
            return dup.next;
        }
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        ListNode start = head;
        for (int i = 1; i <= k - 1; i++) {
            if (head.next == null) {
                return start;
            }
            head = head.next;
        }
        head = start;
        ListNode pre = head.next;
        ListNode tail = head;     //定义尾结点
        for (int i = 1; i < k; i++) {
            tail.next = pre.next;
            pre.next = head;
            head = pre;  //不断更新头结点
            pre = tail.next;
        }
        tail.next = reverseKGroup(pre, k);   //采用递归的方法，
        return head;
    }

    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return head;
        }
        RandomListNode start = head;
        while (head != null) {
            RandomListNode newNode = new RandomListNode(head.label);
            newNode.next = head.next;
            head.next = newNode;
            head = newNode.next;
        }
        head = start;
        RandomListNode newstart = start.next;
        while (start != null) {
            if (start.random != null)                      //注意要判断原结点的random是否可用
            {
                start.next.random = start.random.next;
            } else {
                start.next.random = null;           //注意双枝条件判断时，else不要遗忘。。。
            }
            start = start.next.next;
        }
        start = newstart;
        while (head != null) {
            head.next = newstart.next;
            if (newstart.next != null) {
                newstart.next = newstart.next.next;
            }
            head = head.next;
            newstart = newstart.next;
        }
        return start;

    }

    public boolean hasCycle(ListNode head) {  //判断是否有圈，最简单是用一个hashset判断是否有重复元素
        if (head == null || head.next == null) {
            return false;//空间复杂度简单的话，设置俩个指针，一个走得快，一个走得慢
        }
        ListNode fast = head.next.next;               //如果有圈，那么俩个指针终会相遇
        ListNode slow = head;
        while (fast != slow) {
            if (fast == null || fast.next == null) {
                return false;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        return true;
    }

    public ListNode detectCycle(ListNode head) {  //如果有圈，返回链表上圈的起始点
        if (head == null || head.next == null) {
            return null;//空间复杂度简单的话，设置俩个指针，一个走得快，一个走得慢
        }
        ListNode fast = head.next.next;               //如果有圈，那么俩个指针终会相遇
        ListNode slow = head;
        while (fast != slow) {
            if (fast == null || fast.next == null) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                slow = slow.next.next;
                while (head != slow) {
                    head = head.next;
                    slow = slow.next;
                }
                return slow;
            }
        }
        return slow;
    }

    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        ListNode start = head;    //从中间截断（还是设置一个快指针，一个慢指针），找到中间点后翻转后面的一部分，然后合并两链表
        ListNode slow = head, fast = head;
        ListNode cut = slow;
        while (fast != null && fast.next != null) {
            cut = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        cut.next = null;

        ListNode pre = slow.next;
        ListNode tail = slow;
        while (pre != null) {
            tail.next = pre.next;
            pre.next = slow;
            slow = pre;
            pre = tail.next;
        }
        ListNode temp = slow;
        head = slow;
        ListNode dup = start;
        while (start.next != null) {
            temp = head.next;         //注意一个结点给另一个结点赋值后，若其中一个改变，则另外一个跟着改变
            head.next = start.next;
            start.next = head;
            start = head.next;
            head = temp;
        }
        start.next = head;
        head = dup;
    }
}