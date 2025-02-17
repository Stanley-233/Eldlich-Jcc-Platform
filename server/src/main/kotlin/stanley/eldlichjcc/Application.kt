package stanley.eldlichjcc

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import stanley.eldlichjcc.data.DbHandler

import stanley.eldlichjcc.model.*

fun main() {
    DbHandler.init()
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
        post("/login") {
            val request = call.receive<LoginRequest>()
            val check : Pair<String,String>? = DbHandler.loginCheck(request)
            // 这里添加你的认证逻辑，示例使用硬编码验证
            if (check == null) {
                call.respond(
                    LoginResponse(
                        success = false,
                        error = "用户名或密码错误"
                    )
                )
            } else {
                call.respond(
                    LoginResponse(
                        success = true,
                        role = check.first,
                        seat = check.second
                    )
                )
            }
        }
    }
}