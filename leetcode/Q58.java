class Solution {
    public String reverseWords(String s) {
    String[] a = s.split(" ");
            StringBuffer sb = new StringBuffer();
            for(int i = a.length-1;i>=0;i--){
                if(!a[i].equals(""))  {
                sb.append(a[i]);
                sb.append(" ");}
            }
            return sb.toString().trim();
    }
}