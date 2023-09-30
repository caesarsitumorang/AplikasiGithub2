package com.example.aplikasigithub.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.aplikasigithub.DataLocal.Setting
import com.example.aplikasigithub.R
import com.example.aplikasigithub.databinding.ActivitySettingBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val viewModel by viewModels<SettingViewModel> {
        SettingViewModel.Factory(Setting(this))
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getTheme().observe(this) {
            val isChecked = it
            if (isChecked) {
                binding.thema.text = "Dark Theme"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                binding.thema.text = "Light Theme"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.thema.isChecked = isChecked
        }

        binding.thema.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTheme(isChecked)
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