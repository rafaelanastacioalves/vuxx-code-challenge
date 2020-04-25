package com.example.rafaelanastacioalves.moby.newslisting

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.rafaelanastacioalves.moby.R
import com.example.rafaelanastacioalves.moby.listeners.RecyclerViewClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.detail_news_viewholder.view.*

class NewViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private lateinit var itemDismissListener: RecyclerViewClickListener
    private lateinit var aRecyclerViewListener: RecyclerViewClickListener


    constructor(itemView: View,
                itemclickListener: RecyclerViewClickListener,
                itemDismissListener: RecyclerViewClickListener) : this(itemView) {
        this.aRecyclerViewListener = itemclickListener
        this.itemDismissListener = itemDismissListener
    }

    init {
        itemView.detail_container.setOnClickListener { view ->
            aRecyclerViewListener.onClick(view,
                    adapterPosition)
        }
        itemView.new_dismiss.setOnClickListener { view ->
            itemDismissListener.onClick(view,
                    adapterPosition)
        }
    }

     fun bind(newID: Long, context: Context) {
        itemView.news_detail_title_textview.text = context.getString(R.string.article_id_string
                , (adapterPosition + 1).toString()
                , newID.toString()
        )
    }
}
