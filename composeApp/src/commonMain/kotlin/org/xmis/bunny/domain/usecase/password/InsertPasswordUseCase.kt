package org.xmis.bunny.domain.usecase.password

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.xmis.bunny.data.repositories.password.PasswordRepository
import org.xmis.bunny.presentation.models.PasswordData

class InsertPasswordUseCase: KoinComponent {
    private val passwordRepository: PasswordRepository by inject()

    suspend fun execute(passwordData: PasswordData) {
        passwordRepository.insertPassword(passwordData)
    }
}