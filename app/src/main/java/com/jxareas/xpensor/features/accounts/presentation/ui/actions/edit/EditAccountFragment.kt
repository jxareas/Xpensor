package com.jxareas.xpensor.features.accounts.presentation.ui.actions.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialSharedAxis
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.extensions.getLong
import com.jxareas.xpensor.common.extensions.navigateUpWithNavController
import com.jxareas.xpensor.common.extensions.setMenuOnActivity
import com.jxareas.xpensor.common.extensions.setTint
import com.jxareas.xpensor.common.extensions.showToast
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.databinding.FragmentEditAccountBinding
import com.jxareas.xpensor.features.accounts.presentation.ui.actions.menu.ApplyChangesMenu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditAccountFragment : Fragment() {

    private var _binding: FragmentEditAccountBinding? = null
    private val binding: FragmentEditAccountBinding
        get() = _binding!!

    private val args by navArgs<EditAccountFragmentArgs>()
    private val viewModel: EditAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = resources.getLong(R.integer.material_motion_duration_medium_2)
        }
        returnTransition = MaterialFade().apply {
            interpolator = FastOutSlowInInterpolator()
        }
        exitTransition = MaterialFade().apply {
            interpolator = FastOutSlowInInterpolator()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupView()
        setupListeners()
        setupEventCollector()
    }

    private fun setupEventCollector() {
        val account = args.editableAccount
        var color = account.color

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventSource
                .flowWithLifecycle(lifecycle)
                .collectLatest { editAccountUiEvent ->
                    when (editAccountUiEvent) {
                        is EditAccountUiEvent.UpdateAccount -> {
                            val name = binding.textInputLayoutName.editText?.text.toString().trim()
                            if (name.isEmpty()) {
                                showToast(R.string.account_empty_name_error)
                            } else {
                                val amount =
                                    binding.textInputLayoutMoneyAmount.editText?.text.toString()
                                        .toDoubleOrNull() ?: account.amount

                                val newAccount =
                                    account.copy(name = name, amount = amount, color = color)

                                viewModel.updateAccount(newAccount)
                                navigateUpWithNavController()
                            }
                        }
                        is EditAccountUiEvent.UpdateCurrentColor -> {
                            binding.selectedColor.setTint(editAccountUiEvent.color)
                            color = editAccountUiEvent.color
                        }
                    }
                }
        }
    }

    private fun setupView() {
        val account = args.editableAccount

        binding.run {
            textInputLayoutName.editText
                ?.setText(account.name)
            textInputLayoutMoneyAmount.editText
                ?.setText(account.amount.toAmountFormat(withMinus = false))
            selectedColor.setTint(account.color)
        }
    }

    private fun setupMenu() = setMenuOnActivity {
        ApplyChangesMenu(viewModel::onConfirmAccountEditionClick)
    }

    private fun setupListeners() = binding.run {
        color0.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color1.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color2.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color3.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color4.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color5.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color6.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color7.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color8.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color9.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color10.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color11.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color12.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color13.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color14.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
        color15.setOnClickListener { viewModel.onSelectColorButtonClick(it as ImageView) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
