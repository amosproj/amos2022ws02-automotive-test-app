package com.amos.infotaimos.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.amos.infotaimos.R
import com.amos.infotaimos.databinding.LandingPageTileBinding

class LandingPageTile(context: Context, attrs: AttributeSet) : CardView(context, attrs) {

    init {
        var binding = LandingPageTileBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.LandingPageTile)
        binding.TileImage.setImageDrawable(attributes.getDrawable(R.styleable.LandingPageTile_tileIcon))
        binding.TileText.text = attributes.getText(R.styleable.LandingPageTile_tileText)
        binding.tileLayout.setBackgroundColor(
            attributes.getColor(
                R.styleable.LandingPageTile_tileColor,
                resources.getColor(R.color.landing_green, context.theme)
            )
        )
        attributes.recycle()
    }
}
