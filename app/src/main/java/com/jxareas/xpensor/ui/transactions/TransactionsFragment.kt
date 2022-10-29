package com.jxareas.xpensor.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jxareas.xpensor.databinding.FragmentTransactionsBinding
import com.jxareas.xpensor.ui.date.menu.SelectDateMenu
import com.jxareas.xpensor.ui.transactions.adapter.TransactionAdapter
import com.jxareas.xpensor.ui.transactions.event.TransactionEvent
import com.jxareas.xpensor.ui.transactions.state.TransactionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class TransactionsFragment : Fragment() {

    private var _binding: FragmentTransactionsBinding? = null
    private val binding: FragmentTransactionsBinding
        get() = _binding!!

    private val viewModel: TransactionsViewModel by viewModels()

    @Inject
    lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTransactionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDate()
        setupCollectors()
        setupEventCollector()
    }

    private fun setupEventCollector() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collectLatest { event ->
                when (event) {
                    is TransactionEvent.DateSelected ->
                        navigateToSelectDialogFragment()
                    else -> {
                        // TODO : Add other Event Handlers
                    }
                }
            }
        }
    }


    private fun navigateToSelectDialogFragment() {
        val direction = TransactionsFragmentDirections
            .actionTransactionsFragmentToDateSelectorDialogFragment()
        findNavController().navigate(direction)
    }

    private fun setupDate() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(SelectDateMenu {
            viewModel.onSelectedDateClick()
        }, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

    private fun setupCollectors() {
        lifecycleScope.launchWhenStarted {
            viewModel.transactionState.collectLatest { state ->
                when (state) {
                    is TransactionState.Ready -> {
                        binding.progressBar.isVisible = false
                        binding.noTransaction.visibility = if (state.transactions.isEmpty())
                            View.VISIBLE else View.INVISIBLE
                        transactionAdapter.submitList(state.transactions)
                    }
                    is TransactionState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is TransactionState.Idle -> Unit
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}