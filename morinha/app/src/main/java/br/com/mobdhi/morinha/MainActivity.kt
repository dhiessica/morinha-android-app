package br.com.mobdhi.morinha

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import br.com.mobdhi.morinha.app.AppMainScreen
import br.com.mobdhi.morinha.ui.theme.MorinhaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MorinhaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppMainScreen(
                        restartApp = {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    MainActivity::class.java
                                )
                            )
                            finish()
                        }
                    )
                }
            }
        }
    }
}
