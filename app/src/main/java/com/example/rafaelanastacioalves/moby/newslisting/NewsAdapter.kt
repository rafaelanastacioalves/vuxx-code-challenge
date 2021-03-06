package com.example.rafaelanastacioalves.moby.newslisting

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rafaelanastacioalves.moby.R
import com.example.rafaelanastacioalves.moby.listeners.RecyclerViewClickListener

class NewsAdapter(context: Context) : RecyclerView.Adapter<NewViewHolder>() {
    private lateinit var itemDismissClickListener: RecyclerViewClickListener
    private lateinit var itemClickListener: RecyclerViewClickListener
    private var items: List<Long>? = null
    private val mContext: Context = context

    fun setRecyclerViewClickListener(aRVC: RecyclerViewClickListener) {
        this.itemClickListener = aRVC
    }

    fun setItemDismissClickListener(itemClickListener: RecyclerViewClickListener) {
        this.itemDismissClickListener = itemClickListener
    }

    fun getItems(): List<Long>? {
        return this.items
    }

    fun setItems(items: List<Long>) {
        this.items = items as ArrayList<Long>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        return NewViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.detail_news_viewholder,
                        parent,
                        false),
                itemClickListener,
                itemDismissClickListener)
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        val aRepoW = getItems()?.get(position) as Long
        holder.bind(aRepoW, mContext)
    }

    override fun getItemCount(): Int {
        return if (getItems() != null) {
            getItems()!!.size
        } else {
            0
        }
    }
}

