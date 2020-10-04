public class test{
    public static void main(String[] args) {
        String[] str={"flower","flow","flight"};
        System.out.println(findlonest(str));
    }
    public static String findlonest(String[] strs){
        if (strs == null || strs.length == 0) return "";
        for (int i = 0; i < strs[0].length(); i++) {
            char ch = strs[0].charAt(i);
            for (String str : strs) {
                if (i >= str.length() || ch != str.charAt(i)) {//筛选含有相似字符前缀的子串
                    strs[0] = strs[0].substring(0, i);//获取最长子串
                }
            }
        }
        return strs[0];


    }

}

题变解  假如求最长后缀字符串，是否可以倒转字符串再来使用这个方法
