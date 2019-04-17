package com.dbg.config

import com.dbg.controller.TodoRoutes
import com.dbg.service.TodoService
import com.google.gson.Gson
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import io.ktor.application.Application

class MainModule(private val application: Application) : AbstractModule() {
    override fun configure() {
        bind(TodoRoutes::class.java).asEagerSingleton()
        bind(TodoService::class.java).asEagerSingleton()
        bind(Application::class.java).toInstance(application)
    }

    @Provides
    @Singleton
    fun gson(): Gson = Gson()

}