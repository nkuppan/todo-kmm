package com.nkuppan.todo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ancient.essentials.extentions.autoCleared
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.FragmentSettingsBinding
import com.nkuppan.todo.ui.viewmodel.SettingViewModel
import com.nkuppan.todo.utils.NavigationManager
import com.nkuppan.todo.utils.SettingPrefManager

class SettingFragment : BottomSheetDialogFragment() {

    private var dataBinding: FragmentSettingsBinding by autoCleared()

    private var viewModel: SettingViewModel by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        viewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
        dataBinding = FragmentSettingsBinding.bind(view)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.renameList.observe(this.viewLifecycleOwner, {
            NavigationManager.openTaskGroupPage(
                this,
                SettingPrefManager.getSelectedTaskGroup()
            )
        })
    }
}