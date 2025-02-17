package stanley.eldlichjcc.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.NavigationRail
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import stanley.eldlichjcc.data.UserData

sealed class NavigationItem(val title: String) {
    object Home : NavigationItem("主界面")
    object Timeline : NavigationItem("时间线")
    object Directives : NavigationItem("指令列表")
    object Settings : NavigationItem("设置")
}

@Composable
fun HomeScreen(onLogout: () -> Unit) {
    var selectedItem by remember { mutableStateOf<NavigationItem>(NavigationItem.Home) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(Modifier.fillMaxSize()) {
            // 左侧导航栏
            NavigationRail(
                header = {
                    Text("导航", modifier = Modifier.padding(16.dp))
                }
            ) {
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text(NavigationItem.Home.title) },
                    selected = selectedItem == NavigationItem.Home,
                    onClick = { selectedItem = NavigationItem.Home }
                )

                NavigationRailItem(
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text(NavigationItem.Timeline.title) },
                    selected = selectedItem == NavigationItem.Timeline,
                    onClick = { selectedItem = NavigationItem.Timeline }
                )

                NavigationRailItem(
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text(NavigationItem.Directives.title) },
                    selected = selectedItem == NavigationItem.Directives,
                    onClick = { selectedItem = NavigationItem.Directives }
                )

                NavigationRailItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text(NavigationItem.Settings.title) },
                    selected = selectedItem == NavigationItem.Settings,
                    onClick = { selectedItem = NavigationItem.Settings }
                )
            }

            // 右侧内容区域
            Box(modifier = Modifier.weight(1f)) {
                when (selectedItem) {
                    is NavigationItem.Home -> HomeContent("测试", onLogout)
                    is NavigationItem.Timeline -> TimelineScreen()
                    is NavigationItem.Directives -> DirectiveScreen()
                    is NavigationItem.Settings -> SettingsScreen()
                }
            }
        }
    }
}

@Composable
private fun HomeContent(currentTime: String, onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "当前时间：$currentTime",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            "用户名：" + UserData.username,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            "权限：" + UserData.role,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            "席位：" + UserData.seat,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onLogout) {
            Text("安全登出", modifier = Modifier.padding(horizontal = 24.dp))
        }
    }
}