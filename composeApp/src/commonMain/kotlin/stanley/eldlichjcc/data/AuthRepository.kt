package stanley.eldlichjcc.data

import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

import stanley.eldlichjcc.model.*

interface AuthRepository {
    suspend fun login(username: String, password: String): LoginResponse
}

class AuthRepositoryImpl : AuthRepository {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    override suspend fun login(username: String, password: String): LoginResponse {
        return try {
            client.post(UserData.url+"/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(username, password))
            }.body<LoginResponse>()
        } catch (e: Exception) {
            LoginResponse(success = false, error = e.message ?: "Unknown error")
        }
    }
}