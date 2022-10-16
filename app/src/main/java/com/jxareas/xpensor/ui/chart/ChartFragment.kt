package com.jxareas.xpensor.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.jxareas.xpensor.data.local.views.CategoryView
import com.jxareas.xpensor.databinding.FragmentChartBinding
import com.jxareas.xpensor.ui.main.MainActivityViewModel
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.utils.PreferenceUtils.MAIN_COLOR
import com.jxareas.xpensor.utils.getThemeColor
import com.jxareas.xpensor.utils.setCategoryAttributes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ChartFragment : Fragment() {

    private var _binding: FragmentChartBinding? = null
    private val binding: FragmentChartBinding
        get() = _binding!!

    private val viewModel: ChartViewModel by viewModels()
    private val mainViewModel: MainActivityViewModel by activityViewModels()

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
        setupCollectors()
    }

    private fun setupCollectors() {
        lifecycleScope.launchWhenStarted {
            viewModel.categories.collectLatest { newCategories ->
                updateChartData(newCategories)
            }
        }
    }

    private fun updateChartData(categories: List<CategoryView>) {
        if (categories.isNotEmpty()) {
            val currency = mainViewModel.getCurrency()

            updateCategories(categories, currency)

            var amount = 0.0
            val entries = ArrayList<PieEntry>()
            val entryColors = ArrayList<Int>()

            categories.forEach { category ->
                if (category.amount != 0.0) {
                    entries.add(PieEntry(category.amount.toFloat()))
                    entryColors.add(Color.parseColor(category.iconColor))
                    amount += category.amount
                }
            }

            if (amount == 0.0) {
                entries.add(PieEntry(1f))
                entryColors.add(Color.parseColor(MAIN_COLOR))
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
                centerText = "Expenses\n$amountString"
                setCenterTextSize(20f)
                description.isEnabled = false
                legend.isEnabled = false
                data = PieData(dataSet)
            }

            binding.chart.invalidate()
            binding.chart.animateY(1000, Easing.EaseInOutQuad)
        }
    }

    private fun updateCategories(categories: List<CategoryView>, currency: String?) =
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

}