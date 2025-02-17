package stanley.eldlichjcc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform