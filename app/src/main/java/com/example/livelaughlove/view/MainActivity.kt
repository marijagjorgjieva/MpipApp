package com.example.livelaughlove.view

import android.content.ContentValues.TAG
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.livelaughlove.R
import com.example.livelaughlove.databinding.ActivityMainBinding
import com.example.livelaughlove.model.db.PlantDatabase
import com.example.livelaughlove.model.repositories.PlantRepository
import com.example.livelaughlove.viewmodel.PlantViewModel
import com.example.livelaughlove.viewmodel.PlantViewModelFactory
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {

    lateinit var plantVM: PlantViewModel
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        binding.bottomNavView.setupWithNavController(navController)
        binding.bottomNavView.background = null
        binding.fab.setOnClickListener {
            Navigation.findNavController(this, R.id.fragmentContainerView)
                .navigate(R.id.action_to_add_plant)
        }

        val plantRepository = PlantRepository(PlantDatabase(applicationContext))
        val plantVMProviderFactory = PlantViewModelFactory(plantRepository)
        plantVM = ViewModelProvider(this, plantVMProviderFactory).get(PlantViewModel::class.java)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Add OnDestinationChangedListener to show/hide BottomNavigationView and FAB
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.introFragment -> {
                    binding.bottomNavView.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                    binding.bottomAppBar.visibility = View.GONE

                }
                else -> {
                    binding.bottomNavView.visibility = View.VISIBLE
                    binding.fab.visibility = View.VISIBLE
                    binding.bottomAppBar.visibility = View.VISIBLE

                }
            }
        }
        mAuth = FirebaseAuth.getInstance()
        mAuth.signInAnonymously().addOnFailureListener(this, OnFailureListener { exception ->
            // If sign in fails, display a message to the user.
            Log.e(TAG, "signInAnonymously:FAILURE", exception)
        })

    }


}

