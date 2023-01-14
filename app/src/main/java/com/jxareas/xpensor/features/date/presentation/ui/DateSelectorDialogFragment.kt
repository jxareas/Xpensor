package com.jxareas.xpensor.features.date.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.utils.DateUtils.DAY_IN_MS
import com.jxareas.xpensor.common.utils.DateUtils.fromDate
import com.jxareas.xpensor.common.utils.DateUtils.toDateRange
import com.jxareas.xpensor.core.presentation.MainViewModel
import com.jxareas.xpensor.databinding.DialogFragmentDateSelectorBinding
import com.jxareas.xpensor.features.date.domain.model.EmptyDateRange
import com.jxareas.xpensor.features.date.domain.model.MONTH
import com.jxareas.xpensor.features.date.domain.model.WEEK
import com.jxareas.xpensor.features.date.domain.model.YEAR
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DateSelectorDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentDateSelectorBinding? = null
    private val binding: DialogFragmentDateSelectorBinding
        get() = _binding!!

    private val viewModel: DateSelectorViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    private companion object {
        const val DATE_PICKER_TAG = "date_picker_tag"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogFragmentDateSelectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupEventCollector()
    }

    private fun setupListeners() = binding.run {
        cardViewSelectDate.setOnClickListener { viewModel.onSelectDate() }
        cardViewToday.setOnClickListener { viewModel.onSelectToday() }
        cardViewWeek.setOnClickListener { viewModel.onSelectWeek() }
        cardViewMonth.setOnClickListener { viewModel.onSelectMonth() }
        cardViewYear.setOnClickListener { viewModel.onSelectYear() }
        cardViewAllTime.setOnClickListener { viewModel.onSelectAllTime() }
    }

    private fun setupEventCollector() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventSource.collectLatest { event ->
                when (event) {
                    is SelectDateEvent.CustomDate -> {
                        val datePicker = MaterialDatePicker.Builder.datePicker()
                            .setTitleText(getString(R.string.select_date))
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .setTheme(R.style.MaterialCalendarTheme)
                            .build()

                        datePicker.addOnPositiveButtonClickListener { milliseconds ->
                            val customDay = (milliseconds + DAY_IN_MS).toDateRange()
                            activityViewModel.onUpdateCurrentDateRange(customDay)
                            dismiss()
                        }
                        datePicker.show(childFragmentManager, DATE_PICKER_TAG)
                    }
                    is SelectDateEvent.Today -> {
                        val date = viewModel.getDate().toDateRange()
                        activityViewModel.onUpdateCurrentDateRange(date)
                        dismiss()
                    }
                    is SelectDateEvent.Week -> {
                        val lastWeek = viewModel.getDate(WEEK).fromDate()
                        activityViewModel.onUpdateCurrentDateRange(lastWeek)
                        dismiss()
                    }
                    is SelectDateEvent.Month -> {
                        val lastMonth = viewModel.getDate(MONTH).fromDate()
                        activityViewModel.onUpdateCurrentDateRange(lastMonth)
                        dismiss()
                    }
                    is SelectDateEvent.Year -> {
                        val lastYear = viewModel.getDate(YEAR).fromDate()
                        activityViewModel.onUpdateCurrentDateRange(lastYear)
                        dismiss()
                    }
                    is SelectDateEvent.AllTime -> {
                        activityViewModel.onUpdateCurrentDateRange(EmptyDateRange)
                        dismiss()
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
