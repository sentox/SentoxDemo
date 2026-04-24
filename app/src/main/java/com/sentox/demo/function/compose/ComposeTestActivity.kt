package com.sentox.demo.function.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import com.sentox.demo.R

/**
 * 描述：compose学习界面
 * 说明：
 * Created by Sentox
 * Created on 2026/4/24
 */
class ComposeTestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Preview()
        }
    }

    @Preview
    @Composable
    private fun Preview() {
        Card(Message("author", "body"))
    }

    @Composable
    private fun Card(message: Message) {

        Row{

            Image(
                painterResource(R.mipmap.bg_dragon),
                "desc"
            )

            Column {
                Text(message.author)
                Text(message.body)
            }
        }



    }

    data class Message(val author: String, val body: String)
}