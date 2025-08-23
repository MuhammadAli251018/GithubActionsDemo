package com.onemorenerd.githubactionsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.onemorenerd.githubactionsdemo.ui.theme.GithubActionsDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubActionsDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                        ) {
                            Greeting(
                                name = "Muhammad",
                                modifier = Modifier.padding(innerPadding)
                            )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        fontSize = 24.sp
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GithubActionsDemoTheme {
        Greeting("Android")
    }
}