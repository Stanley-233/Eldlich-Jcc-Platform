package stanley.eldlichjcc.model

// 主时间线的信息
data class Event (
    val session : Int,
    val number : Int,
    val message : String
)

// 指令
data class Directive (
    val session : Int,
    val number : Int,
    val title : String,
    val message : String,
    val sender : String,
    val state : String,
    val receiver : String,
    val reply : String
)
