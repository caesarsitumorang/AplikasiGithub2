package com.example.aplikasigithub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithub.DataLocal.Setting
import com.example.aplikasigithub.Detail.DetailActivity
import com.example.aplikasigithub.Favorite.FavoriteActivity
import com.example.aplikasigithub.Model.ItemsItem
import com.example.aplikasigithub.Result.Result
import com.example.aplikasigithub.Setting.SettingActivity
import com.example.aplikasigithub.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter { user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", user)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<MainViewModel>(){
        MainViewModel.Factory(Setting(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.recyclerViewHome.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewHome.setHasFixedSize(true)
        binding.recyclerViewHome.adapter = adapter


        binding.searchViewHome.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.getUser(p0.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })


        viewModel.resultUser.observe(this) {
            when (it) {
                is Result.Success<*> -> {
                    adapter.setData(it.data as MutableList<ItemsItem>)
                }

                is Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    binding.progressBarHome.isVisible = it.isLoading
                }
            }
        }

        viewModel.getUser()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tab_favorite -> {
                Intent(this, FavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.setting -> {
                Intent(this, SettingActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

