fun main() {
    val nums=intArrayOf(-1, 0, 1, 2, -1, -4)
    val list=listOf(-1, 0, 1, 2, -1, -4)
    var a:Solution=Solution()
    
    print(a.threeSum(list))
    print(a.threeSum(nums))
}


class Solution {
    fun threeSum(num: List<Int>): List<List<Int>> {
        var nums=num.sorted()//List<Int>排序会产生一个新的集合，不会对原集合做出改变
        // num.sort()kotlin数组排序对本数组排序
        val result = hashSetOf<List<Int>>()
        for (i in 0 until nums.size - 2) {
    
            var end = nums.size - 1
            var start = i + 1
            while (start != end && start < end) {
                val item = ArrayList<Int>(3)
                val sum = nums[start] + nums[end] + nums[i]
                
                when {
                    sum > 0 -> end -= 1
                    sum < 0 -> start += 1
                    else -> {
                        item.add(nums[i])
                        item.add(nums[start])
                        item.add(nums[end])
                        result.add(item)
                        start += 1
                        end -= 1
                    }
                }
            }
        }
        return result.toList()
    }

    fun threeSum(nums:IntArray): List<List<Int>> {
       nums.sort()//List<Int>排序会产生一个新的集合，不会对原集合做出改变
        // num.sort()kotlin数组排序对本数组排序
        val result = hashSetOf<List<Int>>()
        for (i in 0 until nums.size - 2) {
    
            var end = nums.size - 1
            var start = i + 1
            while (start != end && start < end) {
                val item = ArrayList<Int>(3)
                val sum = nums[start] + nums[end] + nums[i]
             
                when {
                    sum > 0 -> end -= 1
                    sum < 0 -> start += 1
                    else -> {
                        item.add(nums[i])
                        item.add(nums[start])
                        item.add(nums[end])
                        result.add(item)
                        start += 1
                        end -= 1
                    }
                }
            }
        }
        return result.toList()
    }
}