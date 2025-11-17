package org.xmis.bunny.domain.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.xmis.bunny.data.repositories.password.PasswordRepository
import org.xmis.bunny.data.repositories.password.PasswordRepositoryImpl
import org.xmis.bunny.data.storages.sources.PasswordDataSource
import org.xmis.bunny.data.storages.sources.PasswordDataSourceImpl
import org.xmis.bunny.domain.usecase.password.DeletePasswordUseCase
import org.xmis.bunny.domain.usecase.password.GetPasswordsUseCase
import org.xmis.bunny.domain.usecase.password.InsertPasswordUseCase
import org.xmis.bunny.getPlatform
import org.xmis.bunny.presentation.ui.password.PasswordViewModel

val passwordModule = module {
    singleOf(::PasswordDataSourceImpl).bind(PasswordDataSource::class)

    singleOf(::PasswordRepositoryImpl) { bind<PasswordRepository>() }

    // use-cases
    singleOf(::DeletePasswordUseCase)
    singleOf(::InsertPasswordUseCase)
    singleOf(::GetPasswordsUseCase)

    viewModelOf(::PasswordViewModel)
    factory { getPlatform(this) }
}