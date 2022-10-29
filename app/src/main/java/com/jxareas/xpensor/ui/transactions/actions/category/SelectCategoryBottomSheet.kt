package com.jxareas.xpensor.ui.transactions.actions.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jxareas.xpensor.databinding.BottomSheetSelectCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectCategoryBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetSelectCategoryBinding? = null
    private val binding: BottomSheetSelectCategoryBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetSelectCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}