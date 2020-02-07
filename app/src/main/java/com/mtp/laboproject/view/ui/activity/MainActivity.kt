package com.mtp.laboproject.view.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import com.mtp.laboproject.LaboApplication
import com.mtp.laboproject.R
import com.mtp.laboproject.view.ui.fragment.*


class MainActivity : BaseActivity() {

    private val fragmentManager = supportFragmentManager
    private var laboratoryFragment= LaboratoryFragment()
    private var homeFragment= HomeFragment()
    private var profilFragment= ProfilFragment()
    private var alertFragment=
        AlertFragment()
    private var chartFragment= ChartFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.drawable.logo_bleu_small)
        supportActionBar!!.setDisplayUseLogoEnabled(true)
        supportActionBar!!.title = getString(R.string.watcher)
        
        replaceMainLayout(homeFragment,0)
        //define the activity context
        LaboApplication.instance=this

        bottomNavigationViewHome.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> replaceMainLayout(homeFragment,0)
                R.id.navigation_labo -> replaceMainLayout(laboratoryFragment,1)
                R.id.navigation_graph -> replaceMainLayout(chartFragment,2)
                R.id.navigation_alert -> replaceMainLayout(alertFragment,3)
                R.id.navigation_parametres -> replaceMainLayout(profilFragment,4)
            }
            true
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun replaceMainLayout(fragment: Fragment,menu:Int) {
        // delete bagde if existe
        fragmentManager.beginTransaction()
            .replace(R.id.layoutSlidingHome, fragment)
            .addToBackStack(null)
            .commit()
            refreshBadgeView(menu)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //send data to all fragment if need
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}
