package com.mtp.laboproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtp.laboproject.R
import com.mtp.laboproject.data.model.AlertsDetailsResponse
import com.mtp.laboproject.databinding.RecycleviewAlertBinding
import com.mtp.laboproject.databinding.RecycleviewLaboratoryBinding

import com.mtp.laboproject.listener.AlertsClickListener
import com.squareup.picasso.Picasso

class AlertsAdapter(
    private val listofAlerts: List<AlertsDetailsResponse>,
    private val listner: AlertsClickListener

) : RecyclerView.Adapter<AlertsAdapter.AlertsViewHolder>(), Filterable {
    private var filtredListofAlerts = listofAlerts
    private var resultListOfSearch = arrayListOf<AlertsDetailsResponse>()

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                filtredListofAlerts = if (p0.isNullOrEmpty())
                    listofAlerts
                else {
                    resultListOfSearch.clear()
                    listofAlerts.forEach {
                        if (it.title.toLowerCase().contains(p0.toString()))
                            resultListOfSearch.add(it)
                    }
                    resultListOfSearch
                }
                var filtredResult = FilterResults()
                filtredResult.values = filtredListofAlerts
                return filtredResult
            }

            override fun publishResults(p0: CharSequence?, filtredResult: FilterResults?) {
                filtredListofAlerts = listOf()
                filtredListofAlerts = filtredResult!!.values as List<AlertsDetailsResponse>
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageUri(view: ImageView, imageUri: String?) {
            Picasso.get().load(imageUri).into(view)
        }
    }

    override fun getItemCount(): Int {
        return filtredListofAlerts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AlertsViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycleview_alert,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: AlertsViewHolder, position: Int) {
        holder.recycleviewAlertsBinding.alertsListResponseData =
            filtredListofAlerts[position]

        holder.recycleviewAlertsBinding.cardView.setOnClickListener {
            listner.onRecyclerViewItemClick(
                holder.recycleviewAlertsBinding.cardView,
                filtredListofAlerts[position]
            )
        }

    }


    inner class AlertsViewHolder(
        val recycleviewAlertsBinding: RecycleviewAlertBinding
    ) : RecyclerView.ViewHolder(recycleviewAlertsBinding.root)
}


