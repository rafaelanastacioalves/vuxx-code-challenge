package com.example.rafaelanastacioalves.moby.newslisting

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.rafaelanastacioalves.moby.R
import com.example.rafaelanastacioalves.moby.listeners.RecyclerViewClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.detail_news_viewholder.view.*

class NewViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), View.OnClickListener, LayoutContainer {

    private lateinit var aRecyclerViewListener: RecyclerViewClickListener


    constructor(itemView: View, clickListener: RecyclerViewClickListener) : this(itemView) {
        this.aRecyclerViewListener = clickListener
    }

    init {
        itemView.detail_container.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        aRecyclerViewListener.onClick(v, adapterPosition)
    }


    fun bind(newID: Long, context: Context) {

        itemView.news_detail_title_textview.text = context.getString(R.string.article_id_string
                , (adapterPosition + 1).toString()
                , newID.toString()
        )
    }
}
