package br.com.ams.amsmarmitakm.request

import br.com.ams.amsmarmitakm.domain.entity.Comedor
import kotlinx.serialization.Serializable

@Serializable
data class ComedorRequest(
    val nome: String,
    val foto: String? = null
)

fun ComedorRequest.toDomain(): Comedor {
    return Comedor(nome = nome, foto = foto)
}