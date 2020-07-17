package trans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


public class LeetCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		numberToWords(12345);
        int[] a = {1,4};
        System.out.print(findPoisonedDuration(a, 2));
	}
	public enum Sider{
		SIDER1,SIDER2;
	}
	Sider a = Sider.SIDER1;
	public int kthSmallest(TreeNode root, int k) {
		int result = 0;
		int index = 0;
		Stack<TreeNode> sta = new Stack<>();
		while (root != null || !sta.isEmpty()) {
			while (root != null) {
				sta.push(root);
				root = root.left;
			}
			if (!sta.isEmpty()) {
				root = sta.pop();
				if (++index == k) {
					return root.val;
				}
				root = root.right;
			}
		}
		return result;
	}

	public boolean wordPattern(String pattern, String str) { // 判断是否为双射，1.pattern相同，对应的str一定相同；2.pattern不同，对应的str一定不同
		int pLen = pattern.length();
		String[] strs = str.split(" ");
		int sLen = strs.length;
		if (pLen != sLen) {
			return false;
		}
		Map<Character, String> map = new HashMap<>();
		Map<String, Character> reMap = new HashMap<>();
		Set<String> set = new HashSet<>();
		for (int i = 0; i < pLen; i++) {
			char temp = pattern.charAt(i);
			if (!map.containsKey(temp)) {
				if (set.contains(strs[i])) {
					return false;
				} else {
					set.add(strs[i]);
				}
				map.put(temp, strs[i]);
				reMap.put(strs[i], temp);
			} else {
				String s = map.get(temp);
				if (reMap.get(s) != temp) {
					return false;
				}
				if (!s.equals(strs[i])) {
					return false;
				}
			}
		}
		return true;
	}

	public static String numberToWords(int num) {
		String result = "";
		if (num == 0) {
			return "Zero";
		}
		int a = num / 1000000000;
		if (a > 0) {
			result = group(a) + " Billion";
		}
		num = num % 1000000000;
		a = num / 1000000;
		if (a > 0) {
			result = result + " " + group(a) + " Million";
		}
		num = num % 1000000;
		a = num / 1000;
		if (a > 0) {
			result = result + " " + group(a) + " Thousand";
		}
		num = num % 1000;
		if (num != 0) {
			result = result + " " + group(num);
		}
		if (result.substring(0, 1).equals(" ")) {
			String temp = result.substring(1);
			return temp;
		} else {
			return result;
		}
	}

	public static String group(int num) {
		String[] TwoUnit = { "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };
		String[] OneUnit = { "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen",
				"Nineteen" };
		String[] least = { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };
		String result = "";
		int temp;
		if ((temp = num % 1000) != 0) {
			if (temp / 100 != 0) {
				result = least[temp / 100 - 1] + " Hundred";
				temp = temp % 100;
			}
			if (temp > 10 && temp < 20) {
				result = result + " " + OneUnit[temp - 11];
			} else if (temp <= 10 && temp > 0) {
				result = result + " " + least[temp - 1];
			} else if (temp >= 20 && temp < 100) {
				result = result + " " + TwoUnit[temp / 10 - 2];
				if (temp % 10 != 0) {
					result = result + " " + least[temp % 10 - 1];
				}
			}
		}
		if (result.substring(0, 1).equals(" ")) {
			// System.out.println(result.substring(1) + " 子串有空格");
			return result.substring(1);
		}
		return result;
	}

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

    public static int findPoisonedDuration(int[] timeSeries, int duration) {
	    if (timeSeries.length <= 0){
	        return 0;
        }
	    int result = 0;
	    int endTime = 0;
        for(int curTime : timeSeries){
            if (endTime == 0){
                endTime = curTime + duration;
            } else {
                if (curTime >= endTime){
                    result += duration;
                    endTime = curTime + duration;
                } else {
                    result += duration -  endTime + curTime;
                    endTime = curTime + duration;
                }
            }
        }
        return result + duration;
    }

    public int countNumbersWithUniqueDigits(int n) {
	    if (n == 0){
	        return 1;
        }
        int result = 0;
        for (int i = 1; i <= n; i++){
            result += findUniqueDigitsByLength(i);
        }
        return result;
    }
    public int findUniqueDigitsByLength(int n){
	    if (n == 1){
	        return 10;
        }
        int result = 9;
        for(int i = 1; i < n; i++){
	        result *= (10 - i);
        }
        return result;
    }

}
