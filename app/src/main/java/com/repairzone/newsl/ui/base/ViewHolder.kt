package com.repairzone.newsl.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.repairzone.newsl.BR

open class ViewHolder(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
    fun <T> bind(item: T){
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}