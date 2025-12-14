package org.xmis.bunny.data.repositories.password

import kotlinx.coroutines.flow.Flow
import org.xmis.bunny.data.storages.entities.PasswordEntity
import org.xmis.bunny.presentation.models.PasswordData
import org.xmis.bunny.presentation.models.PasswordExtended

interface PasswordRepository {
    suspend fun insertPassword(passwordData: PasswordData)

    suspend fun getPasswords(): Flow<List<PasswordEntity>>

    suspend fun deletePassword(item: PasswordEntity)
    suspend fun updatePassword(item: PasswordExtended)
}