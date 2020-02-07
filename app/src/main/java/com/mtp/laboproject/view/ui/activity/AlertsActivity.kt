package com.mtp.laboproject.view.ui.activity

import android.graphics.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.mtp.laboproject.R
import com.mtp.laboproject.data.model.alert.AlertsDetailsResponse
import com.mtp.laboproject.view.listener.AlertsClickListener
import com.mtp.laboproject.view.adapter.AlertsAdapter
import com.mtp.laboproject.view.factory.AlertsViewModelFactory
import com.mtp.laboproject.view.viewmodel.AlertsViewModel
import kotlinx.android.synthetic.main.activity_alerts.*


class AlertsActivity : BaseActivity(), AlertsClickListener {

    private lateinit var alertsViewModel: AlertsViewModel
    private lateinit var alertsAdapter: AlertsAdapter
    private lateinit var searchView: SearchView
    private val p = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerts)

        //setRepo()
        //initSwipe()

    }

    // delete this because we dont need viewLifecycleOwner in activity
    /*private fun setRepo() {
        val factory = AlertsViewModelFactory()
        alertsViewModel = ViewModelProviders.of(this, factory)
            .get(AlertsViewModel::class.java)
        alertsViewModel.getAlerts()
        alertsViewModel.alertsLiveData.observe(this, Observer { alerts ->
            recycleview_alerts.also {
                it.layoutManager = GridLayoutManager(this, 2)
                it.setHasFixedSize(true)
                alertsAdapter = AlertsAdapter(alerts as ArrayList<AlertsDetailsResponse>, this)
                it.adapter = alertsAdapter
                searchAlerts()
            }
        })
    }*/

    private fun searchAlerts() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                alertsAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                alertsAdapter.filter.filter(query)
                return false
            }
        })
    }

    // onCreateOptionsMenu()
// Create options menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        searchView = menu.findItem(R.id.search_view).actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        super.onCreateOptionsMenu(menu)
        return true
    }

    // onOptionsItemSelected()
// "On click listener" for options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return false
    }

    override fun onRecyclerViewItemClick(view: View, labo: AlertsDetailsResponse) {
        // DetailsLaboBottomSheet().show(fragmentManager!!, "tessst")
    }


    private fun initSwipe() {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT) {
                    alertsAdapter.removeItem(position)
                } else {
                    //removeView()
                    // edit_position = position
                    //alertDialog.setTitle("Edit Country")
                    // et_country.setText(countries.get(position))
                    // alertDialog.show()
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val icon: Bitmap
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val height =
                        itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3
                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"))
                        val background = RectF(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            dX,
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, p)
                        icon =
                            BitmapFactory.decodeResource(resources, R.drawable.ic_edit_white)
                        val icon_dest = RectF(
                            itemView.left.toFloat() + width,
                            itemView.top.toFloat() + width,
                            itemView.left.toFloat() + 2 * width,
                            itemView.bottom.toFloat() - width
                        )
                        c.drawBitmap(icon, null, icon_dest, p)
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"))
                        val background = RectF(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, p)
                        icon =
                            BitmapFactory.decodeResource(resources, R.drawable.ic_delete_white)
                        val icon_dest = RectF(
                            itemView.right.toFloat() - 2 * width,
                            itemView.top.toFloat() + width,
                            itemView.right.toFloat() - width,
                            itemView.bottom.toFloat() - width
                        )
                        c.drawBitmap(icon, null, icon_dest, p)
                    }
                }
                super.onChildDraw(
                    c,
                    recyclerView!!,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recycleview_alerts)
    }


}


