package com.example.youtubeclone

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.youtubeclone.Fragments.HomeFragment
import com.example.youtubeclone.Fragments.ShortsFragment
import com.example.youtubeclone.Fragments.SubscriptionsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Navigation_Home : AppCompatActivity() {

    lateinit var context: Context
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_home)

        // Initialize views after setting the content view
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        context = this

        // Change the fragment on button click
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(HomeFragment())
                R.id.shorts -> setCurrentFragment(ShortsFragment())
                R.id.subscription -> setCurrentFragment(SubscriptionsFragment())
            }
            true
        }
    }

    // Set the fragments
    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}
