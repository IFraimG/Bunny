package org.xmis.bunny.domain.di

import org.koin.dsl.module
import xmis.bunny.AppLogger.AppLogger

val AppLoggerModule = module {
    factory { AppLogger }

}