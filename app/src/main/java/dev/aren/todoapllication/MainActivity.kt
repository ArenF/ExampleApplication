package dev.aren.todoapllication

import android.graphics.Paint.Align
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import dev.aren.todoapllication.ui.theme.ToDoApllicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoApllicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScaffold()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold() {
    val todoList = remember { mutableStateListOf("초기값") }
    var page by remember { mutableIntStateOf(0) }
    fun changePage(p:Int) {
        page = p
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("TODO 앱")
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton( onClick = {
                if (page == 1) changePage(0) else changePage(1)
            }) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add"
                    )
                    Text(text = "ADD TODO")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (page) {
                0 -> ListShowPage(todoList)
                1 -> ListAddPage(changePage = {
                    todoList.add(it)
                    changePage(0)
                })
            }
        }
    }
}

@Composable
fun ListShowPage(list:MutableList<String>) {

    list.forEach { el ->
        Column(
            modifier = Modifier.padding(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    tint = Color.Green,
                    contentDescription = "",
                )
                Text(
                    text = el.toString(),
                    fontSize = TextUnit(value = 24f, type = TextUnitType.Sp)
                )
            }
            Divider(thickness = 2.dp, color = Color.LightGray)
        }
    }
}

@Composable
fun ListAddPage(changePage:(String) -> Unit) {
    var value by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(8.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            placeholder = { Text(text = "텍스트") },
            value = value,
            onValueChange = { value = it }
        )
        Button(
            onClick = { changePage.invoke(value) }
        ) {
            Text(text = "추가")
        }
    }
}