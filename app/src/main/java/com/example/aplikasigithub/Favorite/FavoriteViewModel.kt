package com.example.aplikasigithub.Favorite

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasigithub.DataLocal.DbModule

class FavoriteViewModel(private  val  dbModule: DbModule) : ViewModel() {
    fun  getUserFavorite() = dbModule.userDao.loadAll()
    class Factory(private  val db:DbModule) : ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T
    }
}