package org.xmis.bunny.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.xmis.bunny.domain.di.passwordModule
import xmis.bunny.di.platformModule


fun initKoin(config : KoinAppDeclaration? = null){
    startKoin {
        config?.invoke(this)
        modules(platformModule(), passwordModule)
    }
}