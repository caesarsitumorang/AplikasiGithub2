package com.example.aplikasigithub.Favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithub.DataLocal.DbModule
import com.example.aplikasigithub.Detail.DetailActivity
import com.example.aplikasigithub.UserAdapter
import com.example.aplikasigithub.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding :ActivityFavoriteBinding
    private val adapter by lazy {
        UserAdapter { user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", user)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.Factory(DbModule(this))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvfavorite.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
        binding.rvfavorite.adapter = adapter

        viewModel.getUserFavorite().observe(this) { userFavorites ->
            adapter.setData(userFavorites)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}