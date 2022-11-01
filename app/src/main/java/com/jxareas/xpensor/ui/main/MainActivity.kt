package com.jxareas.xpensor.ui.main

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jxareas.xpensor.NavGraphDirections
import com.jxareas.xpensor.R
import com.jxareas.xpensor.databinding.ActivityMainBinding
import com.jxareas.xpensor.ui.main.event.MainActivityEvent
import com.jxareas.xpensor.utils.DateUtils.getCurrentLocalDate
import com.jxareas.xpensor.utils.getLong
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

    private val topLevelDestinationIds: Set<Int>
        get() = setOf(R.id.converterFragment,
            R.id.accountsFragment,
            R.id.transactionsFragment,
            R.id.dateSelectorDialogFragment,
            R.id.selectCategoryBottomSheet,
            R.id.addTransactionBottomSheet,
            R.id.chartFragment)

    private companion object {
        const val SPLASH_ICON_VIEW_ANIMATION_SCALE = 0.2f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            val animationDuration = resources.getLong(R.integer.xpensor_splash_screen_duration)
            animateSplashScreen(splashScreenViewProvider, animationDuration)
        }

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

    private fun animateSplashScreen(
        provider: SplashScreenViewProvider,
        animationDuration: Long,
    ) = provider.iconView.animate().let { iconViewPropertyAnimator ->

        val onSplashScreenRemovedAnimator = ObjectAnimator.ofFloat(
            provider.view,
            View.TRANSLATION_Y,
            0f,
            -provider.view.height.toFloat(),
        ).apply {
            interpolator = AnticipateOvershootInterpolator()
            duration = animationDuration
            doOnEnd { provider.remove() }
        }

        val reverseIconAnimation: () -> Unit = {
            iconViewPropertyAnimator
                .setDuration(animationDuration)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .rotation(-2 * animationDuration.toFloat())
                .scaleXBy(-SPLASH_ICON_VIEW_ANIMATION_SCALE)
                .scaleYBy(-SPLASH_ICON_VIEW_ANIMATION_SCALE)
                .withStartAction { onSplashScreenRemovedAnimator.start() }
        }

        iconViewPropertyAnimator
            .setDuration(animationDuration)
            .setInterpolator(AnticipateOvershootInterpolator())
            .rotation(2 * animationDuration.toFloat())
            .scaleXBy(SPLASH_ICON_VIEW_ANIMATION_SCALE)
            .scaleYBy(SPLASH_ICON_VIEW_ANIMATION_SCALE)
            .withEndAction(reverseIconAnimation)
            .start()

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
            }
        }
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