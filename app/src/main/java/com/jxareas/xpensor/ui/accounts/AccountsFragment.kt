package com.jxareas.xpensor.ui.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jxareas.xpensor.databinding.FragmentAccountsBinding
import com.jxareas.xpensor.ui.accounts.adapter.AccountsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class AccountsFragment : Fragment() {

    private var _binding: FragmentAccountsBinding? = null
    private val binding: FragmentAccountsBinding
        get() = _binding!!

    private val viewModel: AccountsViewModel by viewModels()

    @Inject
    lateinit var accountsListAdapter: AccountsListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupCollectors()
    }

    private fun setupRecyclerView() = binding.recyclerViewAccounts.run {
        adapter = accountsListAdapter
    }

    private fun setupCollectors() {
        lifecycleScope.launchWhenStarted {
            viewModel.accounts.collectLatest { newAccountList ->
                accountsListAdapter.submitList(newAccountList)
            }
        }
    }

}