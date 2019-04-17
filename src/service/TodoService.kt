package com.dbg.service

import com.dbg.dto.TodoDTO
import com.google.gson.Gson
import com.google.inject.Inject

class TodoService @Inject constructor(private val gson: Gson) {

    fun findAll(): TodoDTO {
        val t = """{"id": "prueba", "title":"hola"}"""
        return gson.fromJson(t, TodoDTO::class.java)
    }

}