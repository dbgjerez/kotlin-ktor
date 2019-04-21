package com.dbg.dao

import com.datastax.oss.driver.api.core.CqlIdentifier
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.CqlSessionBuilder
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.querybuilder.QueryBuilder.bindMarker
import com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.datastax.oss.driver.internal.querybuilder.insert.DefaultInsert
import com.datastax.oss.driver.internal.querybuilder.select.DefaultSelect
import com.dbg.dto.TodoDTO
import com.dbg.model.Todo

class TodoRepository {

    private val session: CqlSession
    private val limit = 10
    private val replicationFactor = 1
    private val columnId = CqlIdentifier.fromCql("id")
    private val columnTitle = CqlIdentifier.fromCql("title")
    private val table = CqlIdentifier.fromCql("todo")
    private val keyspace = CqlIdentifier.fromCql("example")

    // QUERIES
    private val findAll = DefaultSelect(keyspace, table).all().limit(limit).build()
    private val queryFindById = DefaultSelect(keyspace, table).whereColumn(columnId).isEqualTo(bindMarker()).limit(1)

    init {
        keyspace()
        table()
        session = session()
    }

    private fun sessionInit() = CqlSessionBuilder().build()
    private fun session(): CqlSession = CqlSessionBuilder().withKeyspace(keyspace).build()
    private fun keyspace() =
        sessionInit().execute(
            SchemaBuilder.createKeyspace(keyspace).ifNotExists().withSimpleStrategy(
                replicationFactor
            ).build()
        )

    private fun table() = session().execute(
        SchemaBuilder.createTable(keyspace, table).ifNotExists().withPartitionKey(
            columnId,
            DataTypes.TEXT
        ).withColumn(columnTitle, DataTypes.TEXT).build()
    )

    fun findAll() = session.execute(findAll).map { mapper(it) }.filterNotNull()

    private fun mapper(it: Row) = Todo(
        it.getString(columnId),
        it.getString(columnTitle) ?: String()
    )

    fun findbyId(id: String?): Todo? = session.execute(
        DefaultSelect(
            keyspace,
            table
        ).whereColumn(columnId).isEqualTo(literal(id)).limit(1).build()
    ).one().let { mapper(it!!) }

    fun create(todo: Todo?) =
        session.execute(
            DefaultInsert(keyspace, table).value(columnId, literal(todo?.id!!)).value(
                columnTitle,
                literal(todo.title)
            ).build()
        ).let { todo }

    fun update(id: String?, todo: TodoDTO?) = {}
    fun delete(id: String?) = {}

}