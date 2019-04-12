package com.dbg.controller

import com.dbg.service.TodoService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import javax.inject.Inject

class TodoRoutes @Inject constructor(application: Application, todoService: TodoService) {
    init {
        application.routing {
            get("/todo") {
                call.respond(todoService.findAll())
            }
        }
    }
}