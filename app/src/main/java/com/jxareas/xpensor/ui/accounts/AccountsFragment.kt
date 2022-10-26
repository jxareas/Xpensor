package com.jxareas.xpensor.ui.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import com.jxareas.xpensor.databinding.FragmentAccountsBinding
import com.jxareas.xpensor.ui.accounts.actions.menu.AddAccountMenu
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


    @Inject
    lateinit var accountsListAdapter: AccountsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            interpolator = FastOutSlowInInterpolator()
        }
    }

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
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
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
                }
            }
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(AddAccountMenu {
            viewModel.onAddNewAccountButtonClick()
        }, viewLifecycleOwner, Lifecycle.State.STARTED)
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