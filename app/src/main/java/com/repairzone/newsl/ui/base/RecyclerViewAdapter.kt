package com.repairzone.newsl.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class RecyclerViewAdapter <T : OnClickEvent>(
    private val items: ArrayList<T>,
    private val itemLayoutId: Int,
    private val onItemClick: (Int, T, Int) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, itemLayoutId, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        item.onClick = {type -> onItemClick(type, item, position)}
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
