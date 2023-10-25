package com.monk.kotlinstudy

class ValValConst

/**
 * 1. var 和 val 所声明的属性，其最本质的区别就是：val 不能有 setter，这就达到了 Java 中 final 的效果。
 * 2. var 可变， val 只读（而不是不可变）
 * 3. const 不可变，搭配 val 使用 const val(编译时常量)， 只能定义String 或基本类型
 * 4. const 必须放在object或顶层
 */
class Address{
    var provice = "zhejiang"
    val city = "hangzhou"

    val nickName:String =  if (city=="hangzhou") "jiangtangjiang" else "changjiang"

    companion object {
        private const val phoneCode="0557"
        val nickName:String =  if (phoneCode=="0557") "jiangtangjiang" else "changjiang"

    }
}
fun prettify(address:Address):String{
    val district = "xihu"
    return "district:$district,${address.city},${address.provice}"
}