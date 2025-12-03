package org.xmis.bunny.domain.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.xmis.bunny.getPlatform
import xmis.bunny.AppLogger.AppLogger

val AppLoggerModule = module {
    factory { AppLogger }

}