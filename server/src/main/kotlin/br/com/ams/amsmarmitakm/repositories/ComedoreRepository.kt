package br.com.ams.amsmarmitakm.repositories

import br.com.ams.amsmarmitakm.domain.entity.Comedor


interface ComedoreRepository {
    suspend fun create(comedor: Comedor): String?
    suspend fun read(id: String): Comedor?
    suspend fun readAll(): List<Comedor>?
    suspend fun update(id: String, comedor: Comedor): Long
    suspend fun delete(id: String): Long
}