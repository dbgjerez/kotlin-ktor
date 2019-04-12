package com.dbg.config

import com.dbg.controller.TodoRoutes
import com.dbg.service.TodoService
import com.google.inject.AbstractModule
import io.ktor.application.Application

class MainModule(private val application: Application) : AbstractModule() {
    override fun configure() {
        bind(TodoRoutes::class.java).asEagerSingleton()
        bind(TodoService::class.java).asEagerSingleton()
        bind(Application::class.java).toInstance(application)
    }
}