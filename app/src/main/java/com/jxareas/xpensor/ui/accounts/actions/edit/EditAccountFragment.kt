package com.jxareas.xpensor.ui.accounts.actions.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis
import com.jxareas.xpensor.databinding.FragmentEditAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditAccountFragment : Fragment() {

    private var _binding: FragmentEditAccountBinding? = null
    private val binding: FragmentEditAccountBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        exitTransition = MaterialElevationScale(false).apply {
            interpolator = FastOutSlowInInterpolator()
        }
        returnTransition = MaterialElevationScale(false).apply {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}