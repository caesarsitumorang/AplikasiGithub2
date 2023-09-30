package com.example.aplikasigithub.DataLocal

import android.content.Context
import androidx.room.Room

class DbModule (private val context: Context) {
    private val db = Room.databaseBuilder(context, Appdb::class.java, "usergithub.db")
        .allowMainThreadQueries()
        .build()

    val userDao = db.userDao()
}