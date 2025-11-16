package org.xmis.bunny.data.storages.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val password: String,
    val description: String?
)