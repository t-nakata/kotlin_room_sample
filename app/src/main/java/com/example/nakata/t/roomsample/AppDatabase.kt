package com.example.nakata.t.roomsample

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(
        entities = [User::class],
        version = 1,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}