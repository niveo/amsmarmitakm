package br.com.ams.amsmarmitakm.repositories.impl

import br.com.ams.amsmarmitakm.domain.entity.Comedor
import br.com.ams.amsmarmitakm.repositories.ComedoreRepository
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId

class ComedoreRepositoryImpl(database: MongoDatabase) : ComedoreRepository {

    private var collection: MongoCollection<Comedor> =
        database.getCollection<Comedor>("comedores")

    override suspend fun create(comedor: Comedor): String? = withContext(Dispatchers.IO) {
        val rs = collection.insertOne(comedor)
        rs.insertedId?.asObjectId()?.value?.toString()
    }

    override suspend fun read(id: String): Comedor? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("_id", ObjectId(id))).firstOrNull()
    }

    override suspend fun readAll(): List<Comedor> = withContext(Dispatchers.IO) {
        collection.find().toList()
    }

    override suspend fun update(id: String, comedor: Comedor):
            Long = withContext(Dispatchers.IO) {
        val query = Filters.eq("_id", ObjectId(id))
        val updates = Updates.combine(
            Updates.set(Comedor::nome.name, comedor.nome),
            Updates.set(Comedor::foto.name, comedor.foto)
        )
        val options = UpdateOptions().upsert(true)
        collection.updateOne(query, updates, options).matchedCount
    }

    override suspend fun delete(id: String): Long = withContext(Dispatchers.IO) {
        collection.deleteOne(Filters.eq("_id", ObjectId(id))).deletedCount
    }
}