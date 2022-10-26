package com.jxareas.xpensor.ui.accounts.actions

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
import com.jxareas.xpensor.databinding.FragmentAccountActionsBottomSheetBinding
import com.jxareas.xpensor.ui.accounts.actions.events.AccountActionsEvent
import com.jxareas.xpensor.ui.main.MainActivityViewModel
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AccountActionsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAccountActionsBottomSheetBinding? = null
    private val binding: FragmentAccountActionsBottomSheetBinding
        get() = _binding!!

    private val viewModel: AccountActionsViewModel by viewModels()
    private val mainViewModel: MainActivityViewModel by activityViewModels()

    private val args by navArgs<AccountActionsBottomSheetFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAccountActionsBottomSheetBinding.inflate(inflater, container, false)
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
    }

    private fun setupEventCollector() {
        lifecycleScope.launchWhenStarted {
            viewModel.events.collectLatest { event ->
                when (event) {
                    is AccountActionsEvent.DeleteAccount -> {
                        // TODO : Add DeleteAccount Event Handler
                    }
                    is AccountActionsEvent.ShowDeleteAccountDialog -> {
                        // TODO : Add ShowDeleteAccountDialog Event Handler
                    }
                    is AccountActionsEvent.NavigateToEditAccountsScreen -> {
                        val editAccountDirection =
                            AccountActionsBottomSheetFragmentDirections
                                .actionAccountActionsBottomSheetFragmentToEditAccountFragment()
                        findNavController().navigate(editAccountDirection)
                    }
                }

            }
        }
    }

    private fun setupViewAppearance() {
        val account = args.selectedAccount
        binding.run {
            actionsContainer.setBackgroundColor(Color.parseColor(account.color))
            accountName.text = account.name
            accountAmount.text = account.amount.toAmountFormat(withMinus = false)
            accountCurrency.text = mainViewModel.getCurrency()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}