package com.repairzone.newsl.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.repairzone.newsl.BR

class PagingLoadStateAdapter<T: Any, VH: RecyclerView.ViewHolder>(
    @LayoutRes private val layoutId: Int,
    private val adapter: PagingDataAdapter<T, VH>
): LoadStateAdapter<PagingLoadStateAdapter.NetworkStateItemViewHolder>() {

    class NetworkStateItemViewHolder(
        private val binding: ViewDataBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(loadState: LoadState){
            binding.setVariable(BR.state, loadState)
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
        return NetworkStateItemViewHolder(binding)
    }

}