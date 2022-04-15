package com.repairzone.newsl.utils.ext

import android.text.format.DateUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@BindingAdapter("app:TimeSpan")
fun timeSpan(view: TextView, date: String?){
    if (!date.equals("") || date != null || date != ""){
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val parsed = date?.let { formatter.parse(it) }
        parsed?.let {
            val time = DateUtils.getRelativeTimeSpanString(parsed.time)
            view.text = time
        }
    }
}