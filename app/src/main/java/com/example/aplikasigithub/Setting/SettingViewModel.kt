package com.example.aplikasigithub.Setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.aplikasigithub.DataLocal.Setting
import kotlinx.coroutines.launch

class SettingViewModel (private val pref : Setting) : ViewModel() {

    fun getTheme() = pref.getThemeSettings().asLiveData()

    fun saveTheme(isDark : Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDark)
        }
    }

    class Factory(private val pref : Setting) :ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingViewModel(pref) as T
    }
}