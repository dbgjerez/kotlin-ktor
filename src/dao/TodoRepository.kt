package com.dbg.dao

import com.datastax.oss.driver.api.core.CqlIdentifier
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.CqlSessionBuilder
import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.datastax.oss.driver.internal.querybuilder.insert.DefaultInsert
import com.datastax.oss.driver.internal.querybuilder.select.DefaultSelect
import com.dbg.dto.TodoDTO
import com.dbg.model.Todo

class TodoRepository {

    val keyspace = "example"
    val table = "todo"
    val session: CqlSession
    val limit = 10

    // QUERIES
    val findAll =
        DefaultSelect(CqlIdentifier.fromCql(keyspace), CqlIdentifier.fromCql(table)).all().limit(limit).build()

    init {
        keyspace()
        table()
        session = session()
    }

    private fun sessionInit() = CqlSessionBuilder().build()
    private fun session(): CqlSession = CqlSessionBuilder().withKeyspace(keyspace).build()
    private fun keyspace() =
        sessionInit().execute(SchemaBuilder.createKeyspace(keyspace).ifNotExists().withSimpleStrategy(1).build())

    private fun table() = session().execute(
        SchemaBuilder.createTable(keyspace, table).ifNotExists().withPartitionKey(
            "id",
            DataTypes.TEXT
        ).withColumn("title", DataTypes.TEXT).build()
    )


    fun findAll() = session.execute(findAll).map {
        Todo(
            it.getString(CqlIdentifier.fromCql("id")),
            it.getString(CqlIdentifier.fromCql("title")) ?: String()
        )
    }.filterNotNull()


    fun findbyId(id: String?): Todo? = Todo(id, "v")
    fun create(todo: Todo?) = session.execute(
        DefaultInsert(CqlIdentifier.fromCql(keyspace), CqlIdentifier.fromCql(table)).value(
            "id", literal(todo?.id!!)
        ).value("title", literal(todo.title)).build()
    ).let { todo }

    fun update(id: String?, todo: TodoDTO?) = {}
    fun delete(id: String?) = {}

}