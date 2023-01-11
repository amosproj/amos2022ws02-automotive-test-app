package com.amos.infotaimos.model

import androidx.recyclerview.widget.RecyclerView

fun <T : RecyclerView.ViewHolder> T.onClick(event: (position: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke( adapterPosition)
    }
    return this
}
