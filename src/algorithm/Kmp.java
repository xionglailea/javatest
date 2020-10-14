package algorithm;

/**
 * kmp算法 字符串匹配算法
 *
 * <p>
 * create by xiongjieqing on 2020/10/13 18:47
 */
public class Kmp {

    public static void main(String[] args) {
        new Kmp().match("abbaabbaaba", "abbaaba");
    }

    /**
     * 模式串最大匹配表
     * abbaab 的头部有 a, ab, abb, abba, abbaa（不包含最后一个字符。为「真前缀」）
     * abbaab 的尾部有 b, ab, aab, baab, bbaab（不包含第一个字符。为「真后缀」）
     * 这样最长匹配是 ab，也就是待匹配字符串不回退时，模式串需要回退到第三个字符去继续匹配。
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
     * 核心是 待匹配的字符串，不回退，只回退模式串，而模式串回退多少，依赖模式串的匹配表
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
                System.out.println("匹配成功 index = " + (i - pattern.length() + 1));
                maxMatch = patternMaxMatch[maxMatch - 1];
            }
        }
    }

}
