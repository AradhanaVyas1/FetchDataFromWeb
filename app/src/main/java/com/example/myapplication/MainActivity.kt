package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableInferredTarget
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuoteApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteApp() {
        var quotes by remember { mutableStateOf<List<Quote>>(emptyList()) }
        var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
        val quoteService = QuoteService.create()
        val coroutineScope = rememberCoroutineScope()


    Scaffold() { padding ->

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Button(onClick = {
                    coroutineScope.launch {
                        quotes = quoteService.getQuotes().quotes
                    }
                }) {
                    Text("Display All Quotes")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { searchQuery = TextFieldValue("") }) {
                    Text("Search Authors")
                }
                Spacer(modifier = Modifier.height(16.dp))
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    decorationBox = { innerTextField ->
                        Box(Modifier.padding(8.dp)) {
                            if (searchQuery.text.isEmpty()) {
                                Text("Search 'your'")
                            }
                            innerTextField()
                        }
                    }
                )
                LazyColumn {
                    items(quotes.filter {
                        it.quote.contains(searchQuery.text, ignoreCase = true) ||
                                it.author.contains(searchQuery.text, ignoreCase = true)
                    }) { quote ->
                        Text("${quote.quote} - ${quote.author}", modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }


