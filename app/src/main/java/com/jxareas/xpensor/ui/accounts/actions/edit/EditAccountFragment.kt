package com.jxareas.xpensor.ui.accounts.actions.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jxareas.xpensor.R

class EditAccountFragment : Fragment() {

    companion object {
        fun newInstance() = EditAccountFragment()
    }

    private lateinit var viewModel: EditAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditAccountViewModel::class.java)
        // TODO: Use the ViewModel
    }

}