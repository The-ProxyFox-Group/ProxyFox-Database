package dev.proxyfox.database

import dev.proxyfox.database.comparison.Comparison
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

/**
 * Represents a request
 *
 * @param collectionName The name of the collection to access
 * @param type The type of request to perform
 * @param comparison The comparison to search with
 * @param data The data to use in the request
 * */
public data class DatabaseRequest(
    val collectionName: String,
    val comparison: Comparison,
    val type: RequestType,
    val data: JsonObject? = null
)

/**
 * Represents different types of requests
 * @property FETCH Fetches an object, returns null if not exists
 * @property UPDATE Updates an object with [DatabaseRequest.data], returns self if succeeds, returns null if not exists
 * @property CREATE Creates an object with [DatabaseRequest.data], returns self if succeeds, returns null if exists
 * @property DROP Drops an object, returns self if succeeds, returns null if not exists
 * @property FETCH_OR_CREATE Fetches an object, returns self, create with [DatabaseRequest.data] if not exists
 * @property UPDATE_OR_CREATE Updates an object with [DatabaseRequest.data], returns self, create with [DatabaseRequest.data] if not exists
 * @property DROP_IF_EXISTS Drops an object, returns self
 * */
public enum class RequestType {
    FETCH,
    UPDATE,
    CREATE,
    DROP,
    FETCH_OR_CREATE,
    UPDATE_OR_CREATE,
    DROP_IF_EXISTS
}
