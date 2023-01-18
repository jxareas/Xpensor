package com.jxareas.xpensor.features.transactions.presentation.ui.actions.category

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jxareas.xpensor.common.extensions.navigateWithNavController
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.core.presentation.MainViewModel
import com.jxareas.xpensor.databinding.BottomSheetSelectCategoryBinding
import com.jxareas.xpensor.features.transactions.presentation.ui.actions.category.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SelectCategoryBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetSelectCategoryBinding? = null
    private val binding: BottomSheetSelectCategoryBinding
        get() = _binding!!

    private val viewModel: SelectCategoryViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    @Inject
    internal lateinit var categoryAdapter: CategoryAdapter

    private val args by navArgs<SelectCategoryBottomSheetArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetSelectCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupRecyclerView()
        setupCollectors()
        setupEventCollector()
    }

    private fun setupView() {
        val account = args.selectedAccount
        binding.run {
            accountName.text = account.name
            accountAmount.text = account.amount.toAmountFormat(withMinus = false)
            accountCurrency.text = mainViewModel.getCurrency()
            actionsContainer.setBackgroundColor(Color.parseColor(account.color))
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewCategories.run {
        adapter = categoryAdapter
        categoryAdapter.setOnClickListener(
            CategoryAdapter.OnClickListener { category ->
                viewModel.onSelectCategoryClick(args.selectedAccount, category)
            },
        )
    }

    private fun setupCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.categories
                        .collectLatest(categoryAdapter::submitList)
                }
                launch {
                    mainViewModel.selectedAccount
                        .collectLatest(viewModel::onUpdateSelectedAccount)
                }
                launch {
                    mainViewModel.selectedDateRange
                        .collectLatest(viewModel::onUpdateSelectedDateRange)
                }
            }
        }
    }

    private fun setupEventCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventSource
                .flowWithLifecycle(lifecycle)
                .collectLatest { categorySelectionUiEvent ->
                    when (categorySelectionUiEvent) {
                        is CategorySelectionUiEvent.SelectCategory -> {
                            val direction =
                                SelectCategoryBottomSheetDirections
                                    .actionSelectCategoryBottomSheetToAddTransactionBottomSheet(
                                        categorySelectionUiEvent.account,
                                        categorySelectionUiEvent.category,
                                        args.amount,
                                    )
                            navigateWithNavController(direction)
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
