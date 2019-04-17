package com.dbg.controller

import com.dbg.dto.TodoDTO
import com.dbg.service.TodoService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.*
import javax.inject.Inject

class TodoRoutes @Inject constructor(application: Application, todoService: TodoService) {
    init {
        application.routing {
            get("/todo") {
                call.respond(HttpStatusCode.OK, todoService.findAll())
            }
            get("/todo/{id}") {
                call.parameters["id"].let { todoService::findById }.let { call.respond(HttpStatusCode.OK, it) }
            }
            post("/todo") {
                call.receiveOrNull<TodoDTO>().let { todoService::create }
                    .let { call.respond(HttpStatusCode.Created, it) }
            }
            put("/todo/{id}") {
                val id = call.parameters["id"]
                call.receiveOrNull<TodoDTO>().let { todoService.update(id, it) }.let { call.respond(HttpStatusCode.OK) }
            }
            delete("/todo/{id}") {
                call.parameters["id"].let { todoService::delete }.let { call.respond(HttpStatusCode.OK) }
            }
        }
    }
}