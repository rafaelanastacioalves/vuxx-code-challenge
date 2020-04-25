package com.example.rafaelanastacioalves.moby.newslisting


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rafaelanastacioalves.moby.R
import com.example.rafaelanastacioalves.moby.domain.entities.New
import com.example.rafaelanastacioalves.moby.listeners.RecyclerViewClickListener
import com.example.rafaelanastacioalves.moby.repository.Resource
import kotlinx.android.synthetic.main.activity_news_listing.*
import timber.log.Timber


class NewsListingActivity : AppCompatActivity() {

    private lateinit var mDismissClickListener: RecyclerViewClickListener
    private lateinit var itemClickLIstener: RecyclerViewClickListener
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(this)
    }
    private var mRecyclerView: RecyclerView? = null
    private lateinit var mNewsListViewModel: NewsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setupListerners()
        setupRecyclerView()
        subscribe()

    }

    private fun subscribe() {
        mNewsListViewModel = ViewModelProvider.NewInstanceFactory()
                .create(NewsListViewModel::class.java)
        mNewsListViewModel.loadData().observeForever({ newsResource ->
            Timber.d("On Changed")
            onNewsListReceived(newsResource)
        })
    }


    private fun setupListerners() {
        mDismissClickListener = object : RecyclerViewClickListener {
            override fun onClick(view: View, position: Int) {
                val newId = newsAdapter.getItems()?.get(position)
                newId?.let {
                    markAsRead(newId)
                }
            }
        }

        itemClickLIstener = object : RecyclerViewClickListener {
            override fun onClick(view: View, position: Int) {
                val newId = newsAdapter.getItems()?.get(position)
                newId?.let {
                    loadNews(newId)
                }
            }
        }
    }

    private fun setupViews() {
        setContentView(R.layout.activity_news_listing)
        Timber.tag("LifeCycles")
        Timber.i("onCreate Activity")
    }

    private fun setupRecyclerView() {
        mRecyclerView = findViewById<View>(R.id.news_list) as RecyclerView
        val layoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView!!.layoutManager = layoutManager
        newsAdapter.setRecyclerViewClickListener(itemClickLIstener)
        newsAdapter.setItemDismissClickListener(mDismissClickListener)
        mRecyclerView!!.adapter = newsAdapter
    }

    private fun onNewsListReceived(listResource: Resource<List<Long>?>) {
        when (listResource.status) {
            Resource.Status.GENERIC_ERROR -> {
                //TODO add any error managing
                hideProgressBar()
                showError()
                Timber.w("Nothing returned from Main News List API. Reason:\n ${listResource.message}")
            }
            Resource.Status.LOADING -> showProgressBar()
            Resource.Status.SUCCESS -> {
                listResource.data?.let {
                    newsAdapter.setItems(it)
                }
            }
            Resource.Status.INTERNAL_SERVER_ERROR -> {
                hideProgressBar()
                showError()
            }
        }
    }


    private fun showError() {
        news_loading_error.visibility = View.VISIBLE
    }

    private fun hideError() {
        news_loading_error.visibility = View.GONE
    }

    private fun hideProgressBar() {
        progress_bar.hide()
    }

    private fun showProgressBar() {
        progress_bar.show()
    }

    private fun loadNews(newId: Long) {
        mNewsListViewModel.loadNew(newId).observeForever { newResource ->
            when (newResource.status) {
                Resource.Status.LOADING -> {
                    hideNewsList()
                    showProgressBar()
                }
                Resource.Status.SUCCESS -> {
                    showNewsList()
                    hideProgressBar()
                    openNew(newResource)
                }
                Resource.Status.INTERNAL_SERVER_ERROR -> {
                    hideNewsList()
                    hideProgressBar()
                    showError()
                }
                Resource.Status.GENERIC_ERROR -> {
                    hideNewsList()
                    hideProgressBar()
                    showError()
                }
            }
        }
    }

    private fun markAsRead(newId: Long) {
        mNewsListViewModel.markAsRead(newId).observeForever { newResource ->
            when (newResource.status) {
                Resource.Status.SUCCESS -> {
                    showNewsList()
                    hideProgressBar()
                    Toast.makeText(this,
                            getString(R.string.omitted_item)
                            , Toast.LENGTH_SHORT).show()
                }
                Resource.Status.INTERNAL_SERVER_ERROR -> {
                    hideNewsList()
                    showError()
                }
                Resource.Status.GENERIC_ERROR -> {
                    hideNewsList()
                    showError()
                }
                Resource.Status.LOADING -> {
                    hideNewsList()
                    showProgressBar()
                }
            }

        }
    }

    private fun showNewsList() {
        news_list.visibility = View.VISIBLE
    }

    private fun hideNewsList() {
        news_list.visibility = View.GONE
    }

    private fun openNew(new: Resource<New?>) {
        new.data?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            startActivity(browserIntent)
        }
    }


}
