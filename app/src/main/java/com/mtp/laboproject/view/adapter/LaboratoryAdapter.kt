package com.mtp.laboproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtp.laboproject.R
import com.mtp.laboproject.data.model.LaboratoryResponse
import com.mtp.laboproject.databinding.RecycleviewLaboratoryBinding
import com.squareup.picasso.Picasso


class LaboratoryAdapter (
    private val listofLaboratory:List<LaboratoryResponse>

): RecyclerView.Adapter<LaboratoryAdapter.LaboratoryViewHolder>() {

    companion object {
        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageUri(view: ImageView, imageUri: String?) {
             Picasso.get().load(imageUri).into(view)
        }
    }

    override fun getItemCount()= listofLaboratory.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = LaboratoryViewHolder(
        DataBindingUtil.inflate<RecycleviewLaboratoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.recycleview_laboratory,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: LaboratoryViewHolder, position: Int) {
        holder.recycleviewLaboratoryBinding.laboratoryData=listofLaboratory[position]
    }


    inner class LaboratoryViewHolder(
        val recycleviewLaboratoryBinding:RecycleviewLaboratoryBinding
    ):RecyclerView.ViewHolder(recycleviewLaboratoryBinding.root)
}


