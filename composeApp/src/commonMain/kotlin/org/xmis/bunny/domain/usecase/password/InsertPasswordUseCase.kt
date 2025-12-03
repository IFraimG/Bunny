package org.xmis.bunny.domain.usecase.password

import okio.ByteString.Companion.toByteString
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.xmis.bunny.data.repositories.password.PasswordRepository
import org.xmis.bunny.presentation.models.PasswordData
import xmis.bunny.krypto.Krypto
import xmis.bunny.krypto.encrypt


class InsertPasswordUseCase: KoinComponent {
    private val passwordRepository: PasswordRepository by inject()
    private val krypto: Krypto by inject()

    suspend fun execute(passwordData: PasswordData) {
        val passwordResult: String = encrypt(passwordData.password)
        passwordRepository.insertPassword(
            passwordData.copy(title = passwordData.title,
                description = passwordData.description,
                password = passwordResult)
        )
    }
}