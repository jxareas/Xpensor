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
import com.jxareas.xpensor.databinding.FragmentTransactionsBinding
import com.jxareas.xpensor.ui.menu.SelectDateMenu
import com.jxareas.xpensor.ui.transactions.adapter.TransactionAdapter
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
    }

    private fun setupDate() {
        val menuHost : MenuHost = requireActivity()
        menuHost.addMenuProvider(SelectDateMenu {
            // TODO : Handle on click by the viewmodel
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


}