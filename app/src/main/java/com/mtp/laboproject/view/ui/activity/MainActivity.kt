package com.mtp.laboproject.view.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mtp.laboproject.view.ui.fragment.HomeFragment
import com.mtp.laboproject.view.ui.fragment.LaboratoryFragment
import kotlinx.android.synthetic.main.activity_main.*
import com.mtp.laboproject.LaboApplication
import com.mtp.laboproject.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import android.view.MenuItem
import com.mtp.laboproject.view.ui.fragment.AlertFragment
import com.mtp.laboproject.view.ui.fragment.ProfilFragment


class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private var laboratoryFragment= LaboratoryFragment()
    private var homeFragment= HomeFragment()
    private var profilFragment= ProfilFragment()
    private var alertFragment= AlertFragment()



    private val parentJob = Job()
    //create a coroutine context with the job and the dispatcher
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    //create a coroutine scope with the coroutine context
    private val scope = CoroutineScope(coroutineContext)
    private var menuNavigation = arrayOf(
        R.id.navigation_home,
        R.id.navigation_labo,
        R.id.navigation_graph,
        R.id.navigation_alert,
        R.id.navigation_parametres
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setting toolbar
      //  setSupportActionBar(findViewById(R.id.toolbar))
        //home navigation
       // supportActionBar?.setDisplayHomeAsUpEnabled(false)
        replaceMainLayout(homeFragment,0)
        //define the activity context
        LaboApplication.instance=this

        bottomNavigationViewHome.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> replaceMainLayout(homeFragment,0)
                R.id.navigation_labo -> replaceMainLayout(laboratoryFragment,1)
                R.id.navigation_graph -> replaceMainLayout(laboratoryFragment,2)
                R.id.navigation_alert -> replaceMainLayout(alertFragment,3)
                R.id.navigation_parametres -> replaceMainLayout(profilFragment,4)
            }
            true
        }
    }

    override fun onBackPressed() {
        finish()
    }

      fun showBadgeOnNavigationButtomView(s: String?) {
          scope.launch(Dispatchers.Main.immediate) {
              bottomNavigationViewHome.getOrCreateBadge(menuNavigation[s!!.toInt()]).apply {
                  isVisible=true
                  number=1
              }
          }
    }
    private fun refreshBadgeView(menu:Int) {
        bottomNavigationViewHome.getOrCreateBadge(menuNavigation[menu]).apply {
            isVisible=false
            clearNumber()
        }
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
