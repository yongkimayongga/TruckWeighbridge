package com.truck.weighbridge.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_login")
class LoginToken(
    @PrimaryKey(autoGenerate = false)
    val token: String
)