package com.jxareas.xpensor.features.accounts.presentation.ui.actions

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.core.presentation.MainActivityViewModel
import com.jxareas.xpensor.databinding.BottomSheetAccountActionsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AccountActionsBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetAccountActionsBinding? = null
    private val binding: BottomSheetAccountActionsBinding
        get() = _binding!!

    private val viewModel: AccountActionsViewModel by viewModels()
    private val mainViewModel: MainActivityViewModel by activityViewModels()

    private val args by navArgs<AccountActionsBottomSheetArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetAccountActionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewAppearance()
        setupListeners()
        setupEventCollector()
    }

    private fun setupListeners() = binding.run {
        editButton.setOnClickListener { viewModel.onEditAccount(args.selectedAccount) }
        deleteButton.setOnClickListener { viewModel.onDeleteAccount(args.selectedAccount) }
    }

    private fun setupEventCollector() {
        lifecycleScope.launchWhenStarted {
            viewModel.events.collectLatest { event ->
                when (event) {
                    is AccountActionsUiEvent.DeleteAccount -> {
                        if (args.selectedAccount == mainViewModel.selectedAccount.value) {
                            mainViewModel.onUpdateSelectedAccount(null)
                        }
                        viewModel.removeAccount(args.selectedAccount)
                        dismiss()
                    }
                    is AccountActionsUiEvent.ShowDeleteAccountDialog -> {
                        buildAlertDialog().show()
                    }
                    is AccountActionsUiEvent.NavigateToEditAccountsScreen -> {
                        val editAccountDirection =
                            AccountActionsBottomSheetDirections
                                .actionAccountBottomSheetToEditAccount(args.selectedAccount)
                        findNavController().navigate(editAccountDirection)
                    }
                }
            }
        }
    }

    private fun buildAlertDialog() = MaterialAlertDialogBuilder(requireContext())
        .setIcon(R.drawable.ic_warning)
        .setTitle(getString(R.string.delete_account_alert_title))
        .setMessage(getString(R.string.delete_account_alert_message))
        .setPositiveButton(getString(R.string.confirm)) { _, _ ->
            viewModel.onDeleteAccountConfirmation()
        }
        .setNegativeButton(getString(R.string.cancel)) { _, _ ->
            this@AccountActionsBottomSheet.dismiss()
        }
        .setOnCancelListener {
            this@AccountActionsBottomSheet.dismiss()
        }
        .create()

    private fun setupViewAppearance() {
        val account = args.selectedAccount
        binding.run {
            actionsContainer.setBackgroundColor(Color.parseColor(account.color))
            accountName.text = account.name
            accountAmount.text = account.amount.toAmountFormat(withMinus = false)
            accountCurrency.text = mainViewModel.getCurrencyName()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
