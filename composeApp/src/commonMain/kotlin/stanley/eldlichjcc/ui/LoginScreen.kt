package stanley.eldlichjcc.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import stanley.eldlichjcc.SERVER_PORT
import stanley.eldlichjcc.data.AuthRepository
import stanley.eldlichjcc.data.UserData

@Composable
fun LoginScreen(
    authRepository: AuthRepository,
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit
) {
    var url by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Eldlich 危机联动体系指令系统",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = "Developed by Stanley\n" +
                    "Repository: https://github.com/Stanley-233/Eldlich-Jcc-Platform/",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(bottom = 32.dp),
            textAlign = TextAlign.Center
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("API地址") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("用户名") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("密码") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            singleLine = true
        )

        Button(
            onClick = {
                if (!url.isBlank()) {
                    UserData.url = url + SERVER_PORT
                }

                if (username.isBlank() || password.isBlank()) {
                    errorMessage = "用户名与密码不可为空"
                    return@Button
                }

                isLoading = true
                errorMessage = null

                // 启动协程执行登录操作
                CoroutineScope(Dispatchers.Main).launch {
                    val result = authRepository.login(username, password)
                    isLoading = false
                    if (result.success) {
                        UserData.username = username
                        UserData.role = result.role.toString()
                        UserData.seat = result.seat.toString()
                        onLoginSuccess()
                    } else {
                        errorMessage = result.error ?: "登陆失败"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("登录")
            }
        }
    }
}