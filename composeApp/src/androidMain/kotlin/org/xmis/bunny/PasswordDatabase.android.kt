package org.xmis.bunny

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.xmis.bunny.data.storages.databases.PasswordDatabase


fun getPasswordDatabase(ctx: Context): PasswordDatabase {
    val dbFile = ctx.getDatabasePath("password_database.db")
    return Room.databaseBuilder<PasswordDatabase>(ctx, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}