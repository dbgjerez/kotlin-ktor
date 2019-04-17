package com.dbg.dao

import com.dbg.dto.TodoDTO
import com.dbg.model.Todo
import java.util.*

class TodoRepository {

    fun findAll(): List<Todo> = Arrays.asList(Todo("a", "a"))
    fun findbyId(id: String?): Todo? = Todo(id, "v")
    fun create(todo: Todo?) = Todo("a", "b")
    fun update(id: String?, todo: TodoDTO?) = {}
    fun delete(id: String?) = {}

}