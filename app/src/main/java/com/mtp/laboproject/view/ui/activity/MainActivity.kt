package com.mtp.laboproject.view.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mtp.laboproject.view.ui.fragment.HomeFragment
import com.mtp.laboproject.view.ui.fragment.LaboratoryFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import com.mtp.laboproject.LaboApplication
import com.mtp.laboproject.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext




class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private var laboratoryFragment= LaboratoryFragment()
    private var homeFragment= HomeFragment()
    private var notificationBadge: View? = null

    private val parentJob = Job()
    //create a coroutine context with the job and the dispatcher
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    //create a coroutine scope with the coroutine context
    private val scope = CoroutineScope(coroutineContext)
    private var menuNavigation = arrayOf(
        R.id.navigation_home,
        R.id.navigation_labo,
        R.id.navigation_graph,
        R.id.navigation_parametres
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //init badge


        replaceMainLayout(homeFragment,0)
        //define the activity context
        LaboApplication.instance=this

        bottomNavigationViewHome.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> replaceMainLayout(homeFragment,0)
                R.id.navigation_labo -> replaceMainLayout(laboratoryFragment,1)
                R.id.navigation_graph -> replaceMainLayout(laboratoryFragment,2)
                R.id.navigation_parametres -> replaceMainLayout(laboratoryFragment,3)
            }
            true
        }
    }

    override fun onBackPressed() {
        finish()
    }

      fun showBadgeOnNavigationButtomView(s: String?) {
          scope.launch(Dispatchers.Main.immediate) {
              val badgeDrawable = bottomNavigationViewHome.getOrCreateBadge(menuNavigation[s!!.toInt()])
              badgeDrawable.isVisible = true
              badgeDrawable.number = 1
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
        refreshBadgeView(menu)
        fragmentManager.beginTransaction()
            .replace(R.id.layoutSlidingHome, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {

        private const val TAG = "MainActivity"
    }

}
