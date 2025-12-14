package org.xmis.bunny.domain.usecase.password

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.xmis.bunny.data.repositories.password.PasswordRepository
import org.xmis.bunny.presentation.models.PasswordExtended
import xmis.bunny.krypto.Krypto
import xmis.bunny.krypto.encrypt
import kotlin.getValue

class UpdatePasswordUseCase: KoinComponent {
    private val passwordRepository: PasswordRepository by inject()
    private val krypto: Krypto by inject()

    suspend fun execute(passwordData: PasswordExtended) {
        val passwordResult: String = encrypt(passwordData.password)
        passwordRepository.updatePassword(
            passwordData.copy(title = passwordData.title,
                id = passwordData.id,
                description = passwordData.description,
                password = passwordResult)
        )
    }
}