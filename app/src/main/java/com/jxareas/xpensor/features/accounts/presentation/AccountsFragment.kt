package com.jxareas.xpensor.features.accounts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.core.view.MenuHost
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialSharedAxis
import com.jxareas.xpensor.NavGraphDirections
import com.jxareas.xpensor.R
import com.jxareas.xpensor.databinding.FragmentAccountsBinding
import com.jxareas.xpensor.features.accounts.presentation.actions.menu.AddAccountMenu
import com.jxareas.xpensor.features.accounts.presentation.adapter.AccountsListAdapter
import com.jxareas.xpensor.common.extensions.getLong
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
        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition().also {
            view.doOnPreDraw { startPostponedEnterTransition() }
        }
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
                    is AccountEvent.OpenTheAccountBottomSheet -> {
                        val totalNumberOfAccounts = viewModel.accounts.value.size
                        val openAccountBottomSheetAction =
                            NavGraphDirections.actionGlobalAccountActions(event.account,
                                totalNumberOfAccounts)
                        findNavController().navigate(openAccountBottomSheetAction)
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
        addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )

        accountsListAdapter.setOnClickListener(
            AccountsListAdapter.OnClickListener { account ->
                viewModel.onAccountSelected(account)
            }
        )
    }

    private fun setupCollectors() {
        lifecycleScope.launchWhenStarted {
            viewModel.accounts.collectLatest { newAccountList ->
                accountsListAdapter.submitList(newAccountList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}