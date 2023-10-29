package com.gumsiz.shared

import com.gumsiz.shared.data.Repository
import com.gumsiz.shared.di.sharedModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class RepositoryHelper : KoinComponent {
    private val repository : Repository by inject()

    fun getRepo() : Repository = repository
}
fun initKoin(){
    startKoin {
        modules(sharedModule())
    }
}