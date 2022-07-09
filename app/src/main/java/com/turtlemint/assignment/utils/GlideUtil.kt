package com.turtlemint.assignment.utils

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class GlideUtil {

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ShapeableImageView, url: String? = "") =
            Glide.with(view.context).load(url).into(view)
    }
}