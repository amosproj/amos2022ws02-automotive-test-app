package com.amos.infotaimos.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amos.infotaimos.databinding.LandingPageTileBinding

class LandingPageTileAdapter(private val tiles: List<LandingPageTileData>, val tileTapListener: (LandingPageTileType) -> Unit): RecyclerView.Adapter<LandingPageTileAdapter.LandingTileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandingTileViewHolder {
        val binding = LandingPageTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = LandingTileViewHolder(binding)
        holder.onClick { adapterPosition ->
            tileTapListener(tiles[adapterPosition].type)
        }
        return holder
    }

    override fun onBindViewHolder(holder: LandingTileViewHolder, position: Int) {
        holder.bindValue(tiles[position])
    }

    override fun getItemCount(): Int {
        return tiles.size
    }

    class LandingTileViewHolder(private val holder: LandingPageTileBinding): RecyclerView.ViewHolder(holder.root) {

        fun bindValue(tile: LandingPageTileData) {
            holder.root.id = tile.type.getID()
            holder.TileImage.setImageResource(tile.getIconResource())
            holder.TileText.setText(tile.getStringResource())
            holder.tileLayout.setBackgroundColor(holder.root.context.getColor(tile.getColor()))
        }
    }
}