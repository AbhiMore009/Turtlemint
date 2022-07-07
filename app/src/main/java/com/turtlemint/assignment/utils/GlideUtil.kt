package com.turtlemint.assignment.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.turtlemint.assignment.R

class GlideUtil {

    companion object {

        @JvmStatic // add this line !!
        @BindingAdapter("imageUrl")
        fun loadImage(view: ShapeableImageView, url: String?="") =
            Glide.with(view.context).load(url).into(view)
    }
}