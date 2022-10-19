package com.jxareas.xpensor.ui.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jxareas.xpensor.R
import com.jxareas.xpensor.databinding.FragmentAccountsBinding
import com.jxareas.xpensor.ui.accounts.adapter.AccountsListAdapter
import com.jxareas.xpensor.ui.accounts.events.AccountEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class AccountsFragment : Fragment() {

    private var _binding: FragmentAccountsBinding? = null
    private val binding: FragmentAccountsBinding
        get() = _binding!!

    private val viewModel: AccountsViewModel by viewModels()

    private val accountsMenu = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
            menuInflater.inflate(R.menu.accounts_menu, menu)

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
            when (menuItem.itemId) {
                R.id.add_new_account -> {
                    viewModel.onAddNewAccountButtonClick()
                    true
                }
                else -> false
            }
    }


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
        setupMenu()
        setupRecyclerView()
        setupCollectors()
        setupEventCollectors()
    }

    private fun setupEventCollectors() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collectLatest { event ->
                when (event) {
                    is AccountEvent.NavigateToAddAccountScreen -> {
                        val addAccountFragmentAction =
                            AccountsFragmentDirections.actionAccountsFragmentToAddAccountFragment()
                        findNavController().navigate(addAccountFragmentAction)
                    }
                    is AccountEvent.OpenTheAccountActionsBottomSheet -> {
                        // TODO: Handle navigate to the open account actions bottom sheet
                    }
                }
            }
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(accountsMenu, viewLifecycleOwner, Lifecycle.State.RESUMED)
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