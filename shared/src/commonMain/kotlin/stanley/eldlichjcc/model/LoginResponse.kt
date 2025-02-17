package stanley.eldlichjcc.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    val success: Boolean,
    val role: String? = null,
    val seat: String? = null,
    val error: String? = null
)