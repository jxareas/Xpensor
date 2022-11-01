package com.jxareas.xpensor.ui.accounts.actions.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jxareas.xpensor.databinding.DialogFragmentAccountFilterBinding
import com.jxareas.xpensor.ui.accounts.actions.filter.events.AccountFilterEvent
import com.jxareas.xpensor.ui.accounts.adapter.AccountsListAdapter
import com.jxareas.xpensor.ui.main.MainActivityViewModel
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.utils.getDivider
import com.jxareas.xpensor.utils.setTint
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
                        mainViewModel.onUpdateSelectedAccount(event.account)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}