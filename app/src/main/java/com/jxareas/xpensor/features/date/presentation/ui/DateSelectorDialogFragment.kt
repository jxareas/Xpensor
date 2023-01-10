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
import com.jxareas.xpensor.common.utils.DateUtils.toLocalDate
import com.jxareas.xpensor.core.presentation.MainViewModel
import com.jxareas.xpensor.databinding.DialogFragmentDateSelectorBinding
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
        const val WEEK = 7
        const val MONTH = 30
        const val YEAR = 365
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
            viewModel.events.collectLatest { event ->
                when (event) {
                    is SelectDateEvent.CustomDate -> {
                        val datePicker = MaterialDatePicker.Builder.datePicker()
                            .setTitleText(getString(R.string.select_date))
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .setTheme(R.style.MaterialCalendarTheme)
                            .build()

                        datePicker.addOnPositiveButtonClickListener { milliseconds ->
                            val date = (milliseconds + DAY_IN_MS).toLocalDate()
                            activityViewModel.onUpdateCurrentDateRange(date, date)
                            dismiss()
                        }
                        datePicker.show(childFragmentManager, DATE_PICKER_TAG)
                    }
                    is SelectDateEvent.Today -> {
                        val date = viewModel.getDate()
                        activityViewModel.onUpdateCurrentDateRange(date, date)
                        dismiss()
                    }
                    is SelectDateEvent.Week -> {
                        val from = viewModel.getDate(WEEK)
                        activityViewModel.onUpdateCurrentDateRange(from, null)
                        dismiss()
                    }
                    is SelectDateEvent.Month -> {
                        val from = viewModel.getDate(MONTH)
                        activityViewModel.onUpdateCurrentDateRange(from, null)
                        dismiss()
                    }
                    is SelectDateEvent.Year -> {
                        val from = viewModel.getDate(YEAR)
                        activityViewModel.onUpdateCurrentDateRange(from, null)
                        dismiss()
                    }
                    is SelectDateEvent.AllTime -> {
                        activityViewModel.onUpdateCurrentDateRange(null, null)
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
