package com.example.aplikasigithub.DataLocal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aplikasigithub.Model.ItemsItem
import java.sql.RowId

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: ItemsItem)

    @Query("SELECT * FROM user")
    fun loadAll() :LiveData<MutableList<ItemsItem>>

   @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    fun findById(id : Int) :ItemsItem

    @Delete
    fun delete (user : ItemsItem)
}