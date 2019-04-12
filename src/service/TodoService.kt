package com.dbg.service

import com.dbg.dto.TodoDTO

class TodoService {

    fun findAll(): TodoDTO = TodoDTO("1", "Hacer a cama")

}