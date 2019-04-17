package com.dbg.mapper

import com.dbg.dto.TodoDTO
import com.dbg.model.Todo

class TodoMapper {

    fun map(todo: Todo?): TodoDTO? = TodoDTO(todo?.id, todo?.title)
    fun map(todoDTO: TodoDTO?): Todo? = todoDTO?.title?.let { Todo(todoDTO.id, it) }

}