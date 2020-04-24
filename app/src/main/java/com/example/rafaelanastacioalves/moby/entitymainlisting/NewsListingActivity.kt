package com.example.rafaelanastacioalves.moby.newsmainlisting


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rafaelanastacioalves.moby.R
import com.example.rafaelanastacioalves.moby.domain.entities.New
import com.example.rafaelanastacioalves.moby.listeners.RecyclerViewClickListener
import com.example.rafaelanastacioalves.moby.repository.Resource
import timber.log.Timber


class NewsListingActivity : AppCompatActivity(), RecyclerViewClickListener {

    private val mClickListener = this
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(this)
    }
    private var mRecyclerView: RecyclerView? = null
    lateinit private var mNewsListViewModel: NewsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setupRecyclerView()
        subscribe()

    }


    private fun subscribe() {
        mNewsListViewModel = ViewModelProvider.NewInstanceFactory()
                .create(NewsListViewModel::class.java)
        mNewsListViewModel.loadData().observeForever(Observer { mainEntities ->
            Timber.d("On Changed")
            populateRecyclerView(mainEntities)
        })
    }

    private fun setupViews() {
        setContentView(R.layout.activity_main)
        Timber.tag("LifeCycles")
        Timber.i("onCreate Activity")
    }

    private fun setupRecyclerView() {
        mRecyclerView = findViewById<View>(R.id.main_news_list) as RecyclerView
        val layoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView!!.layoutManager = layoutManager
        newsAdapter!!.setRecyclerViewClickListener(mClickListener)
        mRecyclerView!!.adapter = newsAdapter
    }


    private fun populateRecyclerView(listResource: Resource<List<Long>>) {
        if (listResource.status == Resource.Status.GENERIC_ERROR) {
            //TODO add any error managing
            Timber.w("Nothing returned from Main News List API. Reason:\n ${listResource.message}")

        } else if (listResource.status == Resource.Status.SUCCESS) {
            listResource.data?.let { newsAdapter!!.setItems(it) }
        }
    }


    override fun onClick(view: View, position: Int) {
        val newId = newsAdapter.getItems()?.get(position)
        newId?.let {
            mNewsListViewModel.loadNew(newId).observeForever(Observer { new ->
                Timber.d("On Changed")
                openNew(new)
            })
        }
    }

    private fun openNew(new: Resource<New>) {
        new.data?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            startActivity(browserIntent)
        }
    }


}
