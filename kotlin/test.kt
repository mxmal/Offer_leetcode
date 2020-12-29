fun main(args: Array<String>) {
    var i=2
    var list:MutableList<Int> = mutableListOf(9,9,9,9) 
    list.reverse()
    while(i-->0){
        list.add(0)
    }
    list.reverse()
    print(list)
}