package br.com.ams.amsmarmitakm.parse

import br.com.ams.amsmarmitakm.domain.entity.Comedor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.Document

object ComedorParse {
   /* fun toDocument(comedor: Comedor): Document = Document.parse(Json.encodeToString(comedor))

    fun fromDocument(document: Document): Comedor {
        return Comedor(
            document.getObjectId("_id").toString(),
            document.getString("nome"),
            document.getString("foto")
        )
    }*/
}