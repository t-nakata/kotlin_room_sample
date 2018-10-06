package com.example.nakata.t.roomsample

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
class User {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    var age: Int = 0
}
