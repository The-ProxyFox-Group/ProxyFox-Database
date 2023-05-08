import com.mongodb.client.MongoDatabase
import dev.proxyfox.database.RequestType
import dev.proxyfox.database.dsl.buildRequest
import org.litote.kmongo.KMongo

val client = KMongo.createClient()
val db: MongoDatabase = client.getDatabase("owo")

suspend fun main() {

    buildRequest("owo", RequestType.FETCH) {
        + ("owo" eq ("nya" eq true))
    }
}
