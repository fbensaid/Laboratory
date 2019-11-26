package com.mtp.laboproject.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mtp.laboproject.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private var laboratoryFragment=LaboratoryFragment()
    private var homeFragment=HomeFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceMainLayout(homeFragment)
        bottomNavigationViewHome.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> replaceMainLayout(homeFragment)
                R.id.navigation_labo -> replaceMainLayout(laboratoryFragment)
                R.id.navigation_graph -> replaceMainLayout(laboratoryFragment)
                R.id.navigation_parametres -> replaceMainLayout(laboratoryFragment)
            }
            true
        }
    }

    private fun replaceMainLayout(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .replace(R.id.layoutSlidingHome, fragment)
            .addToBackStack(null)
            .commit()
    }



}
