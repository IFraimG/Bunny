package xmis.bunny.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.xmis.bunny.data.storages.databases.PasswordDatabase
import org.xmis.bunny.getPasswordDatabase
import org.xmis.bunny.platform.PlatformContext
import org.xmis.bunny.platform.PlatformContextAndroid


actual fun platformModule() = module {
    single<PasswordDatabase> { getPasswordDatabase(get()) }
    single<PlatformContext> { PlatformContextAndroid(get()) }
}