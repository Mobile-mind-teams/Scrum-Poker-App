package com.example.scrumpokerapp.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.scrumpokerapp.R
import com.example.scrumpokerapp.databinding.ActivityMainBinding
import com.example.scrumpokerapp.persistance.UserProfile
import com.example.scrumpokerapp.utils.ProjectUtils
import com.example.scrumpokerapp.view.fragment.*
import com.example.scrumpokerapp.viewmodel.MainActivityViewModel
import com.example.scrumpokerapp.viewmodel.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainActivityViewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        mainActivityViewModel = ViewModelProviders.of(
            this,
            MainActivityViewModelFactory()
        )[MainActivityViewModel::class.java]

        initActivity()

        mainActivityViewModel.showBottomNavigationMenu.observe(this, Observer{
            if (it!=null){
                if (it){
                    binding.bottomNavigationMenu.visibility = View.VISIBLE

                    if (mainActivityViewModel.showCreateSession()){
                        mainActivityViewModel.showCreateSessionBottomNavigationMenuItem.postValue(true)
                    }
                } else {
                    binding.bottomNavigationMenu.visibility = View.GONE
                }
            }
        })

        mainActivityViewModel.showCreateSessionBottomNavigationMenuItem.observe(this, Observer {
            if (it != null){
                binding.bottomNavigationMenu.menu.getItem(3).isVisible = it
            }
        })

        binding.bottomNavigationMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment.newInstance(), "HomeFragment")
                R.id.profile -> replaceFragment(ProfileFragment.newInstance(), "ProfileFragment")
                R.id.backlog -> replaceFragment(BacklogFragment.newInstance(), "BacklogFragment")
                R.id.create_session -> replaceFragment(CreateSessionFragment.newInstance(), "CreateSessionFragment")
                else -> Toast.makeText(application, "Error!", Toast.LENGTH_SHORT).show()
            }

            true
        }
    }

    private fun initActivity() {
        mainActivityViewModel.loggedStatus.postValue(false)

        replaceFragment(LogInFragment.newInstance(), "LogInFragment")
    }

    fun replaceFragment(newInstance: Fragment, tag: String) {
        val manager : FragmentManager = supportFragmentManager
        val fragment_transaction : FragmentTransaction = manager.beginTransaction()
        fragment_transaction.replace(R.id.frame_layout, newInstance, tag)
        fragment_transaction.commit()
    }

    override fun onBackPressed() {
        if(!mainActivityViewModel.loggedStatus.value!!.equals(true)){
            mainActivityViewModel.showBottomNavigationMenu.postValue(false)
            replaceFragment(LogInFragment.newInstance(), "LogInFragment")
        }
    }
}