package com.example.rafaelanastacioalves.moby.newslisting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rafaelanastacioalves.moby.domain.entities.New
import com.example.rafaelanastacioalves.moby.domain.interactors.MarkItemAsReadIInteractor
import com.example.rafaelanastacioalves.moby.domain.interactors.NewsDetailsInteractor
import com.example.rafaelanastacioalves.moby.domain.interactors.NewsListInteractor
import com.example.rafaelanastacioalves.moby.repository.Resource


class NewsListViewModel : ViewModel() {

    private val newsList: MutableLiveData<Resource<List<Long>?>> = MutableLiveData<Resource<List<Long>?>>()
    private val newLiveData = MutableLiveData<Resource<New?>>()
    private val markAsReadLiveData = MutableLiveData<Resource<Boolean?>>()

    private val newsListInteractor: NewsListInteractor = NewsListInteractor()
    private val newInteractor: NewsDetailsInteractor = NewsDetailsInteractor()
    private val markAsReadInteractor: MarkItemAsReadIInteractor = MarkItemAsReadIInteractor()

    fun loadData(): MutableLiveData<Resource<List<Long>?>> {
        newLiveData.postValue(Resource.loading())
        newsListInteractor.execute(viewModelScope, null) {
            handleList(it)
        }
        return newsList
    }

    private fun handleList(it: Resource<List<Long>?>) {
        newsList.postValue(it)
    }

    fun loadNew(newId: Long): MutableLiveData<Resource<New?>> {
        newLiveData.postValue(Resource.loading())
        newInteractor.execute(viewModelScope, NewsDetailsInteractor.RequestValues(newId)) {
            handleNew(it)
        }
        return newLiveData
    }

    private fun handleNew(new: Resource<New?>?) {
        newLiveData.postValue(new)
    }

    fun markAsRead(newId: Long): MutableLiveData<Resource<Boolean?>> {
        markAsReadLiveData.postValue(Resource.loading())
        markAsReadInteractor.execute(viewModelScope, MarkItemAsReadIInteractor.RequestValues(newId)) {
            handleMarkItem(it)
        }
        return markAsReadLiveData
    }

    private fun handleMarkItem(resource: Resource<Boolean?>) {
        if (resource.status == Resource.Status.SUCCESS) {
            loadData()
        }
        markAsReadLiveData.postValue(resource)
    }
}
