package com.jxareas.xpensor.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jxareas.xpensor.NavGraphDirections
import com.jxareas.xpensor.R
import com.jxareas.xpensor.databinding.ActivityMainBinding
import com.jxareas.xpensor.ui.main.event.MainActivityEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

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
        setupListeners()
        setupEventCollector()
    }

    private fun setupListeners() = binding.run {
        buttonSettings.setOnClickListener { viewModel.onSettingsButtonClick() }
        toolbarInfoBox.setOnClickListener {
            viewModel.onSelectAccountButtonClick()
        }
    }

    private fun setupEventCollector() {
        lifecycleScope.launchWhenStarted {
            viewModel.events.collectLatest { event ->
                when (event) {
                    is MainActivityEvent.OpenTheSettingsScreen ->
                        navController.navigate(NavGraphDirections.actionGlobalSettingsActivity())
                    is MainActivityEvent.OpenTheSelectAccountDialog -> {
                        // TODO: Handle open the select account dialog fragment}
                    }
                }


            }
        }
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