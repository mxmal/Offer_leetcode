
fun main(args: Array<String>) {
    val ex:ExStr=ExStr()
    print(ex.wordReverse("   the dfgh    sky  is  blue    "))
}

class ExStr{
    fun wordReverse(str:String):String{
        var listData:List<String> 
        listData = str.split(" ")
        var myList: MutableList<String> = mutableListOf()
        println(myList)
        var cs:String = String()
        listData.forEach(
           fun (element:String){
               if(element!=""){
                   myList.add(element)
               
               }
             
            }
        )

        myList.reverse()
        myList.forEach(fun(element:String){
                cs = cs+element+" "
        })
        return cs.trim()
    }
    
}