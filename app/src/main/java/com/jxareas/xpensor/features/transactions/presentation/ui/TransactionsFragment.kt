package com.jxareas.xpensor.features.transactions.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialSharedAxis
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.extensions.getLong
import com.jxareas.xpensor.common.extensions.navigateWithNavController
import com.jxareas.xpensor.common.extensions.postponeEnterTransitionAndStartOnPreDraw
import com.jxareas.xpensor.common.extensions.setMenuOnActivity
import com.jxareas.xpensor.core.presentation.MainActivity
import com.jxareas.xpensor.core.presentation.MainViewModel
import com.jxareas.xpensor.databinding.FragmentTransactionsBinding
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import com.jxareas.xpensor.features.date.presentation.ui.menu.SelectDateMenu
import com.jxareas.xpensor.features.transactions.domain.model.TransactionDetails
import com.jxareas.xpensor.features.transactions.presentation.ui.adapter.TransactionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TransactionsFragment : Fragment() {

    private var _binding: FragmentTransactionsBinding? = null
    private val binding: FragmentTransactionsBinding
        get() = _binding!!

    private val bottomNavigation: BottomNavigationView
            by lazy { (requireActivity() as MainActivity).binding.bottomNavigation }

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
        postponeEnterTransitionAndStartOnPreDraw()
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

                        if (dy > 0 && fab.isExtended) {
                            fab.shrink()
                            bottomNavigation.isVisible = false
                        } else if (dy < 0 && !fab.isExtended) {
                            fab.extend()
                            bottomNavigation.isVisible = true
                        }
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
                    is TransactionUiEvent.DateSelected ->
                        navigateToSelectDialogFragment()
                    is TransactionUiEvent.OpenTheAddTransactionSheet ->
                        navigateToSelectCategoryBottomSheet(event.account)
                    is TransactionUiEvent.DeleteTransaction ->
                        viewModel.deleteTransaction(event.transaction)
                    is TransactionUiEvent.ShowTheDeleteTransactionDialog ->
                        if (!isAlertShowing) showConfirmTransactionDeletionDialog(event.transaction)
                }
            }
        }
    }

    private fun showConfirmTransactionDeletionDialog(details: TransactionDetails) =
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

    private fun navigateToSelectCategoryBottomSheet(accountUi: AccountUi) {
        val selectCategoryBottomSheetAction =
            TransactionsFragmentDirections.actionTransactionsFragmentToSelectCategoryBottomSheet(
                accountUi,
            )
        navigateWithNavController(selectCategoryBottomSheetAction)
    }

    private fun navigateToSelectDialogFragment() {
        val dateSelectorDialogAction =
            TransactionsFragmentDirections.actionTransactionsFragmentToDateSelectorDialogFragment()
        navigateWithNavController(dateSelectorDialogAction)
    }

    private fun setupDate() = setMenuOnActivity {
        SelectDateMenu(viewModel::onSelectDateClick)
    }

    private fun setupCollectors() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.transactionState
                        .collectLatest(this@TransactionsFragment::handleTransactionState)
                }
                launch {
                    mainViewModel.selectedDateRange
                        .collectLatest(viewModel::onUpdateSelectedDateRange)
                }
                launch {
                    mainViewModel.selectedAccount
                        .collectLatest(viewModel::onUpdateSelectedAccount)
                }
            }
        }

    }

    private fun handleTransactionState(state: TransactionState) =
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
