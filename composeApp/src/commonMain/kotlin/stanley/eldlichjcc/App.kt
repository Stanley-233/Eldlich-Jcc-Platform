package stanley.eldlichjcc

import androidx.compose.material.*
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

import stanley.eldlichjcc.data.AuthRepositoryImpl
import stanley.eldlichjcc.ui.LoginScreen
import stanley.eldlichjcc.ui.HomeScreen

// 更新后的 App 组件
@Composable
@Preview
fun App() {
    MaterialTheme {
        var isLoggedIn by remember { mutableStateOf(false) }
        val authRepository = remember { AuthRepositoryImpl() }

        if (isLoggedIn) {
            // 登录成功后的主界面
            HomeScreen(onLogout = { isLoggedIn = false })
        } else {
            LoginScreen(
                authRepository = authRepository,
                onLoginSuccess = { isLoggedIn = true }
            )
        }
    }
}
