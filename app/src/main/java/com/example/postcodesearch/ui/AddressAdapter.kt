package com.example.postcodesearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.postcodesearch.R
import com.example.postcodesearch.data.AddressData

class AddressAdapter :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    var address: List<AddressData>? = null
    set(value) {
        if(!value.isNullOrEmpty()){
            field = value
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_address_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        address?.let { list ->
            holder.postal.text = list[position].postalCode
            holder.name.text = list[position].localName
        }
    }

    override fun getItemCount() = address?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val postal: TextView = view.findViewById(R.id.postal)
    }
}