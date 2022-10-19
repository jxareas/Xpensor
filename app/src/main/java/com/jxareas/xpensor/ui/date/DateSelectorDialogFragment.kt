package com.jxareas.xpensor.ui.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jxareas.xpensor.databinding.DialogFragmentDateSelectorBinding
import com.jxareas.xpensor.ui.date.event.DateSelectedEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DateSelectorDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentDateSelectorBinding? = null
    private val binding: DialogFragmentDateSelectorBinding
        get() = _binding!!

    private val viewModel: DateSelectorViewModel by viewModels()

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
                    is DateSelectedEvent.CustomDate -> {
                        // TODO : Handle select custom date
                    }
                    is DateSelectedEvent.Today -> {
                        // TODO : Handle select today
                    }
                    is DateSelectedEvent.Week -> {
                        // TODO : Handle select week
                    }
                    is DateSelectedEvent.Month -> {
                        // TODO : Handle select month
                    }
                    is DateSelectedEvent.Year -> {
                        // TODO : Handle select year
                    }
                    is DateSelectedEvent.AllTime -> {
                        // TODO : Handle select all time
                    }
                }
            }
        }
    }


}