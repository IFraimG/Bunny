package org.xmis.bunny.domain.usecase.password

import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.xmis.bunny.data.repositories.password.PasswordRepository
import org.xmis.bunny.data.storages.entities.PasswordEntity

class GetPasswordsUseCase: KoinComponent {
    private val passwordRepository: PasswordRepository by inject()

    suspend fun execute(): Flow<List<PasswordEntity>> = passwordRepository.getPasswords()
}