package com.tripian.trpprovider.util.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class CardView @JvmOverloads constructor(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {

    init {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            cardElevation = 0f
            maxCardElevation = 0f
            preventCornerOverlap = false
        }
    }

}