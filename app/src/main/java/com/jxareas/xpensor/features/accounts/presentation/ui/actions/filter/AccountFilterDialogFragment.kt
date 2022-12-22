package com.jxareas.xpensor.features.accounts.presentation.ui.actions.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jxareas.xpensor.common.extensions.getDivider
import com.jxareas.xpensor.common.extensions.setTint
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.core.presentation.MainActivityViewModel
import com.jxareas.xpensor.databinding.DialogFragmentAccountFilterBinding
import com.jxareas.xpensor.features.accounts.presentation.ui.adapter.AccountsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class AccountFilterDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentAccountFilterBinding? = null
    private val binding: DialogFragmentAccountFilterBinding
        get() = _binding!!

    private val viewModel: AccountFilterViewModel by viewModels()
    private val mainViewModel: MainActivityViewModel by activityViewModels()

    @Inject
    internal lateinit var accountsListAdapter: AccountsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogFragmentAccountFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupRecyclerView()
        setupCollectors()
        setupEventCollector()
    }

    private fun setupEventCollector() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collectLatest { event ->
                when (event) {
                    is AccountFilterEvent.SelectAccount ->
                        mainViewModel.onUpdateSelectedAccount(event.account).also { dismiss() }
                }
            }
        }
    }

    private fun setupCollectors() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.accounts.collectLatest { accounts ->
                accountsListAdapter.submitList(accounts)
                binding.allAccountsAmount.text =
                    viewModel.getTotalAccountsAmount().toAmountFormat(withMinus = false)
            }
        }
    }

    private fun setupView() = binding.run {
        allAccountsCurrency.text = mainViewModel.getCurrency()
        allAccountsIconColor.setTint(mainViewModel.selectedAccount.value?.color)
        allAccountsItem.setOnClickListener {
            mainViewModel.onUpdateSelectedAccount(null)
            dismiss()
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewAccounts.run {
        adapter = accountsListAdapter
        layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(getDivider(requireContext()))

        accountsListAdapter.setOnClickListener(
            AccountsListAdapter.OnClickListener { account ->
                viewModel.onAccountSelected(account)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
