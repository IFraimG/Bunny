package org.xmis.bunny.data.storages.databases

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.xmis.bunny.data.storages.dao.PasswordDao
import org.xmis.bunny.data.storages.entities.PasswordEntity

@Database(entities = [PasswordEntity::class], version = 1)
@ConstructedBy(PasswordDatabaseConstructor::class)
abstract class PasswordDatabase : RoomDatabase() {
    abstract fun getDao(): PasswordDao
}

@Suppress("KotlinNoActualForExpect")
expect object PasswordDatabaseConstructor : RoomDatabaseConstructor<PasswordDatabase> {
    override fun initialize(): PasswordDatabase
}
