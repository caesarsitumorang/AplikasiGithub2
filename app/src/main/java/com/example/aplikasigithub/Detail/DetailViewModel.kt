package com.example.aplikasigithub.Detail

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aplikasigithub.DataLocal.DbModule
import com.example.aplikasigithub.Model.ItemsItem
import com.example.aplikasigithub.Model.Service.ApiClient
import com.example.aplikasigithub.Result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel (private  val db :DbModule): ViewModel() {
    val resultDetailUser = MutableLiveData<Result>()
    val resultFollowersUser = MutableLiveData<Result>()
    val resultFollowingUser = MutableLiveData<Result>()
    val resultSuccessFacorite = MutableLiveData<Boolean>()
    val resultDeleteFavorite = MutableLiveData<Boolean>()

    private  var Favorite = false

    fun setFavorite(item: ItemsItem?) {
        viewModelScope.launch {
            item?.let {
                if (Favorite) {
                    db.userDao.delete(item)
                    resultDeleteFavorite.value=true
                } else {
                    db.userDao.insert(item)
                    resultSuccessFacorite.value= true
                }
            }
            Favorite = !Favorite
        }
    }

fun findFavorite(id: Int,listenFavorite: () -> Unit) {
    viewModelScope.launch {
        val user = db.userDao.findById(id)
        if (user != null) {
            listenFavorite()
        }
    }
}

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            launch(Dispatchers.Main) {
                flow {
                    val response = ApiClient
                        .githubService
                        .getDetailUserGithub(username)
                    emit(response)
                }.onStart {
                    resultDetailUser.value = Result.Loading(true)
                }.onCompletion {
                    resultDetailUser.value = Result.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    it.printStackTrace()
                    resultDetailUser.value = Result.Error(it)
                }.collect {
                    resultDetailUser.value = Result.Success(it)
                }
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            launch(Dispatchers.Main) {
                flow {
                    val response = ApiClient
                        .githubService
                        .getFollowinglUserGithub(username)
                    emit(response)
                }.onStart {
                    resultFollowingUser.value = Result.Loading(true)
                }.onCompletion {
                    resultFollowingUser.value = Result.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    it.printStackTrace()
                    resultFollowingUser.value = Result.Error(it)
                }.collect {
                    resultFollowingUser.value = Result.Success(it)
                }
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            launch(Dispatchers.Main) {
                flow {
                    val response = ApiClient
                        .githubService
                        .getFollowerslUserGithub(username)
                    emit(response)
                }.onStart {
                    resultFollowersUser.value = Result.Loading(true)
                }.onCompletion {
                    resultFollowersUser.value = Result.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    it.printStackTrace()
                    resultFollowersUser.value = Result.Error(it)
                }.collect {
                    resultFollowersUser.value = Result.Success(it)
                }
            }
        }
    }
    class Factory(private val db: DbModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T
    }
}
