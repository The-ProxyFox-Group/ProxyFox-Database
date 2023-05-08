package dev.proxyfox.database.drivers

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import dev.proxyfox.database.*
import dev.proxyfox.database.comparison.Comparison
import dev.proxyfox.database.defaultJson
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.bson.conversions.Bson
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

public class MongoDriver(
    databaseName: String,
    private val json: Json = defaultJson,
    connectionString: String?
): DatabaseDriver {
    private val client: MongoClient = connectionString?.let { KMongo.createClient(it) } ?: KMongo.createClient()
    private val db: MongoDatabase = client.getDatabase(databaseName)

    private val collections: HashMap<String, MongoCollection<JsonObject>> = hashMapOf()

    private fun getCollection(name: String): MongoCollection<JsonObject> {
        if (collections.containsKey(name)) return collections[name]!!
        val collection = db.getCollection<JsonObject>(name)
        collections[name] = collection
        return collection
    }

    private val Comparison.bson: Bson get() {
        TODO()
    }

    override suspend fun handleRequest(request: DatabaseRequest): JsonObject? {
        val collection = getCollection(request.collectionName)
        return when (request.type) {
            RequestType.FETCH -> TODO()
            RequestType.UPDATE -> TODO()
            RequestType.CREATE -> TODO()
            RequestType.DROP -> TODO()
            RequestType.FETCH_OR_CREATE -> TODO()
            RequestType.UPDATE_OR_CREATE -> TODO()
            RequestType.DROP_IF_EXISTS -> TODO()
        }
    }
}
