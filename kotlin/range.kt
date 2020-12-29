fun main(args: Array<String>) {
    for (index in 1..10){
        print("$index")
        
    }
    for (index in 1 until 10){
        print(index)
    }
    for(index in 10 downTo 1){
        print(index)
    }
    // for (index in 100 downTo 1){
    //     print(index)
    // }
    // for (index in 1..100 step 2){
    //     print(index)//会输出1..3..5......
    // }
    // for (index in 1 until 10){
    //     println(index)//输出0..9
    // }
    // val array = arrayOf("a", "b", "c")
    // for ((index,e) in array.withIndex()){
    //     println("下标=$index----元素=$e")
    // }
    // val array = arrayOf("a", "b", "c")
    //     for (index in array.indices){
    //         println("index=$index")//输出0，1，2
    //     }
}