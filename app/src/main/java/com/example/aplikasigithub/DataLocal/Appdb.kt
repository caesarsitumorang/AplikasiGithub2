package com.example.aplikasigithub.DataLocal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aplikasigithub.Model.ItemsItem

@Database(entities =  [ItemsItem::class], version = 1, exportSchema = false)
abstract class Appdb : RoomDatabase(){
    abstract fun userDao() : UserDao
}