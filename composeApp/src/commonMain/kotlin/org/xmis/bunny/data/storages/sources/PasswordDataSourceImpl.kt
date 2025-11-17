package org.xmis.bunny.data.storages.sources

import kotlinx.coroutines.flow.Flow
import org.xmis.bunny.data.storages.databases.PasswordDatabase
import org.xmis.bunny.data.storages.entities.PasswordEntity
import org.xmis.bunny.presentation.models.PasswordData

class PasswordDataSourceImpl(private val passwordDatabase: PasswordDatabase): PasswordDataSource {
    override suspend fun insert(item: PasswordData) {
        passwordDatabase.getDao().insert(PasswordEntity(
            title = item.title,
            password = item.password, description = item.description)
        )
    }

    override suspend fun getPasswords(): Flow<List<PasswordEntity>> {
        return passwordDatabase.getDao().getPasswords()
    }

    override suspend fun count(): Int = passwordDatabase.getDao().count()

    override suspend fun deletePassword(item: PasswordEntity) {
        passwordDatabase.getDao().deletePassword(item.id)
    }

    override suspend fun destroy() {
        passwordDatabase.getDao().destroy()
    }
}