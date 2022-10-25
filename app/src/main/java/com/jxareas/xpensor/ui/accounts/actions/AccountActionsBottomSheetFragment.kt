package com.jxareas.xpensor.ui.accounts.actions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jxareas.xpensor.databinding.FragmentAccountActionsBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountActionsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAccountActionsBottomSheetBinding? = null
    private val binding: FragmentAccountActionsBottomSheetBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAccountActionsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}