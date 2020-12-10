package com.nkuppan.todo.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nkuppan.todo.R
import com.nkuppan.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.actionAdd.setOnClickListener(this)
        binding.bottomAppBar.setNavigationOnClickListener {
            //TODO handle the click listener
        }
    }

    private fun navigateToTaskCreate() {
        navController.navigate(R.id.action_todoListFragment_to_taskCreateDialogFragment)
    }

    override fun onClick(aView: View?) {
        when (aView?.id) {
            R.id.action_add -> {
                navigateToTaskCreate()
            }
        }
    }
}
