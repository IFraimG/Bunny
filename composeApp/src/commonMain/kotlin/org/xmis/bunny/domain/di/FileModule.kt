package org.xmis.bunny.domain.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.xmis.bunny.domain.usecase.files.GetFilesListUseCase
import org.xmis.bunny.presentation.ui.files.FilesListViewModel


val FileModule = module {
    singleOf(::GetFilesListUseCase)

    viewModelOf(::FilesListViewModel)
}