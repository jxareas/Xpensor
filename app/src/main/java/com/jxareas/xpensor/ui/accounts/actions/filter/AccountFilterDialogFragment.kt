package com.jxareas.xpensor.ui.accounts.actions.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jxareas.xpensor.databinding.DialogFragmentAccountFilterBinding
import com.jxareas.xpensor.ui.accounts.adapter.AccountsListAdapter
import com.jxareas.xpensor.utils.getDivider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountFilterDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentAccountFilterBinding? = null
    private val binding: DialogFragmentAccountFilterBinding
        get() = _binding!!

    @Inject
    internal lateinit var accountsListAdapter: AccountsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogFragmentAccountFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = binding.recyclerViewAccounts.run {
        adapter = accountsListAdapter
        layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(getDivider(requireContext()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}