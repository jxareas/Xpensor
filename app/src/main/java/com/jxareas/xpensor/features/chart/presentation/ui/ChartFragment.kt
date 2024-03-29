package com.jxareas.xpensor.features.chart.presentation.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialSharedAxis
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.extensions.getLong
import com.jxareas.xpensor.common.extensions.getThemeColor
import com.jxareas.xpensor.common.extensions.navigateWithNavController
import com.jxareas.xpensor.common.extensions.setCategoryAttributes
import com.jxareas.xpensor.common.extensions.setMenuOnActivity
import com.jxareas.xpensor.common.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.core.data.local.preferences.UserPreferences.Companion.DEFAULT_COLOR
import com.jxareas.xpensor.core.presentation.MainViewModel
import com.jxareas.xpensor.databinding.FragmentChartBinding
import com.jxareas.xpensor.features.date.presentation.ui.menu.SelectDateMenu
import com.jxareas.xpensor.features.transactions.presentation.model.CategoryWithAmountUi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChartFragment : Fragment() {

    private var _binding: FragmentChartBinding? = null
    private val binding: FragmentChartBinding
        get() = _binding!!

    private val viewModel: ChartViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

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
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition().also {
            view.doOnPreDraw { startPostponedEnterTransition() }
        }
        setupMenu()
        setupCollectors()
        setupEventCollector()
    }

    private fun setupMenu() = setMenuOnActivity {
        SelectDateMenu(viewModel::onDateSelectedClick)
    }

    private fun setupCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.categories.collectLatest(this@ChartFragment::updateChartData)
                }

                launch {
                    mainViewModel.selectedDateRange.collectLatest(viewModel::onSelectedDateRangeUpdate)
                }

                launch {
                    mainViewModel.selectedAccount.collectLatest(viewModel::onSelectedAccountUpdate)
                }

            }
        }
    }

    private fun setupEventCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventSource
                .flowWithLifecycle(lifecycle)
                .collectLatest { chartUiEvent ->
                    when (chartUiEvent) {
                        is ChartUiEvent.DateSelected -> {
                            val selectDateDialogAction = ChartFragmentDirections
                                .actionChartFragmentToDateSelectorDialogFragment()
                            navigateWithNavController(selectDateDialogAction)
                        }
                    }
                }
        }
    }

    private fun updateChartData(details: List<CategoryWithAmountUi>) {
        if (details.isNotEmpty()) {
            val currency = mainViewModel.getCurrency()

            updateCategories(details, currency)

            var amount = 0.0
            val entries = ArrayList<PieEntry>()
            val entryColors = ArrayList<Int>()

            details.forEach { categoryWithDetails ->
                if (categoryWithDetails.amount != 0.0) {
                    entries.add(PieEntry(categoryWithDetails.amount.toFloat()))
                    entryColors.add(Color.parseColor(categoryWithDetails.category.iconColor))
                    amount += categoryWithDetails.amount
                }
            }

            if (amount == 0.0) {
                entries.add(PieEntry(1f))
                entryColors.add(Color.parseColor(DEFAULT_COLOR))
                binding.chart.alpha = 0.3f
            } else binding.chart.alpha = 1f

            val dataSet = PieDataSet(entries, "").apply {
                colors = entryColors
                setDrawValues(false)
                sliceSpace = 2f
            }

            val amountString = amount.toAmountFormat(withMinus = false) + ' ' + currency
            binding.chart.apply {
                isDrawHoleEnabled = true
                holeRadius = 86f
                setHoleColor(Color.TRANSPARENT)
                setCenterTextColor(getThemeColor(com.google.android.material.R.attr.colorSecondary))
                centerText = resources.getString(R.string.expenses_with_amount, amountString)
                setCenterTextTypeface(Typeface.DEFAULT_BOLD)
                setCenterTextSize(20f)
                description.isEnabled = false
                legend.isEnabled = false
                data = PieData(dataSet)
            }

            binding.chart.invalidate()
            binding.chart.animateY(1000, Easing.EaseInOutCirc)
        }
    }

    private fun updateCategories(categories: List<CategoryWithAmountUi>, currency: String?) =
        binding.run {
            category1.setCategoryAttributes(categories[0], currency)
            category2.setCategoryAttributes(categories[1], currency)
            category3.setCategoryAttributes(categories[2], currency)
            category4.setCategoryAttributes(categories[3], currency)
            category5.setCategoryAttributes(categories[4], currency)
            category6.setCategoryAttributes(categories[5], currency)
            category7.setCategoryAttributes(categories[6], currency)
            category8.setCategoryAttributes(categories[7], currency)
            category9.setCategoryAttributes(categories[8], currency)
            category10.setCategoryAttributes(categories[9], currency)
            category11.setCategoryAttributes(categories[10], currency)
            category12.setCategoryAttributes(categories[11], currency)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
