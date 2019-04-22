package com.dbg.service

import com.dbg.dao.TodoRepository
import com.dbg.dto.TodoDTO
import com.dbg.mapper.TodoMapper
import com.google.inject.Inject

class TodoService @Inject constructor(private val todoRepository: TodoRepository, private val todoMapper: TodoMapper) {

    fun findAll() = todoRepository.findAll().mapNotNull(todoMapper::map)
    fun findById(id: String?) = todoRepository.findbyId(id).let(todoMapper::map)
    fun create(todo: TodoDTO?) = todo.let(todoMapper::map).let(todoRepository::create).let(todoMapper::map)
    fun update(id: String?, todo: TodoDTO?) = todoRepository.update(id, todo)
    fun delete(id: String?) = id.let(todoRepository::delete)

}