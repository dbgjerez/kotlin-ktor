package com.dbg.config

import com.dbg.controller.TodoRoutes
import com.dbg.dao.TodoRepository
import com.dbg.mapper.TodoMapper
import com.dbg.service.TodoService
import com.google.inject.AbstractModule
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import java.text.DateFormat

class MainModule(private val application: Application) : AbstractModule() {
    override fun configure() {
        application.install(ContentNegotiation) {
            gson {
                setDateFormat(DateFormat.LONG)
                setPrettyPrinting()
            }
        }
        application.install(DefaultHeaders)

        bind(TodoRoutes::class.java).asEagerSingleton()
        bind(TodoService::class.java).asEagerSingleton()
        bind(TodoRepository::class.java).asEagerSingleton()
        bind(TodoMapper::class.java).asEagerSingleton()
        bind(Application::class.java).toInstance(application)
    }

}