package com.jxareas.xpensor.features.accounts.presentation.ui.actions.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.extensions.setMenuOnActivity
import com.jxareas.xpensor.common.extensions.setTint
import com.jxareas.xpensor.common.extensions.showToast
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences
import com.jxareas.xpensor.databinding.FragmentAddAccountBinding
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.accounts.presentation.ui.actions.menu.ApplyChangesMenu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddAccountFragment : Fragment() {

    private var _binding: FragmentAddAccountBinding? = null
    private val binding: FragmentAddAccountBinding
        get() = _binding!!

    private val viewModel: AddAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            interpolator = FastOutSlowInInterpolator()
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            interpolator = FastOutSlowInInterpolator()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupListeners()
        setupEventCollector()
    }

    private fun setupEventCollector() {
        var color = UserPreferences.DEFAULT_COLOR
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventSource
                .flowWithLifecycle(lifecycle)
                .collectLatest { addAccountUiEvent ->
                    when (addAccountUiEvent) {
                        is AddAccountUiEvent.CreateNewAccount -> {
                            val name = binding.textInputLayoutName.editText?.text.toString().trim()
                            if (name.isEmpty()) {
                                showToast(R.string.account_empty_name_error)
                            } else {
                                val amount =
                                    binding.textInputLayoutMoneyAmount.editText?.text.toString()
                                        .toDoubleOrNull() ?: 0.0

                                val account =
                                    AccountUi(
                                        name = name,
                                        amount = amount,
                                        color = color,
                                        id = AccountUi.EMPTY_ID,
                                    )
                                viewModel.createNewAccount(account)
                                    .also { findNavController().navigateUp() }
                            }
                        }
                        is AddAccountUiEvent.SelectAccountColor -> {
                            binding.selectedColor.setTint(addAccountUiEvent.color)
                            color = addAccountUiEvent.color
                        }
                    }
                }
        }
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

    private fun setupMenu() = setMenuOnActivity {
        ApplyChangesMenu(viewModel::onConfirmAccountCreationClick)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
