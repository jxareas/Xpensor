package com.jxareas.xpensor.features.transactions.presentation.ui.actions.add

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.extensions.navigateWithNavController
import com.jxareas.xpensor.common.extensions.setIcon
import com.jxareas.xpensor.common.extensions.showSnackbar
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.databinding.BottomSheetAddTransactionBinding
import com.jxareas.xpensor.features.transactions.domain.model.Transaction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddTransactionBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetAddTransactionBinding? = null
    private val binding: BottomSheetAddTransactionBinding
        get() = _binding!!

    private val viewModel: AddTransactionViewModel by viewModels()
    private val args by navArgs<AddTransactionBottomSheetArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListeners()
        setupCollectors()
        setupEventCollector()
    }

    private fun setupView() = binding.run {
        accountName.text = args.selectedAccount.name
        categoryName.text = args.selectedCategory.category.name
        categoryIcon.setIcon(args.selectedCategory.category.icon)

        if (args.amount != 0f)
            textInputLayoutExpense.editText?.setText(
                args.amount.toDouble().toAmountFormat(withMinus = false),
            )

        accountBackground.setBackgroundColor(Color.parseColor(args.selectedAccount.color))
        categoryBackground
            .setBackgroundColor(Color.parseColor(args.selectedCategory.category.iconColor))
    }

    private fun setupListeners() = binding.run {
        buttonAddTransaction.setOnClickListener { viewModel.onConfirmTransactionCreation() }
    }

    private fun setupCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.transactionState
                .flowWithLifecycle(lifecycle)
                .collectLatest { newTransactionState ->
                    when (newTransactionState) {
                        is NewTransactionState.Idle -> Unit
                        is NewTransactionState.Valid -> navigateBackToTransactionFragment()
                        is NewTransactionState.NotEnoughFunds -> showInvalidTransactionSnackbar()
                    }
                }
        }
    }

    private fun setupEventCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventSource
                .flowWithLifecycle(lifecycle)
                .collectLatest { event ->
                    when (event) {
                        is AddTransactionUiEvent.CreateNewTransaction -> {
                            val account = args.selectedAccount
                            val details = args.selectedCategory
                            val amount =
                                binding.textInputLayoutExpense.editText?.text.toString()
                                    .toDoubleOrNull()
                            if (amount == null || amount <= 0)
                                showInvalidInputSnackbar()
                            else {
                                val note =
                                    binding.textInputLayoutDescription.editText?.text.toString()

                                val transaction = Transaction(
                                    note = note,
                                    amount = amount,
                                )

                                viewModel.onAddTransaction(
                                    transaction,
                                    account.id,
                                    details.category.id,
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun showInvalidTransactionSnackbar() =
        showSnackbar(errorMessage = getString(R.string.not_enough_funds))

    private fun showInvalidInputSnackbar() =
        showSnackbar(errorMessage = getString(R.string.enter_expense_error))

    private fun navigateBackToTransactionFragment() {
        val direction =
            AddTransactionBottomSheetDirections
                .actionAddTransactionBottomSheetToTransactionsFragment()
        navigateWithNavController(direction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
