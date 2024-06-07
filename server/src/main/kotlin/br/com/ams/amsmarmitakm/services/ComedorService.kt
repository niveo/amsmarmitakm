package br.com.ams.amsmarmitakm.services

import br.com.ams.amsmarmitakm.domain.entity.Comedor
import br.com.ams.amsmarmitakm.repositories.ComedoreRepository

class ComedorService(private val comedoreRepository: ComedoreRepository) {

    suspend fun create(comedor: Comedor): String? {
        return comedoreRepository.create(comedor)
    }

    suspend fun read(id: String): Comedor? {
        return comedoreRepository.read(id)
    }

    suspend fun readAll(): List<Comedor>? {
        return comedoreRepository.readAll()
    }

    suspend fun update(id: String, comedor: Comedor): Long {
        return comedoreRepository.update(id, comedor)
    }

    suspend fun delete(id: String): Long {
        return comedoreRepository.delete(id)
    }
}