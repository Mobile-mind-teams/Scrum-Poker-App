package com.example.scrumpokerapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.databinding.ActivityMainBinding
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.view.fragment.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isProductOwner : Boolean = intent.getBooleanExtra("role", false)

        val binding : ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        if(isProductOwner){
            binding.bottomNavigationMenu.menu.getItem(3).setVisible(true)
        }

        replaceFragment(HomeFragment.newInstance())

        binding.bottomNavigationMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment.newInstance())
                R.id.profile -> replaceFragment(ProfileFragment.newInstance())
                R.id.backlog -> replaceFragment(BacklogFragment.newInstance())
                R.id.create_session -> replaceFragment(CreateSessionFragment.newInstance())
                else -> Toast.makeText(application, "Error!", Toast.LENGTH_SHORT).show()
            }

            true
        }
    }

    private fun replaceFragment(newInstance: Fragment) {
        val manager : FragmentManager = supportFragmentManager
        val fragment_transaction : FragmentTransaction = manager.beginTransaction()
        fragment_transaction.replace(R.id.frame_layout, newInstance)
        fragment_transaction.commit()
    }
}