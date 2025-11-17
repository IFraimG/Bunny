package org.xmis.bunny.data.repositories.password

import kotlinx.coroutines.flow.Flow
import org.xmis.bunny.data.storages.databases.PasswordDatabase
import org.xmis.bunny.data.storages.entities.PasswordEntity
import org.xmis.bunny.data.storages.sources.PasswordDataSource
import org.xmis.bunny.presentation.models.PasswordData

class PasswordRepositoryImpl(
    private val source: PasswordDataSource
): PasswordRepository {
    override suspend fun insertPassword(passwordData: PasswordData) {
        source.insert(passwordData)
    }

    override suspend fun getPasswords(): Flow<List<PasswordEntity>> {
        return source.getPasswords()
    }

    override suspend fun deletePassword(item: PasswordEntity) {
        return source.deletePassword(item)
    }
}