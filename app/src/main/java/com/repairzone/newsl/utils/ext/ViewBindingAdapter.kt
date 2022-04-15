package com.repairzone.newsl.utils.ext

import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:loadState")
fun state(view: View, state: LoadState?){
    state?.let {
        view.visibility = if (state is LoadState.Loading){
            View.VISIBLE
        }else{
            View.GONE
        }
    }
}