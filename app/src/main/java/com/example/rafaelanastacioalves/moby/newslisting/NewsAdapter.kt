package com.example.rafaelanastacioalves.moby.newslisting

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rafaelanastacioalves.moby.R
import com.example.rafaelanastacioalves.moby.listeners.RecyclerViewClickListener

class NewsAdapter(context: Context) : RecyclerView.Adapter<NewViewHolder>() {
    private lateinit var recyclerViewClickListener: RecyclerViewClickListener
    private var items: List<Long>? = null

    private val mContext: Context = context

    fun setRecyclerViewClickListener(aRVC: RecyclerViewClickListener) {
        this.recyclerViewClickListener = aRVC
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
                .inflate(R.layout.detail_news_viewholder, parent, false), recyclerViewClickListener)
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        val aRepoW = getItems()?.get(position) as Long
        holder.bind(aRepoW, mContext)
    }

    override fun getItemCount(): Int {
        if (getItems() != null) {
            return getItems()!!.size
        } else {
            return 0
        }
    }
}

