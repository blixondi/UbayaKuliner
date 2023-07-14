package com.shem.ubayafood.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.shem.ubayafood.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


fun ImageView.loadImage(url: String?, progressBar: ProgressBar) {
    Picasso.get()
        .load(url)
        .resize(400, 400)
        .centerCrop()
        .error(R.drawable.baseline_error_24)
        .into(this, object: Callback {
            override fun onSuccess() {
                progressBar.visibility=View.GONE
            }

            override fun onError(e: Exception?) {
                Log.e("errorcheck",e?.message.toString())
            }
        })

}

@BindingAdapter("android:imageUrl","android:progressBar")
fun loadPhotoURL(view: ImageView, url:String?, pb:ProgressBar){
    view.loadImage(url, pb)
}