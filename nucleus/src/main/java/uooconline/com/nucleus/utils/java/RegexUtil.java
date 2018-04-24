package uooconline.com.nucleus.utils.java;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    //匹配某个字符串符合的区间文本：如:<em>A</em>B<em>C</em>DEFG-><em>A</em>直接的文本
    public static List<String> matcherBetween(String originStr, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(originStr);
        List<EMItem> targetPosition = new ArrayList<>();
        List<String> matchStr = new ArrayList<>();
        while (matcher.find()) {
            targetPosition.add(new EMItem(matcher.start(), matcher.end()));
        }
        for (int i = 0; i < targetPosition.size(); i++) {
            //脚标有效
            if (i + 1 < targetPosition.size())
                //每个item的结束位置到下一个item的起始位置
                matchStr.add(originStr.substring(targetPosition.get(i).end, targetPosition.get(i + 1).start));
        }
        return matchStr;
    }

    //过滤字符串
    public static String filterString(String originStr, String regex) {
        StringBuilder totalStr = new StringBuilder();
        String[] split = originStr.split(regex);
        for (int i = 0; i < split.length; i++) {
            if (split[i].length() != 0) {
                totalStr.append(split[i]);
            }
        }
        return totalStr.toString();
    }

    private static class EMItem {

        public int start;
        public int end;

        private EMItem(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
