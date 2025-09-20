package com.example.todolistapp

import androidx.compose.ui.graphics.Color
import org.json.JSONObject
import androidx.compose.ui.graphics.toArgb
import org.joda.time.DateTime

val TodoItem.json: JSONObject
    get() {
        val json = JSONObject()
        json.put("uid", uid)
        json.put("text", text)
        json.put("isDone", isDone)

        if (color != Color.White) {
            json.put("color", color.toArgb())
        }

        if (importance != Importance.ORDINARY) {
            json.put("importance", importance.name)
        }

        if (deadline != null) {
            json.put("deadline", deadline.millis)
        }

        return json
    }

fun parse(json: JSONObject): TodoItem? {
    try {
        val uid = json.getString("uid")
        val text = json.getString("text")

        val color = if (json.has("color")) {
            Color(json.getInt("color"))
        } else {
            Color.White
        }

        val importance = if (json.has("importance")) {
            Importance.valueOf(json.getString("importance"))
        } else {
            Importance.ORDINARY
        }

        val deadline = if (json.has("deadline")) {
            DateTime(json.getLong("deadline"))
        } else {
            null
        }

        return TodoItem(uid = uid, text = text, importance = importance, color = color, deadline = deadline)
    }
    catch (e: Exception) {
        return null
    }
}