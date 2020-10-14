package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StrProblem {

    /**
     *
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        StrProblem s1 = new StrProblem();
        //		s1.reverseWords(" 1");
        String string = " 1 I love   you";
        String[] splitStrings = string.split(" ");
        System.out.println(splitStrings.length);
        for (String s : splitStrings) {
			if (!s.equals("")) {
				System.out.println(s);
			}
        }
        //		int[] A={0,0};
        //		s1.fractionToDecimal(2, 3);
        //		s1.largestNumber(A);
        //		PriorityQueue<ListNode> pQueue=new PriorityQueue<>();
        //		ListNode l1=new ListNode(2);
        //		ListNode l2=new ListNode(1);
        //		pQueue.offer(l1);
        //		pQueue.offer(l2);
        //		while(!pQueue.isEmpty())
        //			System.out.println(pQueue.remove().val);
        //		s1.reverseBits(0);
        //		String a="9";
        //		String bString="5";
        //		System.out.println(a.compareTo(bString));
        //		s1.divide(-2147483648, 1);
        //		String test="aA";
        //		String a="";
        //        String reg="[^-+0-9]";
        //        Pattern p=Pattern.compile(reg);
        //        Matcher m=p.matcher("+-33ab44");
        //		boolean c = m.find();
        //		String dString=m.replaceAll("").trim();
        //		s1.findRepeatedDnaSequences1("GAGAGAGAGAGA");
        //		A[6]=1;
        //		s1.rotate(A, 3);
        //		int a=4;
        //		String s="1.22.33";
        //		String[] resultStrings=s.split("\\.");
        //		for(String ss:resultStrings)
        //			System.out.println(ss);
        //		String[] s2={"3","3","2"};
        //		Comparator<String> comparator=s1.new strcom();
        //		Arrays.sort(s2,comparator);
        //		for(String s:s2)
        //			System.out.println(s);

        //		System.out.println(bString.substring(1).equals(""));
    }

    class strcom implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            // TODO Auto-generated method stub
            String s1 = o1 + o2;
            String s2 = o2 + o1;
            return s1.compareTo(s2);   //按升序排列的最后
        }

    }

    public boolean isPalindrome(String s) {   //检测是否是回文结构
        String reg = "[^0-9a-zA-Z]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(s);
        String test = m.replaceAll("").trim();
        String last = test.toLowerCase();
        char[] a1 = last.toCharArray();
        for (int i = 0; i < a1.length / 2; i++) {
			if (a1[i] != a1[a1.length - i - 1]) {
				return false;
			}
        }
        return true;
    }

    //暴力法
    public int strStr(String haystack, String needle) {
		if (needle.length() > haystack.length()) {
			return -1;
		}
		if (needle == "") {
			return 0;   //注意判断是注意null和“”的区别
		}
        int m = haystack.length();
        int n = needle.length();
        for (int i = 0; i <= m - n; i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {
                int j = 1;
                while (j < n && haystack.charAt(i + j) == needle.charAt(j)) {
                    j++;
                }
				if (j == n) {
					return i;
				}
            }
        }
        return -1;
    }

    public int atoi(String str) { //把所有数字都提取出来构成一个整数
        String reg = "[^-+0-9]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        String test = m.replaceAll("").trim();
        int len = test.length();
		if (len == 0) {
			return 0;
		}
        char sig = test.charAt(0);
        if (len == 1) {
			if (sig == '-' || sig == '+') {
				return 0;
			} else {
				return Integer.parseInt(test);
			}

        }
        for (int i = 1; i < len; i++) {
			if (test.charAt(i) == '-' || test.charAt(i) == '+') {
				return 0;
			}
        }
        String result;
        if (!Character.isDigit(sig)) {
            result = test.substring(1);
        } else {
            result = test;
        }
        int INT_MAX = Integer.MAX_VALUE;
        int INT_MIN = Integer.MIN_VALUE;
        int r = 0;
        try {
            r = Integer.parseInt(result);
        } catch (Exception e) {
			if (sig == '-') {
				return INT_MIN;
			} else {
				return INT_MAX;
			}
        }
        return sig == '-' ? r * (-1) : r;
    }

    public int atoi1(String str) {  //输入的字符串规律为先空格，再正负号，再数字，后面接着可能有字母，返回的数字是连续的，内嵌在字符串中
        int sig = 1;
        int n = 0;   //统计+-号出现的次数
        int i = 0;
        int len = str.length();
        int result = 0;
        int INT_MAX = Integer.MAX_VALUE;
        int INT_MIN = Integer.MIN_VALUE;
		while (i < len && str.charAt(i) == ' ') {
			i++;
		}
        if (i < len) {
            if (str.charAt(i) == '-') {
                i++;
                sig = -1;
                n++;
            }
            if (str.charAt(i) == '+') {
                i++;
                n++;
            }
        }
		if (n > 1) {
			return 0;  //如果出现多个+-号，也要返回0
		}
        for (; i < len; i++) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9') {
				break;
			}
			if (result > INT_MAX / 10 || (result == INT_MAX / 10 && str.charAt(i) - '0' > INT_MAX % 10))//判斷是否过界
			{
				return sig == -1 ? INT_MIN : INT_MAX;
			}
            result = 10 * result + str.charAt(i) - '0';
        }
        return result * sig;
    }

    public String addBinary(String a, String b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}
        int m = a.length();
        int n = b.length();
        int x = m >= n ? m : n;
        String result = "";
        int carry = 0;
        int num1 = 0, num2 = 0;
        int add;
        for (int i = 0; i < x; i++) {
            num1 = m <= 0 ? 0 : a.charAt(m - 1) - '0';
            num2 = n <= 0 ? 0 : b.charAt(n - 1) - '0';
            add = (num1 + num2 + carry) % 2;
            carry = (num1 + num2 + carry) / 2;
            result = add + result;    //合并字符串，自动将int转成了string
            m--;
            n--;
        }
		if (carry == 1) {
			return 1 + result;
		} else {
			return result;
		}
    }

    public String longestPalindrome(String s) { //暴力枚举法
        char[] origi = s.toCharArray();
        int length = origi.length;
        int start = 0;
        int max = 1;
        int left, right;
        for (int i = 0; i < length - 1; i++) {
            if (origi[i] == origi[i + 1]) {
                left = i;
                right = i + 1;
                while (left >= 0 && right < length && origi[left] == origi[right]) {
                    left--;
                    right++;
                }
                if (right - left - 1 > max) {
                    max = right - left - 1;
                    start = left + 1;
                }
            }
            left = i;
            right = i;
            while (left >= 0 && right < length && origi[left] == origi[right]) {
                left--;
                right++;
            }
            if (right - left - 1 > max) {
                max = right - left - 1;
                start = left + 1;
            }
        }
        return s.substring(start, start + max);
    }

    public boolean isMatch(String s, String p) {
		if (p.length() == 0) {
			return s.equals(p);
		}
        if (p.length() == 1 || (p.length() > 1 && p.charAt(1) != '*')) {
			if (s.length() > 0 && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.')) {
				return isMatch(s.substring(1), p.substring(1));
			} else {
				return false;
			}
        } else {
            int i = 0;
            while (s.substring(i).length() > 0
                && (s.charAt(i) == p.charAt(0) || p.charAt(0) == '.')) {  //*能匹配0个前面的字符
				if (isMatch(s.substring(i), p.substring(2))) {
					return true;
				}
                i++;
            }
            return isMatch(s.substring(i), p.substring(2));
        }
    }

    public int divide(int dividend, int divisor) {
		if (divisor == 0) {
			return Integer.MAX_VALUE;
		}
        double sig = 1;
		if (dividend > 0 && divisor < 0 || dividend < 0 && divisor > 0) {
			sig = -1;
		}
        double result = 0;
        double a = Math.abs((double) dividend), b = Math.abs((double) divisor); //全部转成double类型进行计算
        double tempDivisor;
        while (a >= b) {
            tempDivisor = b;
            for (int i = 0; a >= tempDivisor; tempDivisor *= 2, i++) {//每次将除数加倍
                a -= tempDivisor;    //用被除数减去除数
                result = result + (1 << i);
				if (result > (double) Integer.MAX_VALUE + 1.0) {
					return Integer.MAX_VALUE;
				}
            }
        }
		if (result * sig > Integer.MAX_VALUE)  //先只算正的，到最后在判断正负号
		{
			return Integer.MAX_VALUE;
		}
        return (int) (result * sig);
    }

    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
			if ((n & 1) == 1) {
				result = result | (0x80000000 >>> i);
			}
            n = n >>> 1;
        }
        return result;
    }

    public int hammingWeight(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
			if ((n & (1 << i)) != 0) {
				result++;
			}
        }
        return result;
    }

    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        int n = 0;
        int i = 0;
        int len = nums.length;
		if (len == 0) {
			return;
		}
        int temp = nums[0];
        int start = 0;
        while (n < len) {
            if (i + k <= len - 1) {
                int cur = nums[i + k];
                nums[i + k] = temp;
                temp = cur;
                i = i + k;
            } else {
                int cur = nums[i + k - len];
                nums[i + k - len] = temp;
                temp = cur;
                i = i + k - len;
                if (i == start) { //如果循环一遍又跑到数组前面已经循环的地方，那么往下挪一个，继续循环，但是要注意改变temp的值；
                    i = i + 1;
                    start = i;
                    temp = nums[i];
                }
            }
            n++;
        }
    }

    public int trailingZeroes(int n) {
		if (n < 5) {
			return 0;
		}
        return n / 5 + trailingZeroes(n / 5);
    }

    public int titleToNumber(String s) {
        int n = s.length();
        int result = 0;
        for (int i = 0; i < n; i++) {
            result = result * 26 + s.charAt(i) - 'A' + 1;
        }
        return result;
    }

    public int majorityElement(int[] num) {
        int n = num.length;
		if (n == 1) {
			return num[0];
		}
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
			if (map.containsKey(num[i])) {
				int k = map.get(num[i]);
				map.put(num[i], k + 1);
				if (k + 1 > n / 2) {
					return num[i];
				}
			} else {
				map.put(num[i], 1);
			}
        }
        return 0;
    }

    public String convertToTitle(int n) {
        String result = "";
        while (n > 0) {
            if (n % 26 == 0) {   //如果能被26整除，那么要单独拿出来处理
                result = "Z" + result;
                n = n / 26 - 1;    //并且这里要减1，比如zz，是26*26+26，第二步只要前面的26
            } else {
                char c = (char) (n % 26 - 1 + 'A'); //如果不能被26整除，那么直接处理
                result = Character.toString(c) + result;
                n = n / 26;
            }
        }
        return result;
    }

    public int compareVersion(String version1, String version2) {
        String[] ver1 = version1.split("\\.");   //先将俩个字符串分割
        String[] ver2 = version2.split("\\.");
        int l1 = ver1.length;
        int l2 = ver2.length;
        for (int i = 0; i < Math.max(l1, l2); i++) {
            int s1 = i < l1 ? Integer.parseInt(ver1[i]) : 0;
            int s2 = i < l2 ? Integer.parseInt(ver2[i]) : 0;
			if (s1 > s2) {
				return 1;
			}
			if (s1 < s2) {
				return -1;
			}
        }
        return 0;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		if (headA == null | headB == null) {
			return null;
		}
        ListNode dupA = headA;
        ListNode dupB = headB;
        int n = 1;
        while (headA != null && headB != null) {
            headA = headA.next;
            headB = headB.next;
            n++;
        }
        int A = headA == null ? 0 : 1;
        ListNode temp = headA == null ? headB : headA;
        int m = 0;
        while (temp != null) {
            temp = temp.next;
            m++;                //得到长度之差
        }
        ListNode longNode = dupA, shortNode = dupB;
        if (A == 0) {
            longNode = dupB;
            shortNode = dupA;
        }
        while (m > 0) {
            longNode = longNode.next;
            m--;
        }
        while (longNode != null) {
			if (longNode == shortNode) {
				return longNode;
			} else {
				longNode = longNode.next;
				shortNode = shortNode.next;
			}
        }
        return null;
    }

    public List<String> findRepeatedDnaSequences(String s) {   //内存超了
        int n = s.length();
        List<String> result = new ArrayList<>();

		if (n < 10) {
			return result;
		}
        StringBuilder temp = new StringBuilder(s.substring(0, 9));
        Set<StringBuilder> set = new HashSet<>();
        set.add(temp);
        for (int i = 1; i <= n - 10; i++) {
            temp.delete(0, 1);
            temp.append(s.charAt(i));
			if (set.contains(temp)) {
				result.add(temp.toString());
			}
            set.add(temp);
        }
        return result;
    }

    public List<String> findRepeatedDnaSequences1(String s) {
        int n = s.length();
        List<String> result = new ArrayList<>();
		if (n < 10) {
			return result;
		}
        int[] code = new int[26];
        code['C' - 'A'] = 1;
        code['G' - 'A'] = 2;
        code['T' - 'A'] = 3;
        Set<Integer> set = new HashSet<>();
        Set<Integer> doubletime = new HashSet<>();
        for (int i = 0; i <= n - 10; i++) {
            int count = 0;
            for (int j = i; j < i + 10; j++) {
                count |= code[s.charAt(j) - 'A'];
                count <<= 2;
            }
			if (!set.add(count) && doubletime.add(count)) {
				result.add(s.substring(i, i + 10));
			}
        }
        return result;
    }

    public String largestNumber(int[] num) {
        Comparator<String> strComparator = new Comparator<String>() { //重写比较器
            @Override
            public int compare(String o1, String o2) {
                // TODO Auto-generated method stub
                String s1 = o1 + o2;
                String s2 = o2 + o1;
                return s1.compareTo(s2);
            }
        };
        int n = num.length;
        String[] temp = new String[n];
        int i = 0;
		for (int j : num) {
			temp[i++] = j + "";
		}
        Arrays.sort(temp, strComparator);  //排序时候按照自己设计的比较器规则来排序，最后顺序是升序排列
        String result = "";
		if (temp[n - 1].equals("0")) {
			return "0";
		}
        for (String s : temp) {
            result = s + result;
        }
        return result;
    }

    public String fractionToDecimal(int numerator, int denominator) {
		if (numerator == 0) {
			return "0";
		}
        StringBuilder result = new StringBuilder();
		if ((numerator < 0 && denominator > 0) || (numerator > 0 && denominator < 0)) {
			result.append("-");
		}
        long num1 = Math.abs((long) numerator);
        long num2 = Math.abs((long) denominator);
        long fac = num1 / num2;
        result.append(fac);
        result.append(".");
        num1 = num1 % num2;
        HashMap<Long, Integer> map = new HashMap<>();
        int index = result.length();
        map.put(num1, index);
        while (num1 != 0) {
            num1 *= 10;
            fac = num1 / num2;
            num1 = num1 % num2;
            result.append(fac);
            index++;
            if (map.containsKey(num1)) {
                result.insert(map.get(num1), "(");
                result.append(")");
                break;
            }
            map.put(num1, index);
        }
        return result.toString();
    }

    public ListNode mergeKLists(List<ListNode> lists) {   //和之前使用递归不同，这里使用了优先级队列，在插入时就排好了顺序
		if (lists == null || lists.size() == 0) {
			return null;
		}
        Comparator<ListNode> nodeCompare = new Comparator<ListNode>() { //还是自己实现的比较器

            @Override
            public int compare(ListNode o1, ListNode o2) {
                // TODO Auto-generated method stub
				if (o1.val > o2.val) {
					return 1;
				} else if (o1.val == o2.val) {
					return 0;
				} else {
					return -1;
				}
            }

        };
        PriorityQueue<ListNode> pQueue = new PriorityQueue<>(lists.size(), nodeCompare);
        for (ListNode l : lists) {
			if (l != null) {
				pQueue.offer(l);
			}
        }
        ListNode tempListNode;
        ListNode result = new ListNode(-1);
        ListNode dup = result;
        while (!pQueue.isEmpty()) {
            tempListNode = pQueue.remove();
            result.next = tempListNode;
            result = result.next;
			if (tempListNode.next != null) {
				pQueue.offer(tempListNode.next);
			}
        }
        return dup.next;
    }

    public int findMin(int[] num) {
        int n = num.length;
        int low = 0, high = n - 1;
        int mid = high / 2;
        while (low < high) {
            if (num[low] < num[mid] && num[mid] > num[high]) {
                low = mid + 1;
            } else if (num[low] > num[mid] && num[mid] < num[high]) {
                high = mid;
            } else {
                return Math.min(num[low], num[high]);
            }
            mid = (low + high) / 2;
        }
        return num[low];
    }

    public int maxProduct(int[] A) {  // 超时的方法
        int n = A.length;
        int[][] conProduct = new int[n][n];
        int max = 0;
        for (int i = 0; i < n; i++) {
            conProduct[i][i] = A[i];
			if (A[i] > max) {
				max = A[i];
			}
        }
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				conProduct[i][j] = conProduct[i][j - 1] * A[j];
				if (conProduct[i][j] > max) {
					max = conProduct[i][j];
				}
			}
		}
        return max;
    }

    public int maxProduct1(int[] A) {
        int n = A.length;
		if (n == 0) {
			return 0;
		}
        int max = A[0]; //正的最大
        int min = A[0]; //负的最大
        int result = max;
        for (int i = 1; i < n; i++) {
            if (A[i] > 0) {
                max = Math.max(max * A[i], A[i]);   //分A[i]大于0和小于0的情况讨论
                min = Math.min(min * A[i], A[i]);
            } else {
                int temp = max;
                max = Math.max(min * A[i], A[i]);
                min = Math.min(temp * A[i], A[i]);
            }
			if (max > result) {
				result = max;
			}
        }
        return result;
    }

    public String reverseWords(String s) {
        String[] split = s.split(" ");
        int n = split.length;
        StringBuilder result = new StringBuilder();
        for (int i = n - 1; i >= 0; i--) {
            if (!split[i].equals(" ") && !split[i].equals("")) {
                result.append(split[i]);
                result.append(" ");
            }
        }

        int m = result.length();
		if (m == 0) {
			return "";
		}
        return result.toString().substring(0, m - 1);
    }

    //    public List<List<String>> partition(String s) {
    //        List<List<String>> result=new ArrayList<>();
    //
    //    }
    //    public List<List<String>> getPartition(List<List<String>> result,List<String> temp,String s){
    //    	int n=s.length();
    //    	for(int i=1;i<=n;i++){
    //
    //    	}
    //    }
    public boolean isPal(String s) {
        int l = 0;
        int h = s.length() - 1;
        while (l <= h) {
			if (s.charAt(l) != s.charAt(h)) {
				return false;
			}
            l++;
            h--;
        }
        return true;
    }
}
