package algorithm;

/**
 * kmp�㷨 �ַ���ƥ���㷨
 *
 * <p>
 * create by xiongjieqing on 2020/10/13 18:47
 */
public class Kmp {

    public static void main(String[] args) {
        new Kmp().match("abbaabbaaba", "abbaaba");
    }

    /**
     * ģʽ�����ƥ���
     * abbaab ��ͷ���� a, ab, abb, abba, abbaa�����������һ���ַ���Ϊ����ǰ׺����
     * abbaab ��β���� b, ab, aab, baab, bbaab����������һ���ַ���Ϊ�����׺����
     * �����ƥ���� ab��Ҳ���Ǵ�ƥ���ַ���������ʱ��ģʽ����Ҫ���˵��������ַ�ȥ����ƥ�䡣
     * @param pattern
     * @return
     */
    public int[] maxMatchLength(String pattern) {
        int[] maxMatchLengths = new int[pattern.length()];
        int maxMatch = 0;
        for (int i = 1; i < pattern.length(); i++) {
            while (maxMatch > 0 && pattern.charAt(i) != pattern.charAt(maxMatch)) {
                maxMatch = maxMatchLengths[maxMatch - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(maxMatch)) {
                maxMatch++;
            }
            maxMatchLengths[i] = maxMatch;
        }
        return maxMatchLengths;
    }

    /**
     * ������ ��ƥ����ַ����������ˣ�ֻ����ģʽ������ģʽ�����˶��٣�����ģʽ����ƥ���
     * @param text
     * @param pattern
     */
    public void match(String text, String pattern) {
        int[] patternMaxMatch = maxMatchLength(pattern);
        int maxMatch = 0;

        for (int i = 0; i < text.length(); i++) {
            while (maxMatch > 0 && pattern.charAt(maxMatch) != text.charAt(i)) {
                maxMatch = patternMaxMatch[maxMatch - 1];
            }
            if (text.charAt(i) == pattern.charAt(maxMatch)) {
                maxMatch++;
            }
            if (maxMatch == pattern.length()) {
                System.out.println("ƥ��ɹ� index = " + (i - pattern.length() + 1));
                maxMatch = patternMaxMatch[maxMatch - 1];
            }
        }
    }

}
