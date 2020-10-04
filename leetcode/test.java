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
                if (i >= str.length() || ch != str.charAt(i)) {
                    strs[0] = strs[0].substring(0, i);
                }
            }
        }
        return strs[0];


    }

}