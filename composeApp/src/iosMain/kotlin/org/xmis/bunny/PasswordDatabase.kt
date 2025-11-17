package org.xmis.bunny

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.xmis.bunny.data.storages.databases.PasswordDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun getPasswordDatabaseBuilder(): RoomDatabase.Builder<PasswordDatabase> {
    val dbFilePath = documentDirectory() + "/password_database.db"
    return Room.databaseBuilder<PasswordDatabase>(
        name = dbFilePath,
    ).setDriver(BundledSQLiteDriver()).setQueryCoroutineContext(Dispatchers.IO)
}

fun getPasswordDatabase(): PasswordDatabase {
    return getPasswordDatabaseBuilder().build()
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}