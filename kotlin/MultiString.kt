fun main(args: Array<String>) {
    val util = Util()
    print(util.getMultiString("999", "999"))
}

class Util{
    fun getMultiString(strFirst:String,strSecond:String):String{
        if(strFirst.first()=='0'||strSecond.first()=='0'){
            return "0"
        }
        var count=0
        var strFirstList = strFirst.toList()
        var strSecondList = strSecond.toList()
        var sumList:MutableList<List<Int>> = mutableListOf()
        var storage:List<Int> = mutableListOf(0)
        strFirstList=strFirstList.reversed()

        strFirstList.forEach(fun(value:Char){            
            sumList.add(getSum(count,getNum(value),getNum(strSecondList)))
            count++
        })
        sumList.forEach(
            fun(numList:List<Int>){
               storage = plus(numList,storage)
            }
        )
        
        // print(sumList)
        // val list3 = str2.split(Regex("[0-9]+"))正则匹配分割字符串
        // print(list3.size)
        // for (str in list3){
        //     print("$str 1 \n ")
        // }
        var str=StringBuffer()//转换为字符串
        storage = storage.reversed()
        storage.forEach(
            fun(n:Int){
                str.append(n)
            }
        )
        var s=str.toString()
        return s
    }

    fun plus(listLong:List<Int>,listShort:List<Int>):List<Int>{//加法运算
        var i=0
        var jinwei=0
        var num:MutableList<Int> = mutableListOf()
        listLong.forEach(
            fun(value:Int){
                if(i<listShort.size){
                    
                    num.add((value+listShort[i]+jinwei) % 10)
                    jinwei = (value+listShort[i]+jinwei)/10
                }else{
                    if(jinwei>0){
                   
                
                    num.add((value+jinwei) % 10)
                    jinwei = (value+jinwei)/10
                    }else{
                        num.add(value)
                    }
                }
                i++
            }
        )
        if(jinwei>0){
            num.add(jinwei)
        }//补充进位
        print(num)
        return num
    }

    fun getNum(value:Char):Int{//数字字符转化
        return value-'0'
    }
    fun getNum(value:List<Char>):MutableList<Int>{
        var sum:MutableList<Int> = mutableListOf()
        value.forEach (fun(numChar:Char)
        {
        sum.add(getNum(numChar   ))
    }) 
            
            return sum
    }
//得商/10的余数%10
    fun getSum(count:Int,value:Int,strSecondList:MutableList<Int>):MutableList<Int>{//乘法运算
        var divide:Int 
        var c=count
        var jinwei:Int = 0
        var sum :MutableList<Int> = mutableListOf()
        strSecondList.reverse()
        strSecondList.forEach(
            fun(n:Int){
                divide = (n*value+jinwei)%10         
                sum.add(divide)
                jinwei = (n*value+jinwei)/10
            }
        )
        if(jinwei!=0){
            sum.add(jinwei)
        }       
            sum.reverse()
            while(c-->0){
                sum.add(0)
        }
      
        sum.reverse()
        //确定格式
        return sum
    }
   
}