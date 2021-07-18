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
                A[++i] = A[j]; // ÿ�θ�һ������һ����һ����
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

    public int BinarySearch(int low, int high, int key, int[] A) { // ���ֲ��ҷ�
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

    public double findMedianSortedArrays(int A[], int B[]) { // ����ֵ
        int m = A.length;
        int n = B.length;
        if ((m + n) % 2 != 0) {
            return findMedian(A, B, (m + n) / 2 + 1);
        } else {
            return (findMedian(A, B, (m + n) / 2) + findMedian(A, B,
                (m + n) / 2 + 1)) / 2.0;
        }
    }

    public double findMedian(int A[], int[] B, int k) { // �ݹ���ҵ�k�����
        // �ȼ���A�ĳ����Ǳ�B�ĳ���ҪС���ߵȵ�
        int m = A.length;
        int n = B.length;
        if (m == 0) {
            return B[k - 1]; // �ݹ���ֹ���� ��ĳ���鳤��Ϊ0��ֱ������һ��������
        }
        if (m > n) {
            return findMedian(B, A, k);// ���A�ĳ��ȳ���ֱ�ӽ���˳�򼴿�
        }
        if (k == 1) {
            return Math.min(A[0], B[0]); // ֻ��һ����ֱ����
        }

        int ia = Math.min(m, k / 2);
        int ib = k - ia; // ˼·���Ƕ��ֲ���
        if (A[ia - 1] < B[ib - 1]) {
            return findMedian(Arrays.copyOfRange(A, ia, m), B, k - ia);// ÿ�εݹ��С���ҷ�Χ
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
            if (!usedNum.get(i)) { // ���ù�ϣ�����������������,
                int localLength = 0;
                int temp = i - 1;
                while (usedNum.containsKey(i) && !usedNum.get(i)) {// ע��while�г���Ϊnull����������д���
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
            usedNumbers.put(i, j); // ע���ϣ��ļ������ظ�
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
        for (int i = result.size() - 1; i >= 1; i--) { // ����ӽ�ȥ�����Ƴ��ظ���,����ӵ�ʱ����ķ�ʱ���
            for (int j = i - 1; j >= 0; j--) {
                if (result.get(i).get(0) == result.get(j).get(0)
                    && result.get(i).get(1) == result.get(j).get(1)
                    && result.get(i).get(2) == result.get(j).get(2)) {
                    result.remove(j);
                    i--; // ÿ�Ƴ�һ�������ѭ���ͼ�Сһ����
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
                    if (Math.abs(temp - target) <= Math.abs(sum - target)) { // ÿ���ҵ��ӽ��ľ͸���
                        sum = temp;
                    }
                    second++;
                } else if (temp > target) { // ע�������жϵ�ʱ��if ��else
                    // if��elseһ������д����Ȼ�ͻ�����߼�����
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
                    twosum.put(num[i] + num[j], twoSumList); // ����ͬ�����п��ܶ��洢����
                } else {
                    List<Integer> index = new ArrayList<Integer>();
                    index.add(i);
                    index.add(j);
                    twosum.put(num[i] + num[j], index);
                }// ��ϣ��ļ������ظ���ֵ���ظ�
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
                            if (result.contains(printout)) { // ����ӱ߼����û���ظ�
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

    public void nextPermutation(int[] num) {     //ȫ���������㷨���ֵ���
        int a = -1;                                //�����е��Ҷ˿�ʼ���ҳ���һ�����ұ�����С������pi
        for (int i = num.length - 1; i >= 1; i--) {   //��pi���ұߵ������У��ҳ����б�pi���������С������pk
            if (num[i - 1] < num[i]) {            //�Ի�pi��pk�ٽ�pi+1......pk-1pkpk+1pn��ת�õ�����p
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

    public int[] reverseNum(int[] num, int start) {  //�������д�start��ʼ��Ԫ�ط�ת
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
        for (int i = 0; i < 9; i++) {   //�����
            for (int j = 0; j < 9; j++) {
                checkedChar[j] = board[i][j];
            }
            if (!isSingleUsed(checkedChar)) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {  //�����
            for (int j = 0; j < 9; j++) {
                checkedChar[j] = board[j][i];
            }
            if (!isSingleUsed(checkedChar)) {
                return false;
            }
        }
        for (int i = 0; i < 3; i++) { //���С����
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

    public boolean isSingleUsed(char[] board) {  //����Ƿ����ظ����ֳ���
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
                maxIndex = i;      //�ҳ�������
            }
        }
        int left_max = 0, right_max = 0;  //�����ҷֱ���������׼���������ӵ�ֵ
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

    public void rotate(int[][] matrix) {   //��ת���󣬽���ķֳ��ĸ�С��
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n - n / 2; j++) {
                int temp = matrix[i][j];     //������ת��4����Ҫ��Ӧ׼ȷ����һ���Ĺ�����ת�Ĵκ��ֻص���ʼ��λ��
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                matrix[j][n - 1 - i] = temp;
            }
        }
    }

    public void rotateAnother(int[][] matrix) {  //һ��һ���������ߣ��ܹ�����n/2��
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

    public int[] plusOne(int[] digits) {     //һ�����ĸ���λ�ϵ�������������У�������һ����
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

    public int climbStairs(int n) {    //ʹ�õݹ�����˳�ʱ
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        } else {
            return climbStairs(n - 1) + climbStairs(n - 2);
        }
    }

    public int climbStairs1(int n) {   //�˴��õ����ķ���,һ��һ����ǰ����
        int pre = 0;
        int current = 1;
        for (int i = 1; i <= n; i++) {
            int temp = current;
            current = current + pre;
            pre = temp;
        }
        return current;
    }

    public List<Integer> grayCode(int n) {   //����һ��n����ʾn����,�г����еĸ����룬����0-2��n�η�-1
        List<Integer> result = new ArrayList<>();
        result.add(0);
        for (int i = 1; i <= n; i++) {     //һ��һ�������ƣ��ҵ���Ӧ�Ĺ���
            int temp = (int) Math.pow(2, i - 1);
            for (int j = (int) Math.pow(2, i - 1); j < Math.pow(2, i); j++) { //ÿ�����ӵĸ���Ϊ2�ģ�i-1���η�
                result.add(j, result.get(temp - 1) + (int) Math.pow(2, i - 1));//ֵ���������ǰ�һ����ԳƵ�
                temp--;
            }
        }
        return result;
    }

    public void setZeroes(int[][] matrix) {
        int[] row = new int[matrix.length];    //������¼�����Ƿ���0����
        int[] col = new int[matrix[0].length];
        for (int i = 0; i < row.length; i++) {
            for (int j = 0; j < col.length; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = col[j] = 1;    //�ҳ����е�Ϊ0��Ԫ�أ�����¼������
                }
            }
        }
        for (int i = 0; i < row.length; i++) {
            if (row[i] == 1) {            //���л��г���0��ȫ�����Ϊ0
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

    public int candy(int[] ratings) {   //���ǹ���ֻҪ���Ա�����һ���߾�Ҫ�����෢�ǹ������Դ�ǰ���󣬴Ӻ���ǰ�ֱ����һ��
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

    public int singleNumber(int[] A) {//һ�������г���һ�������඼�ǳɶԳ��ֵģ�������У�������ǵ�������
        int result = 0;
        for (int i : A) {
            result ^= i;
        }
        return result;
    }

    public int singleNumber2(int[] A) {//λ���㣬������ĳλ��1���ֵĴ����������3�ı�������ʾsingle num�ڸ�λ��Ϊ0������Ϊ1
        int[] count = new int[Integer.SIZE];
        int result = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < Integer.SIZE; j++) {
                count[j] += (A[i] >> j) & 1;   //����λ
            }
        }
        for (int j = 0; j < Integer.SIZE; j++) {
            count[j] %= 3;
            result += count[j] << j;   //����λ��������������ʾ����
        }
        return result;

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {  //���������洢�ڵ������У��ɶ�ȡ�����
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
        ListNode start = new ListNode(-1);    //�Լ���һ������ͷ�����е�˳�򶼱仯�϶��������������,
        start.next = head;                    //���Ժܷ�����ҵ���ͷ���������
        ListNode dup = start;
        ListNode cur = null;
        for (int i = 0; i < m - 1; i++) {
            start = start.next;
        }
        ListNode pre = start.next;
        if (pre != null) {               //�ж��Ƿ�����β��
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
        big.next = null;    //ע���������ֹ�ɻ�
        return dup1.next;
    }

    public ListNode deleteDuplicates(ListNode head) {
        ListNode dup = head;
        if (head != null && head.next != null) {       //ע���ж��������
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

    public ListNode deleteDuplicatesII(ListNode head) {//�ǵݹ�ķ�ʽ
        if (head == null || head.next == null) {
            return head;
        } else {
            ListNode start = new ListNode(-1);
            ListNode dup = start;
            ListNode cur = head.next;
            while (cur != null) {   //ÿ��ѭ�����ж�һ���µ���head
                if (head.val == cur.val) {
                    while (head.val == cur.val) { //��ĳ����ͬ��ֵȫ�ҳ�����ȫ������������һ��ֵ��ʼ�ж�
                        cur = cur.next;
                        if (cur == null) {    //����ﵽ�߽�������˵�����������һЩֵ��ȣ����ڷ��ص������Ͽ�
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
                    start.next = head;    //û�к�head��ȵ����Ļ�����ǣ����¼�����
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
        ListNode tail = head;     //����β���
        for (int i = 1; i < k; i++) {
            tail.next = pre.next;
            pre.next = head;
            head = pre;  //���ϸ���ͷ���
            pre = tail.next;
        }
        tail.next = reverseKGroup(pre, k);   //���õݹ�ķ�����
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
            if (start.random != null)                      //ע��Ҫ�ж�ԭ����random�Ƿ����
            {
                start.next.random = start.random.next;
            } else {
                start.next.random = null;           //ע��˫֦�����ж�ʱ��else��Ҫ����������
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

    public boolean hasCycle(ListNode head) {  //�ж��Ƿ���Ȧ���������һ��hashset�ж��Ƿ����ظ�Ԫ��
        if (head == null || head.next == null) {
            return false;//�ռ临�Ӷȼ򵥵Ļ�����������ָ�룬һ���ߵÿ죬һ���ߵ���
        }
        ListNode fast = head.next.next;               //�����Ȧ����ô����ָ���ջ�����
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

    public ListNode detectCycle(ListNode head) {  //�����Ȧ������������Ȧ����ʼ��
        if (head == null || head.next == null) {
            return null;//�ռ临�Ӷȼ򵥵Ļ�����������ָ�룬һ���ߵÿ죬һ���ߵ���
        }
        ListNode fast = head.next.next;               //�����Ȧ����ô����ָ���ջ�����
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
        ListNode start = head;    //���м�ضϣ���������һ����ָ�룬һ����ָ�룩���ҵ��м���ת�����һ���֣�Ȼ��ϲ�������
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
            temp = head.next;         //ע��һ��������һ����㸳ֵ��������һ���ı䣬������һ�����Ÿı�
            head.next = start.next;
            start.next = head;
            start = head.next;
            head = temp;
        }
        start.next = head;
        head = dup;
    }
}