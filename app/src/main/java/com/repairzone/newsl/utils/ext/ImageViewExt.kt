package com.repairzone.newsl.utils.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("app:loadUrl")
fun loadUrl(view: ImageView, url: String?){
    if (!url.equals("") || url != null || url != ""){
        val options = RequestOptions().transform(CenterCrop())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(false)
        Glide.with(view.context)
            .load(url)
            .apply(options)
            .centerCrop()
            .into(view)
    }
}