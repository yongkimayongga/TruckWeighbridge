package com.truck.weighbridge.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.truck.weighbridge.R
import com.truck.weighbridge.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
    }

    // Method #2
    private fun onFloatingClicked() {
        navController.navigate(R.id.action_listFragment_to_addFragment)
        hideFloatingButton()
    }

    // Method #3
    fun showFloatingButton() = with(binding) {
        fab.show()
        fab.visibility = View.VISIBLE
    }

    // Method #4
    fun hideFloatingButton() = with(binding) {
        fab.hide()
        fab.visibility = View.GONE
    }

    // Method #5
    private fun initView() = with(binding) {
        navController = findNavController(R.id.container)

        fab.setOnClickListener {
            onFloatingClicked()
        }
    }
}