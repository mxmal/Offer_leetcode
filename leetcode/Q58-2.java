String自带substring方法


class Solution {
    public String reverseLeftWords(String s, int n) {
        String x = s.substring(n,s.length())+s.substring(0,n);
        return x;
    }
}
char数组拼接存储然后转换为String


class Solution {
    public String reverseLeftWords(String s, int n) {
        char x[] = new char[s.length()];
        for(int i=n;i<s.length();i++){
            x[i-n]=s.charAt(i);
        }
        for(int i=0;i<n;i++){
            x[s.length()-n+i]=s.charAt(i);
        }
        String x1 = String.valueOf(x);
        return x1;
    }
}

