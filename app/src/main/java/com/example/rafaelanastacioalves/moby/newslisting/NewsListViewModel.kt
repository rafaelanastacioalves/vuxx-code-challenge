package com.example.rafaelanastacioalves.moby.newslisting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rafaelanastacioalves.moby.domain.entities.New
import com.example.rafaelanastacioalves.moby.domain.interactors.NewsDetailsInteractor
import com.example.rafaelanastacioalves.moby.domain.interactors.NewsListInteractor
import com.example.rafaelanastacioalves.moby.repository.Resource


class NewsListViewModel : ViewModel() {

    val newsList: MutableLiveData<Resource<List<Long>?>> = MutableLiveData<Resource<List<Long>?>>()
    val newLiveData = MutableLiveData<Resource<New?>>()

    val newsListInteractor: NewsListInteractor = NewsListInteractor()
    val newInteractor: NewsDetailsInteractor = NewsDetailsInteractor()


    fun loadData(): MutableLiveData<Resource<List<Long>?>> {
        newLiveData.postValue(Resource.loading())
        newsListInteractor.execute(viewModelScope, null, {
            handleList(it)
        })

        return newsList
    }

    private fun handleList(it: Resource<List<Long>?>) {
        newsList.postValue(it)
    }

    fun loadNew(newId: Long): MutableLiveData<Resource<New?>> {
        newLiveData.postValue(Resource.loading())
        newInteractor.execute(viewModelScope, NewsDetailsInteractor.RequestValues(newId), {
            handleNew(it)
        })
        return newLiveData
    }

    private fun handleNew(new: Resource<New?>?) {
        newLiveData.postValue(new)
    }

}
