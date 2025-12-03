package org.xmis.bunny.domain.usecase.password

import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.xmis.bunny.data.repositories.password.PasswordRepository
import org.xmis.bunny.data.storages.entities.PasswordEntity
import org.xmis.bunny.presentation.models.PasswordExtended

class DeletePasswordUseCase: KoinComponent {
    private val passwordRepository: PasswordRepository by inject()

    suspend fun execute(item: PasswordExtended) {
        passwordRepository.deletePassword(PasswordEntity(
            title = item.title,
            password = item.password,
            id = item.id,
            description = item.description
        ))
    }
}