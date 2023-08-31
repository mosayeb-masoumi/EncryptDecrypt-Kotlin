package com.example.encrypted_decrypted_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.encrypted_decrypted_kotlin.ui.theme.Encrypted_Decrypted_KotlinTheme
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //source https://www.youtube.com/watch?v=aaSck7jBDbw

        val cryptoManager = CryptoManager()

        setContent {
            Encrypted_Decrypted_KotlinTheme {


                var messageToEncrypt by remember { mutableStateOf("") }
                var messageToDecrypt by remember { mutableStateOf("") }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp)
                    )
                    {

                        TextField(
                            value = messageToEncrypt,
                            onValueChange = {
                                messageToEncrypt = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(text = "Encrypt string") }
                        )

                        Row(modifier = Modifier.fillMaxWidth()) {

                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    val bytes = messageToEncrypt.encodeToByteArray()
                                    val file = File(filesDir, "secret.txt")
                                    if (!file.exists()) {
                                        file.createNewFile()
                                    }
                                    val fos = FileOutputStream(file)
                                    messageToDecrypt = cryptoManager.encrypt(
                                        bytes = bytes,
                                        outputStream = fos
                                    ).decodeToString()  // used decodeToString()   to show result in text
                                }) {
                                Text(text = "Encrypt")
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    val file = File(filesDir, "secret.txt")
                                    messageToEncrypt = cryptoManager.decrypt(
                                        inputStream = FileInputStream(file)
                                    ).decodeToString()
                                }) {
                                Text(text = "Decrypt")   // used decodeToString() to show result in textFiled
                            }

                        }

                        Text(text = messageToDecrypt)
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}