package com.dbg.dao

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.CqlSessionBuilder
import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.dbg.dto.TodoDTO
import com.dbg.model.Todo
import java.util.*

class TodoRepository {

    val keyspace = "example"
    val table = "todo"
    val session: CqlSession

    init {
        keyspace()
        table()
        session = session()
    }

    private fun sessionInit() = CqlSessionBuilder().build()
    private fun session(): CqlSession = CqlSessionBuilder().withKeyspace(keyspace).build()
    private fun keyspace() = sessionInit().execute(SchemaBuilder.createKeyspace(keyspace).withSimpleStrategy(1).build())
    private fun table() = session().execute(
        SchemaBuilder.createTable(keyspace, table).withPartitionKey(
            "id",
            DataTypes.TEXT
        ).withColumn("title", DataTypes.TEXT).build()
    )

    fun findAll(): List<Todo> = Arrays.asList()
    fun findbyId(id: String?): Todo? = Todo(id, "v")
    fun create(todo: Todo?) = Todo("a", "b")
    fun update(id: String?, todo: TodoDTO?) = {}
    fun delete(id: String?) = {}

}