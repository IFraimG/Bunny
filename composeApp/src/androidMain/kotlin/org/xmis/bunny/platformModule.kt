package xmis.bunny.di

import org.koin.dsl.module
import org.xmis.bunny.data.storages.databases.PasswordDatabase
import org.xmis.bunny.getPasswordDatabase

actual fun platformModule() = module {
    single<PasswordDatabase> { getPasswordDatabase(get()) }
}