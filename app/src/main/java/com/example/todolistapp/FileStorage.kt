package com.example.todolistapp

import android.content.Context
import org.joda.time.DateTime
import org.json.JSONArray

const val TIME_TO_DELETE_OVERDUE = 7

class FileStorage(context: Context) {
    private val _todoItems = mutableListOf<TodoItem>()
    val todoItems: List<TodoItem> get() = _todoItems.toList()
    private val file = context.getSharedPreferences("todos", Context.MODE_PRIVATE)
    private val key = "todo_items"

    fun addNewTodo(todoItem: TodoItem) {
        _todoItems.add(todoItem)
        saveTodosToFile()
    }

    fun deleteTodo(uid: String) {
        _todoItems.removeIf { it.uid == uid }
        saveTodosToFile()
    }

    fun saveTodosToFile() {
        val jsonArray = JSONArray().apply {
            _todoItems.forEach { put(it.json) }
        }
        file.edit().putString(key, jsonArray.toString()).apply()
    }

    fun loadTodosFromFile() {
        val jsonString = file.getString(key, "[]")
        val jsonArray = JSONArray(jsonString)

        _todoItems.clear()

        val list = (0 until jsonArray.length())
            .mapNotNull { jsonItem ->
                val jsonObject = jsonArray.getJSONObject(jsonItem)
                jsonObject.parse()
            }
        _todoItems.addAll(list)
        deletingOverdueTasks(TIME_TO_DELETE_OVERDUE)
    }

    fun deletingOverdueTasks(days: Int) {
        _todoItems.removeAll { todoItem ->
            todoItem.deadline?.plusDays(days)?.let { it < DateTime.now() } ?: false
        }
    }
}