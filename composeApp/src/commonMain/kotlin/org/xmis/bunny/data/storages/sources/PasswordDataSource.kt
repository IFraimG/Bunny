package org.xmis.bunny.data.storages.sources

import kotlinx.coroutines.flow.Flow
import org.xmis.bunny.data.storages.entities.PasswordEntity
import org.xmis.bunny.presentation.models.PasswordData

interface PasswordDataSource {
    suspend fun insert(item: PasswordData)

    suspend fun count(): Int

    suspend fun getPasswords(): Flow<List<PasswordEntity>>

    suspend fun deletePassword(item: PasswordEntity)

    suspend fun destroy()
}