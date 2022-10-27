package com.jxareas.xpensor.ui.accounts.actions.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialSharedAxis
import com.jxareas.xpensor.R
import com.jxareas.xpensor.databinding.FragmentEditAccountBinding
import com.jxareas.xpensor.ui.accounts.actions.menu.ApplyChangesMenu
import com.jxareas.xpensor.utils.DateUtils.toAmountFormat
import com.jxareas.xpensor.utils.getLong
import com.jxareas.xpensor.utils.setTint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditAccountFragment : Fragment() {

    private var _binding: FragmentEditAccountBinding? = null
    private val binding: FragmentEditAccountBinding
        get() = _binding!!


    private val args by navArgs<EditAccountFragmentArgs>()
    private val viewModel: EditAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = resources.getLong(R.integer.material_motion_duration_medium_2)
        }
        returnTransition = MaterialFade().apply {
            interpolator = FastOutSlowInInterpolator()
        }
        exitTransition = MaterialFade().apply {
            interpolator = FastOutSlowInInterpolator()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupView()
    }

    private fun setupView() {
        val account = args.editableAccount

        binding.run {
            textInputLayoutName.editText?.setText(account.name)
            textInputLayoutMoneyAmount.editText?.setText(account.amount.toAmountFormat(withMinus = false))
            selectedColor.setTint(account.color)
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(ApplyChangesMenu {
            viewModel.onApplyChanges()
        }, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}