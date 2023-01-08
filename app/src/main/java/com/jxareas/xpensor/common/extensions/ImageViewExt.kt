package com.jxareas.xpensor.common.extensions

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.ImageViewCompat
import com.jxareas.xpensor.R
import com.jxareas.xpensor.common.utils.PreferenceUtils

private val mapOfDrawables = mapOf(
    0 to R.drawable.ic_family,
    1 to R.drawable.ic_finance,
    2 to R.drawable.ic_giftbox,
    3 to R.drawable.ic_grocery,
    4 to R.drawable.ic_health,
    5 to R.drawable.ic_house,
    6 to R.drawable.ic_leisure,
    7 to R.drawable.ic_shopping,
    8 to R.drawable.ic_transport,
    9 to R.drawable.ic_other,
    10 to R.drawable.ic_restaurant,
    11 to R.drawable.ic_services
)

fun getImageViewTint(imageView: ImageView): String {
    val colorInt = imageView.imageTintList?.defaultColor
    return if (colorInt != null)
        String.format("#%06X", 0xFFFFFF and colorInt)
    else PreferenceUtils.MAIN_COLOR
}

fun ImageView.setIcon(id: Int) {
    this.setImageResource(mapOfDrawables[id] ?: R.drawable.ic_image_error)
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    )
}

fun ImageView.setTint(value: String?) {
    DrawableCompat.setTint(this.drawable, Color.parseColor(value ?: PreferenceUtils.MAIN_COLOR))
}
