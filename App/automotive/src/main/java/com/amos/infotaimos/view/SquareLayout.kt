package com.amos.infotaimos.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/*+
    * This class is used to create a square layout.
    * @param context The context of the application.
    * @param attrs The attributes of the layout.
 */
class SquareLayout(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val min = widthMeasureSpec.coerceAtMost(heightMeasureSpec)
        super.onMeasure(min, min)
    }
}