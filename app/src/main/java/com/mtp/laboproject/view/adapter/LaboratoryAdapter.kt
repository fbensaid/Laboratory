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
import com.mtp.laboproject.listener.LaboratoryClickListener
import com.mtp.laboproject.data.model.labs.LabsObjectResponse
import com.mtp.laboproject.databinding.RecycleviewLaboratoryBinding
import com.squareup.picasso.Picasso

class LaboratoryAdapter(
    private val listofLaboratory: List<LabsObjectResponse>,
    private val listner: LaboratoryClickListener

) : RecyclerView.Adapter<LaboratoryAdapter.LaboratoryViewHolder>(), Filterable {
    private var filtredListofLaboratory = listofLaboratory
    private var resultListOfSearch = arrayListOf<LabsObjectResponse>()

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                filtredListofLaboratory = if (p0.isNullOrEmpty())
                    listofLaboratory
                else {
                    resultListOfSearch.clear()
                    listofLaboratory.forEach {
                        if (it.toString().toLowerCase().contains(p0.toString()))
                            resultListOfSearch.add(it)
                    }
                    resultListOfSearch
                }
                var filtredResult = FilterResults()
                filtredResult.values = filtredListofLaboratory
                return filtredResult
            }

            override fun publishResults(p0: CharSequence?, filtredResult: FilterResults?) {
                filtredListofLaboratory = listOf()
                filtredListofLaboratory = filtredResult!!.values as List<LabsObjectResponse>
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
        return filtredListofLaboratory.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LaboratoryViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycleview_laboratory,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: LaboratoryViewHolder, position: Int) {
        holder.recycleviewLaboratoryBinding.laboratoryResponseData =
            filtredListofLaboratory[position]

        holder.recycleviewLaboratoryBinding.cardViewLabo.setOnClickListener {
            listner.onRecyclerViewItemClick(
                holder.recycleviewLaboratoryBinding.cardViewLabo,
                filtredListofLaboratory[position]
            )
        }

    }


    inner class LaboratoryViewHolder(
        val recycleviewLaboratoryBinding: RecycleviewLaboratoryBinding
    ) : RecyclerView.ViewHolder(recycleviewLaboratoryBinding.root)
}


