package com.jxareas.xpensor.features.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jxareas.xpensor.R
import com.jxareas.xpensor.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onStart() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}