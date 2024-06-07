package br.com.ams.amsmarmitakm.di

import br.com.ams.amsmarmitakm.repositories.ComedoreRepository
import br.com.ams.amsmarmitakm.repositories.impl.ComedoreRepositoryImpl
import br.com.ams.amsmarmitakm.services.ComedorService
import org.koin.dsl.module

val comedorModule = module {
    single<ComedoreRepository> {
        ComedoreRepositoryImpl(get())
    }
    single { ComedorService(get()) }
}