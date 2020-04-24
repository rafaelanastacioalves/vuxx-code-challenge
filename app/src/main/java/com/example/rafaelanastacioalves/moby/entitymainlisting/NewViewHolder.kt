package com.example.rafaelanastacioalves.moby.newsmainlisting;

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.rafaelanastacioalves.moby.listeners.RecyclerViewClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.detail_news_viewholder.view.*

class NewViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), View.OnClickListener, LayoutContainer{

    lateinit private var aRecyclerViewListener: RecyclerViewClickListener


    constructor(itemView: View , clickListener: RecyclerViewClickListener) : this(itemView) {
        this.aRecyclerViewListener = clickListener
    }
    init {
        itemView.detail_container.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        aRecyclerViewListener.onClick(v, getAdapterPosition());
    }

    fun bind(newID: Long, context: Context) {
        itemView.news_detail_title_textview.setText(newID.toString());
//        val placeholderList: StateListDrawable= context.getResources().getDrawable(R.drawable.ic_placeholder_map_selector) as StateListDrawable;
//        Picasso.get()
//                .load(newID.getImage_url())
//                .placeholder(placeholderList)
//                .into(itemView.main_news_imageview as ImageView);

    }
}
