package com.jxareas.xpensor.features.authentication.presentation.ui

import android.animation.ObjectAnimator
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.extensions.getLong
import com.jxareas.xpensor.common.extensions.setTint
import com.jxareas.xpensor.core.presentation.MainActivity
import com.jxareas.xpensor.databinding.ActivityAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding

    private val viewModel: AuthenticationViewModel by viewModels()

    private val vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
    }

    companion object {
        private const val ERROR_TIME = 250L
        private const val SPLASH_ICON_VIEW_ANIMATION_SCALE = 0.2f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setOnExitAnimationListener { splashScreenViewProvider ->
            val animationDuration = resources.getLong(R.integer.xpensor_splash_screen_duration)
            animateSplashScreen(splashScreenViewProvider, animationDuration)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListeners()
        setupCollectors()
        setupEventCollector()
    }

    private fun setupView() = binding.run {
        if (viewModel.isFirstAppLaunch()) {
            textViewEnterPin.text = getString(R.string.set_the_pin)
            buttonForgotCode.isVisible = false
        }
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
                .rotation(-animationDuration.toFloat())
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

    private fun setupEventCollector() = binding.run {
        lifecycleScope.launchWhenStarted {
            viewModel.events.collectLatest {
                when (it) {
                    is AuthenticationUiEvent.ForgotPinCode -> {
                        showAlert(
                            getString(R.string.forgot_alert_title),
                            getString(R.string.forgot_alert_message)
                        ) {
                            showAlert(
                                getString(R.string.erase_data_alert_title),
                                getString(R.string.erase_data_alert_message)
                            ) {
                                viewModel.onEraseDataClick()
                            }
                        }
                    }
                    is AuthenticationUiEvent.OpenMainActivity -> {

                        for (item in listOf(circle1, circle2, circle3, circle4)) {
                            item.setTint(R.color.blue_green)
                        }

                        val intent = Intent(this@AuthenticationActivity, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        finish()
                    }
                    is AuthenticationUiEvent.DeletePinCode -> {
                        lifecycleScope.launch {

                            buttonBackspace.isClickable = false
                            buttonForgotCode.isClickable = false

                            for (item in listOf(circle1, circle2, circle3, circle4)) {
                                item.setTint(R.color.orange_red)
                            }
                            startVibrating()
                            delay(ERROR_TIME)
                            viewModel.clearCode()

                            buttonBackspace.isClickable = true
                            buttonForgotCode.isClickable = true
                        }
                    }
                    is AuthenticationUiEvent.EraseAppData ->
                        (getSystemService(ACTIVITY_SERVICE) as ActivityManager)
                            .clearApplicationUserData()
                    is AuthenticationUiEvent.SetNewPinCode -> {
                        textViewEnterPin.text = getString(R.string.set_the_pin)
                    }
                    is AuthenticationUiEvent.RepeatPinCode -> {
                        textViewEnterPin.text = getString(R.string.repeat_the_pin)
                    }
                }
            }
        }
    }

    private fun setupCollectors() {
        lifecycleScope.launchWhenStarted {
            viewModel.pinCode.collectLatest { newCode ->
                with(binding) {
                    var count = newCode.length
                    for (item in listOf(circle1, circle2, circle3, circle4)) {
                        item.setTint(if (count > 0) R.color.yellow_orange else R.color.light_gray)
                        count--
                    }
                }
            }
        }
    }

    private fun setupListeners() = binding.run {
        number0.setOnClickListener { viewModel.onButtonNumberClick("0") }
        number1.setOnClickListener { viewModel.onButtonNumberClick("1") }
        number2.setOnClickListener { viewModel.onButtonNumberClick("2") }
        number3.setOnClickListener { viewModel.onButtonNumberClick("3") }
        number4.setOnClickListener { viewModel.onButtonNumberClick("4") }
        number5.setOnClickListener { viewModel.onButtonNumberClick("5") }
        number6.setOnClickListener { viewModel.onButtonNumberClick("6") }
        number7.setOnClickListener { viewModel.onButtonNumberClick("7") }
        number8.setOnClickListener { viewModel.onButtonNumberClick("8") }
        number9.setOnClickListener { viewModel.onButtonNumberClick("9") }
        buttonForgotCode.setOnClickListener { viewModel.forgotButtonClick() }
        buttonBackspace.setOnClickListener { viewModel.backspaceButtonClick() }
    }

    private fun startVibrating() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    ERROR_TIME,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            vibrator.vibrate(ERROR_TIME)
        }
    }

    private fun showAlert(title: String, message: String, onPositiveButtonClick: () -> Unit) {
        val alert = AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_warning)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> onPositiveButtonClick() }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            .create()
        alert.show()
    }
}
