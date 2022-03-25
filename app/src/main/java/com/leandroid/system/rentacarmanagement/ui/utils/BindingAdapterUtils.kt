package com.leandroid.system.rentacarmanagement.ui.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapterUtils {

//    @BindingAdapter("imageNewsUrl", "imagePlaceholder", "withoutImage")
//    @JvmStatic
//    fun loadImageNews(view: ImageView, url: String?, placeHolder: Int, withoutImage: Int) {
//        val image = if(url.isNullOrEmpty()) withoutImage else url
//        val context = view.context
//        Glide.with(context)
//            .load(image)
//            .placeholder(placeHolder)
//            .error(R.drawable.ic_image_error)
//            .into(view)
//    }



    @BindingAdapter("imageUrl", "imageResource", "imageError")
    @JvmStatic
    fun loadImageWithError(view: ImageView, url: String?, placeHolder: Int, error: Int) {
        url?.let {image ->
            val context = view.context
            if (image.isNotEmpty()) {
                Glide.with(context)
                    .load(image)
                    .placeholder(placeHolder)
                    .error(error)
                    .into(view)
            } else {
                Glide.with(context)
                    .load(placeHolder)
                    .error(error)
                    .into(view)
            }
        }
    }


//    @BindingAdapter("imageUrl", "imagePlaceholder", "withoutImage")
//    @JvmStatic
//    fun loadImage(view: ImageView, url: String?, placeHolder: Int, withoutImage: Int) {
//        val image = if(url.isNullOrEmpty()) withoutImage else url
//        val context = view.context
//        Glide.with(context)
//            .load(image)
//            .placeholder(placeHolder)
//            //.override(50, 50)
//            //.dontAnimate()
//            .error(R.drawable.ic_image_error)
//            .into(view)
////        Glide
////                    .with(context)
////                    .load(image)
////                    .apply(RequestOptions.noTransformation()
////                        .placeholder(placeHolder)
////                        //.override(40, 40)
////                        //.dontAnimate()
////                        .error(R.drawable.ic_image_error)
////                        .priority(Priority.HIGH))
////                    .into(view)
//    }
//
//
//
//    @BindingAdapter("iconResource", "imageResource")
//    @JvmStatic
//    fun loadImageFromResource(view: ImageView, resource: String, placeHolder: Int) {
//        val context = view.context
//        val id = context.resources.getIdentifier(resource, "drawable", context.packageName)
//        Glide.with(context)
//            .load(id)
//            .placeholder(placeHolder)
//            .override(50, 50)
//            .dontAnimate()
//            .error(R.drawable.ic_image_error)
//            .into(view)
//    }
//
//    @BindingAdapter("iconResource")
//    @JvmStatic
//    fun loadImageFromDrawable(view: TextView, resource: String) {
//        val context = view.context
//        val id = context.resources.getIdentifier(resource, "drawable", context.packageName)
//        view.setCompoundDrawables(null, null, context.getDrawable(id), null)
////        Glide.with(context)
////            .load(id)
////            .placeholder(placeHolder)
////            .override(50, 50)
////            .dontAnimate()
////            .error(R.drawable.ic_image_error)
////            .into(view)
//    }
}