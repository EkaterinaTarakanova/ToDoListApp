package com.example.todolistapp

import android.content.Context
import org.json.JSONArray
import java.io.File

class FileStorage(context: Context) {
    private val _todoItems = mutableListOf<TodoItem>()
    val todoItems: List<TodoItem> get() = _todoItems
    private val file = File(context.filesDir, "todos.json")

    fun addNewTodo(todoItem: TodoItem) {
        _todoItems.add(todoItem)
        saveTodosToFile()
    }

    fun deleteTodo(uid: String) {
        _todoItems.removeIf { it.uid == uid }
        saveTodosToFile()
    }

    fun saveTodosToFile() {
        val jsonArray = JSONArray()
        for (item in _todoItems) {
            jsonArray.put(item.json)
        }
        file.writeText(jsonArray.toString())
    }

    fun loadTodosFromFile() {
        if (!file.exists()) {
            return
        }
        val jsonString = file.readText()
        val jsonArray = JSONArray(jsonString)
        _todoItems.clear()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val todoItem = parse(jsonObject)

            if (todoItem != null) {
                _todoItems.add(todoItem)
            }
        }

    }
}