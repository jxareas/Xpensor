package com.jxareas.xpensor.features.accounts.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialSharedAxis
import com.jxareas.xpensor.NavGraphDirections
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.extensions.getLong
import com.jxareas.xpensor.common.extensions.navigateWithNavController
import com.jxareas.xpensor.common.extensions.postponeEnterTransitionAndStartOnPreDraw
import com.jxareas.xpensor.common.extensions.setMenuOnActivity
import com.jxareas.xpensor.databinding.FragmentAccountsBinding
import com.jxareas.xpensor.features.accounts.presentation.model.TotalAccountsAmountUi
import com.jxareas.xpensor.features.accounts.presentation.ui.actions.menu.AddAccountMenu
import com.jxareas.xpensor.features.accounts.presentation.ui.adapter.AccountsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AccountsFragment : Fragment() {

    private var _binding: FragmentAccountsBinding? = null
    private val binding: FragmentAccountsBinding
        get() = _binding!!

    private val viewModel: AccountsViewModel by viewModels()

    @Inject
    lateinit var accountListAdapter: AccountsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            interpolator = AnticipateOvershootInterpolator()
            duration = resources.getLong(R.integer.material_motion_duration_long_1)
            setPathMotion(MaterialArcMotion())
        }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            interpolator = AnticipateOvershootInterpolator()
            duration = resources.getLong(R.integer.material_motion_duration_long_1)
            setPathMotion(MaterialArcMotion())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransitionAndStartOnPreDraw()
        setupMenu()
        setupRecyclerView()
        setupCollectors()
        setupEventCollector()
    }


    private fun setupMenu() = setMenuOnActivity {
        AddAccountMenu(viewModel::onAddNewAccountButtonClick)
    }

    private fun setupRecyclerView() = binding.recyclerViewAccounts.run {
        adapter = accountListAdapter
        addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL),
        )

        accountListAdapter.setOnClickListener(
            AccountsListAdapter.OnClickListener { account ->
                viewModel.onSelectAccountClick(account)
            },
        )
    }

    private fun setupCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.accounts.collectLatest(accountListAdapter::submitList)
                }
                launch {
                    viewModel.totalAccountsAmount.collectLatest(this@AccountsFragment::setTotalAccountsAmountText)
                }
            }
        }

    }

    private fun setTotalAccountsAmountText(totalAmountUi: TotalAccountsAmountUi?) = binding.run {
        totalAmountUi?.let {
            val totalAmountString = totalAmountUi.totalAmount.toString()
            val currency = totalAmountUi.currencyName
            textViewFullAmount.text =
                resources.getString(R.string.total_account_amount, totalAmountString)
            textViewMainCurrency.text =
                resources.getString(R.string.preferred_currency, currency)
        }
    }

    private fun setupEventCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventSource
                .flowWithLifecycle(lifecycle)
                .collectLatest { accountUiEvent ->
                    when (accountUiEvent) {
                        is AccountUiEvent.NavigateToAddAccountScreen -> {
                            val addAccountFragmentAction =
                                AccountsFragmentDirections.actionAccountsFragmentToAddAccountFragment()
                            navigateWithNavController(addAccountFragmentAction)
                        }
                        is AccountUiEvent.OpenTheAccountBottomSheet -> {
                            val totalNumberOfAccounts = viewModel.accounts.value.size
                            val openAccountBottomSheetAction =
                                NavGraphDirections.actionGlobalAccountActions(
                                    accountUiEvent.account,
                                    totalNumberOfAccounts,
                                )
                            navigateWithNavController(openAccountBottomSheetAction)
                        }
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
