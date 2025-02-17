package stanley.eldlichjcc

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*

import stanley.eldlichjcc.model.*

fun main() {
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

            // 这里添加你的认证逻辑，示例使用硬编码验证
            if (request.username == "admin" && request.password == "123456") {
                call.respond(
                    LoginResponse(
                        success = true,
                        token = "sample_jwt_token_here"
                    )
                )
            } else {
                call.respond(
                    LoginResponse(
                        success = false,
                        error = "Invalid credentials"
                    )
                )
            }
        }
    }
}