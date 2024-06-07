package br.com.ams.amsmarmitakm.response

import kotlinx.serialization.Serializable

@Serializable
data class ComedorResponse(
    val _id: String,
    val nome: String,
    val foto: String? = null
)