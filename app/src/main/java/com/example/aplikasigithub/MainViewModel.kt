package com.example.aplikasigithub

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.aplikasigithub.DataLocal.Setting
import com.example.aplikasigithub.Model.Service.ApiClient
import com.example.aplikasigithub.Result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel (private val preferences: Setting): ViewModel(){

    val resultUser = MutableLiveData<Result>()

    fun getTheme() = preferences.getThemeSettings().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getUser() {
        viewModelScope.launch {
            launch (Dispatchers.Main){
                flow {
                    val response = ApiClient
                        .githubService
                        .getUserGithub()
                    emit(response)
                }.onStart {
                    resultUser.value = Result.Loading(true)
                }.onCompletion {
                    resultUser.value = Result.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    it.printStackTrace()
                    resultUser.value = Result.Error(it)
                }.collect {
                    resultUser.value = Result.Success(it)
                }
            }

        }
    }
    fun getUser(username : String) {
        viewModelScope.launch {
            launch (Dispatchers.Main){
                flow {
                    val response = ApiClient
                        .githubService
                        .SearchlUserGithub(mapOf(
                            "q" to username,)
                        )
                    emit(response)
                }.onStart {
                    resultUser.value = Result.Loading(true)
                }.onCompletion {
                    resultUser.value = Result.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    it.printStackTrace()
                    resultUser.value = Result.Error(it)
                }.collect {
                    resultUser.value = Result.Success(it.items)
                }
            }
        }
    }
    class Factory(private val preferences: Setting) :ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
    }
}