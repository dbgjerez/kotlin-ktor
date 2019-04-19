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
            route("/todo") {
                get {
                    call.respond(HttpStatusCode.OK, todoService.findAll())
                }
                get("{id}") {
                    call.parameters["id"].let { todoService::findById }.let { call.respond(HttpStatusCode.OK, it) }
                }
                post {
                    val dto = call.receiveOrNull<TodoDTO>()
                    call.respond(HttpStatusCode.Created, todoService.create(dto)!!)
                }
                put("{id}") {
                    val id = call.parameters["id"]
                    call.receiveOrNull<TodoDTO>().let { todoService.update(id, it) }
                        .let { call.respond(HttpStatusCode.OK) }
                }
                delete("{id}") {
                    call.parameters["id"].let { todoService::delete }.let { call.respond(HttpStatusCode.OK) }
                }
            }
        }
    }
}