package com.example.nakata.t.roomsample

import android.arch.persistence.room.*


@Dao
interface UserDao {
    @Query("select * from user")
    fun findAll(): List<User>

    @Query("select * from user where id = :id")
    fun findById(id: Int): User?

    @Query("select * from user limit 1")
    fun findFirest(): User?

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    fun insert(user: User)

    @Query("UPDATE user SET name = :name, age = :age WHERE id = :id")
    fun update(id: Int, name: String, age: Int)

    @Delete
    fun delete(user: User)
}