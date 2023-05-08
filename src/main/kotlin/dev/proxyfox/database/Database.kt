package dev.proxyfox.database

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

internal val defaultJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

/**
 * Represents a database.
 * @param driver The database driver
 * @param json An optional kx.ser [Json]
 * */
public class Database(
    private val driver: DatabaseDriver,
    public val json: Json = defaultJson
): DatabaseDriver by driver {
    init {
        if (driver is Database)
            throw IllegalArgumentException("dev.proxyfox.database.Database\$driver must not be of dev.proxyfox.database.Database")
    }

    public suspend inline fun <reified T> request(request: DatabaseRequest): T? {
        return handleRequest(request)?.let {
            json.decodeFromJsonElement(it)
        }
    }
}

/**
 * This is responsible for managing connections and requests to the database,
 * it should be all you need to implement on your own.
 * */
public interface DatabaseDriver {
    /**
     * Responsible for performing requests.
     * @param request The request to execute
     * */
    public suspend fun handleRequest(request: DatabaseRequest): JsonObject?
}
