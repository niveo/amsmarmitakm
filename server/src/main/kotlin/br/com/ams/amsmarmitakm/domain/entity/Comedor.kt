package br.com.ams.amsmarmitakm.domain.entity

import br.com.ams.amsmarmitakm.response.ComedorResponse
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Comedor(
    @BsonId
    val _id: ObjectId? = null,
    val nome: String,
    val foto: String? = null
) {
    fun toResponse() = ComedorResponse(this._id.toString(), this.nome, this.foto)
}