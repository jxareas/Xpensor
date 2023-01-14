package com.jxareas.xpensor.features.transactions.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.core.view.MenuHost
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialSharedAxis
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.extensions.getLong
import com.jxareas.xpensor.core.presentation.MainActivity
import com.jxareas.xpensor.core.presentation.MainViewModel
import com.jxareas.xpensor.databinding.FragmentTransactionsBinding
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.date.presentation.ui.menu.SelectDateMenu
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails
import com.jxareas.xpensor.features.transactions.presentation.ui.adapter.TransactionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class TransactionsFragment : Fragment() {

    private var _binding: FragmentTransactionsBinding? = null
    private val binding: FragmentTransactionsBinding
        get() = _binding!!

    private lateinit var bottomNavigation: BottomNavigationView

    private val viewModel: TransactionsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var transactionAdapter: TransactionAdapter

    private var isAlertShowing = false

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
        bottomNavigation = (requireActivity() as MainActivity).binding.bottomNavigation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTransactionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition().also {
            view.doOnPreDraw { startPostponedEnterTransition() }
        }
        setupDate()
        setupRecyclerView()
        setupListeners()
        setupCollectors()
        setupEventCollector()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewTransactions.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL),
            )

            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val fab = binding.fabAddNewTransaction

                        if (dy > 0 && fab.isExtended)
                            fab.shrink()
                        else if (dy < 0 && !fab.isExtended)
                            fab.extend()
                    }
                },
            )
        }
    }

    private fun setupListeners() = binding.run {
        fabAddNewTransaction.setOnClickListener {
            val account = mainViewModel.selectedAccount.value ?: mainViewModel.accounts.value[0]
            viewModel.onAddTransactionClick(account)
        }
    }

    private fun setupEventCollector() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.eventSource.collectLatest { event ->
                when (event) {
                    is TransactionEvent.DateSelected ->
                        navigateToSelectDialogFragment()
                    is TransactionEvent.OpenTheAddTransactionSheet ->
                        navigateToAddTransactionSheet(event.account)
                    is TransactionEvent.DeleteTransaction ->
                        viewModel.deleteTransaction(event.transaction)
                    is TransactionEvent.ShowTheDeleteTransactionDialog ->
                        if (!isAlertShowing) showAlertDialog(event.transaction)
                }
            }
        }
    }

    private fun showAlertDialog(details: TransactionDetails) =
        MaterialAlertDialogBuilder(requireContext())
            .setIcon(R.drawable.ic_warning)
            .setTitle(R.string.delete_transaction_alert_title)
            .setMessage(
                getString(
                    R.string.transaction_summary_dialog,
                    details.category.name,
                    details.account.name,
                    details.transaction.amount.toString(),
                ),
            )
            .setPositiveButton(getString(R.string.confirm)) { _, _ ->
                viewModel.onDeleteTransactionConfirm(details)
                isAlertShowing = false
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                isAlertShowing = false
            }
            .setOnCancelListener { isAlertShowing = false }
            .create()
            .show()
            .also { isAlertShowing = true }

    private fun navigateToAddTransactionSheet(accountUi: AccountUi) {
        val direction =
            TransactionsFragmentDirections.actionTransactionsFragmentToSelectCategoryBottomSheet(
                accountUi,
            )
        findNavController().navigate(direction)
    }

    private fun navigateToSelectDialogFragment() {
        val direction =
            TransactionsFragmentDirections.actionTransactionsFragmentToDateSelectorDialogFragment()
        findNavController().navigate(direction)
    }

    private fun setupDate() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            SelectDateMenu {
                viewModel.onSelectedDateClick()
            },
            viewLifecycleOwner, Lifecycle.State.STARTED,
        )
    }

    private fun setupCollectors() {

        lifecycleScope.launchWhenStarted {
            viewModel.transactionState.collectLatest { state ->
                when (state) {
                    is TransactionState.Ready -> {
                        binding.progressBarTransactions.isVisible = false
                        binding.textViewNoTransactions.visibility =
                            if (state.transactions.isEmpty())
                                View.VISIBLE else View.INVISIBLE
                        transactionAdapter.submitList(state.transactions)
                    }
                    is TransactionState.Loading -> {
                        binding.progressBarTransactions.isVisible = true
                    }
                    is TransactionState.Idle -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.selectedDateRange.collectLatest(viewModel::onUpdateSelectedDateRange)
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.selectedAccount.collectLatest(viewModel::onUpdateSelectedAccount)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
