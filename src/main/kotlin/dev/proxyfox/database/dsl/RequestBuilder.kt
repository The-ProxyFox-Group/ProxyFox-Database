package dev.proxyfox.database.dsl

import dev.proxyfox.database.*
import dev.proxyfox.database.comparison.Comparison
import kotlinx.serialization.json.*
import kotlin.reflect.KClass

/**
 * A DSL for building requests.
 *
 * Example:
 * ```kt
 * buildRequest(SomeClass::class, RequestType.FETCH_OR_CREATE) {
 *     +("nya" eq "uwu")
 *
 *     data(SomeClass())
 * }
 * ```
 * */
public class RequestBuilder(
    private val collectionName: String,
    private val type: RequestType,
    public val json: Json = defaultJson
) {

    private var data: JsonObject? = null
    private val comparisons: ArrayList<Comparison> = arrayListOf()

    public fun data(obj: JsonObject) {
        this.data = obj
    }

    public inline fun <reified T> data(t: T) {
        data(json.encodeToJsonElement(t).jsonObject)
    }

    internal fun build(): DatabaseRequest = TODO()
}

public typealias RequestBuilderAction = RequestBuilder.() -> Unit

/**
 * Builds a request using the [RequestBuilder] DSL
 * @param collectionName The name of the collection
 * @param action The DSL action
 * */
public fun buildRequest(
    collectionName: String,
    type: RequestType,
    json: Json = defaultJson,
    action: RequestBuilderAction
): DatabaseRequest = RequestBuilder(collectionName, type, json).apply(action).build()

/**
 * Builds a request using the [RequestBuilder] DSL
 *
 * [collectionType] is automatically normalized to make the first character lowercase.
 * Ex: SomeClass -> someClass
 * @param collectionType The type of the collection to get
 * @param action The DSL action
 *
 * @see buildRequest
 * */
public fun <T : Any> buildRequest(
    collectionType: KClass<T>,
    type: RequestType,
    json: Json = defaultJson,
    action: RequestBuilderAction
): DatabaseRequest = buildRequest(
    collectionType.simpleName!!.replaceFirstChar { it.lowercase() },
    type,
    json,
    action
)
