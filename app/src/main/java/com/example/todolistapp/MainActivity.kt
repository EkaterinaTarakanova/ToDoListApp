package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.todolistapp.ui.theme.ToDoListAppTheme
import org.joda.time.DateTime


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val fileStorage = FileStorage(this)

        val testItem1 = TodoItem(
            uid = "test-uid-1",
            text = "Купить молоко",
            importance = Importance.IMPORTANT,
            color = Color.Blue,
            deadline = DateTime.now().plusDays(2)
        )

        val testItem2 = TodoItem(
            text = "Сделать домашку",
            importance = Importance.ORDINARY,
            color = Color.White,
            deadline = DateTime.now().plusHours(8)
        )

        fileStorage.addNewTodo(testItem1)
        fileStorage.addNewTodo(testItem2)

        println("Сохранено задач: ${fileStorage.todoItems.size}")
        fileStorage.todoItems.forEach {
            println("Задача: ${it.text}, UID: ${it.uid}")
        }

        val newFileStorage = FileStorage(this)
        newFileStorage.loadTodosFromFile()
        println("Загружено задач: ${newFileStorage.todoItems.size}")

        println("\nУдаление задачи")
        newFileStorage.deleteTodo("test-uid-1")
        println("После удаления: ${newFileStorage.todoItems.size} задач")

//        setContent {
//            ToDoListAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "World",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoListAppTheme {
        Greeting("Android")
    }
}