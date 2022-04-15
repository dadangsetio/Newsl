package com.repairzone.newsl.ui.base

import android.graphics.Color.alpha
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.repairzone.newsl.R

class PagedRecyclerAdapter<T: OnClickActionModel>(
    private val mainLayoutId: Int,
    private val footerLayoutId: Int? = null,
    comparator: DiffUtil.ItemCallback<T>,
    private val onItemClicked: (Int, T?, Int) -> Unit
): PagingDataAdapter<T, ViewHolder>(comparator) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            holder.bind(it)
            it?.onClick = {type -> onItemClicked(type, it, position)}
        }
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.alpha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = when(viewType){
            TYPE_FOOTER -> {
                DataBindingUtil.inflate(inflater, footerLayoutId!!, parent, false)
            }
            else ->  {
                DataBindingUtil.inflate(inflater, mainLayoutId, parent, false)
            }
        }
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is T)
            TYPE_CONTENT
        else
            TYPE_FOOTER
    }

    companion object{
        const val TYPE_CONTENT = 0
        const val TYPE_FOOTER = 1
    }
}