package com.jxareas.xpensor.core.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jxareas.xpensor.NavGraphDirections
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.utils.DateUtils.getCurrentLocalDate
import com.jxareas.xpensor.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding private set

    private val viewModel: MainActivityViewModel by viewModels()

    private val navController: NavController by lazy {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navHost.navController
    }

    private val nonFilterableFragmentIds: Set<Int>
        get() = setOf(R.id.accountsFragment, R.id.converterFragment)

    private val topLevelDestinationIds: Set<Int>
        get() = setOf(
            R.id.converterFragment,
            R.id.accountsFragment,
            R.id.transactionsFragment,
            R.id.dateSelectorDialogFragment,
            R.id.accountFilterDialogFragment,
            R.id.selectCategoryBottomSheet,
            R.id.addTransactionBottomSheet,
            R.id.chartFragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupNavigation()
        setupListeners()
        setupCollectors()
        setupEventCollector()
    }

    private fun setupCollectors() {
        lifecycleScope.launchWhenStarted {
            viewModel.selectedAccount.collectLatest { currentAccount ->
                binding.toolbarTitle.text = currentAccount?.name ?: getString(R.string.all_accounts)
            }
        }
        val datePattern = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        lifecycleScope.launchWhenStarted {
            viewModel.selectedDateRange.collectLatest { currentDateRange ->
                binding.toolbarSubtitle.text =
                    if (currentDateRange.first == null && currentDateRange.second == null)
                        getString(R.string.all_time)
                    else if (currentDateRange.second == null)
                        "${currentDateRange.first?.format(datePattern)} - ${
                        getCurrentLocalDate().format(datePattern)
                        }"
                    else
                        currentDateRange.first?.format(datePattern)
            }
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupListeners() = binding.run {
        buttonSettings.setOnClickListener { viewModel.onSettingsButtonClick() }
        toolbarInfoBox.setOnClickListener { viewModel.onSelectAccountButtonClick() }
    }

    private fun setupEventCollector() {
        lifecycleScope.launchWhenStarted {
            viewModel.events.collectLatest { event ->
                when (event) {
                    is MainActivityEvent.OpenTheSettingsScreen ->
                        navController.navigate(NavGraphDirections.actionGlobalSettingsActivity())
                    is MainActivityEvent.OpenTheSelectAccountDialog ->
                        navController.navigate(NavGraphDirections.actionGlobalAccountFilterDialog())
                }
            }
        }
    }

    private fun setupNavigation() {
        binding.bottomNavigation.setupWithNavController(navController)
        setupActionBarWithNavController(navController, AppBarConfiguration(topLevelDestinationIds))

        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, currentDestination, _ ->

                val isFragmentWithoutBars = when (currentDestination.id) {
                    R.id.addAccountFragment -> true
                    R.id.editAccountFragment -> true
                    else -> false
                }

                changeNavigationVisibility(if (isFragmentWithoutBars) View.GONE else View.VISIBLE)
                supportActionBar?.setDisplayShowTitleEnabled(isFragmentWithoutBars)

                if (currentDestination.id in topLevelDestinationIds)
                    binding.bottomNavigation.visibility = View.VISIBLE
                else binding.bottomNavigation.visibility = View.GONE
                setupFilters(currentDestination.id)
            }
        }
    }

    private fun setupFilters(destinationId: Int) = binding.run {
        val isNonFilterableFragment = destinationId in nonFilterableFragmentIds
        moreButton.visibility = if (isNonFilterableFragment) View.GONE else View.VISIBLE
        toolbarInfoBox.isEnabled = !isNonFilterableFragment
    }

    private fun changeNavigationVisibility(visibility: Int) {
        with(binding) {
            bottomNavigation.visibility = visibility
            buttonSettings.visibility = visibility
            toolbarInfoBox.visibility = visibility
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()
}
