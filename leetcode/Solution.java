class Solution1 {
    public int findRepeatNumber(int[] nums) {
        Set<Integer> set=new HashSet<>();
        int res=-1;
        for(int num:nums){
            if(!set.add(num)){
                res = num;
                break;
            }
        }
        return res;
    }
}