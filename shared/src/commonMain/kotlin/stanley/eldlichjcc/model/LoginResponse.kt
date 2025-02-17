package stanley.eldlichjcc.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    val success: Boolean,
    val token: String? = null,
    val error: String? = null
)