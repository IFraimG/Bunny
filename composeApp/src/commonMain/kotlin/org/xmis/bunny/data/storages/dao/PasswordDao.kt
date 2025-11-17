package org.xmis.bunny.data.storages.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.xmis.bunny.data.storages.entities.PasswordEntity

@Dao
interface PasswordDao {
    @Insert
    suspend fun insert(item: PasswordEntity)

    @Query("SELECT count(*) FROM PasswordEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM PasswordEntity")
    fun getPasswords(): Flow<List<PasswordEntity>>

    @Query("DELETE FROM PasswordEntity WHERE password = :password AND title = :title")
    fun deletePassword(title: String, password: String)

    @Query("DELETE FROM PasswordEntity")
    suspend fun destroy()
}
