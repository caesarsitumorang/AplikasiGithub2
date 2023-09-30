package com.example.aplikasigithub.Detail

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.example.aplikasigithub.DataLocal.DbModule
import com.example.aplikasigithub.Detail.Follower_wing.FollowsFragment
import com.example.aplikasigithub.Model.ItemsItem
import com.example.aplikasigithub.Model.ResponseDetailUser
import com.example.aplikasigithub.R
import com.example.aplikasigithub.Result.Result
import com.example.aplikasigithub.databinding.ActivityDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

private lateinit var binding: ActivityDetailBinding
private val viewModel by viewModels<DetailViewModel> {
    DetailViewModel.Factory(DbModule(this))
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getParcelableExtra<ItemsItem>("item")
        val username = item?.login ?: ""


        viewModel.resultDetailUser.observe(this) {
            when(it) {
                is Result.Success<*> -> {
                    val user = it.data as ResponseDetailUser
                    binding.image.load(user.avatarUrl)
                    binding.nama.text = user.name
                    binding.user.text = user.login
                    binding.followers.text = StringBuilder(user.following.toString()).append(" Following")
                    binding.following.text = StringBuilder(user.followers.toString()).append(" Followers")
                    binding.location.text = user.location ?: "-----"
                    val publicReposCount = user.publicRepos ?: 0
                    binding.repository.text = StringBuilder(publicReposCount.toString()).append(" Repository")

                }
                is Result.Error -> {
                    Toast.makeText(this,it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                    binding.progressBar1.isVisible = it.isLoading
                }
            }
        }
        viewModel.getDetailUser(username)
            val fragments = mutableListOf<Fragment>(
                FollowsFragment.newInstance(FollowsFragment.FOLLOWERS),
                FollowsFragment.newInstance(FollowsFragment.FOLLOWING)
            )

        val titleFragments  = mutableListOf<String>(
            getString(R.string.followers), getString(R.string.following)
        )
        val adapter = DetailAdapter(this,fragments)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tab, binding.viewpager) {tab, posisi ->
            tab.text = titleFragments[posisi]
        }.attach()

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.getFollowers(username)
                    1 -> viewModel.getFollowing(username)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
         viewModel.getFollowers(username)

        viewModel.resultSuccessFacorite.observe(this) {
            binding.btnfavorite.changeIconColor(R.color.merahtua)
        }

        viewModel.resultDeleteFavorite.observe(this) {
            binding.btnfavorite.changeIconColor(R.color.white)
        }

        binding.btnfavorite.setOnClickListener{
            viewModel.setFavorite(item)
        }

      viewModel.findFavorite(item?.id?:0) {
            binding.btnfavorite.changeIconColor(R.color.merahtua)
      }
    }
 fun FloatingActionButton.changeIconColor(@ColorRes color : Int) {
     imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context,color))
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