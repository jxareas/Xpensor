package com.jxareas.xpensor.core.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jxareas.xpensor.R
import com.jxareas.xpensor.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navController: NavController by lazy {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navHost.navController
    }

    private val topLevelDestinationIds: Set<Int>
        get() = setOf(R.id.converterFragment,
            R.id.accountsFragment,
            R.id.transactionsFragment,
            R.id.chartFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.bottomNavigation.setupWithNavController(navController)

        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, navDestination, _ ->
                if (navDestination.id in topLevelDestinationIds)
                    binding.bottomNavigation.visibility = View.VISIBLE
                else binding.bottomNavigation.visibility = View.GONE
            }
        }
    }
}