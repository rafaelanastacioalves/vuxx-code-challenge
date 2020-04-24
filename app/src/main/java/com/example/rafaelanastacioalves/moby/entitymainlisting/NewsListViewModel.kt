package com.example.rafaelanastacioalves.moby.newsmainlisting;

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rafaelanastacioalves.moby.domain.entities.New
import com.example.rafaelanastacioalves.moby.domain.interactors.NewsDetailsBaseInteractor
import com.example.rafaelanastacioalves.moby.domain.interactors.NewsListBaseInteractor
import com.example.rafaelanastacioalves.moby.repository.Resource


class NewsListViewModel : ViewModel() {

    val mainNewsList = MutableLiveData<Resource<List<Long>>>();
    val newLiveData = MutableLiveData<Resource<New>>()

    val mainNewsListInteractor: NewsListBaseInteractor = NewsListBaseInteractor()
    val newInteractor: NewsDetailsBaseInteractor = NewsDetailsBaseInteractor()



    fun loadData() : MutableLiveData<Resource<List<Long>>> {
        mainNewsListInteractor.execute(viewModelScope,null, {
            handleList(it)
        })

        return mainNewsList

    }

    private fun handleList(it: Resource<List<Long>>) {
        mainNewsList.postValue(it)
    }

    fun loadNew(newId: Long): MutableLiveData<Resource<New>> {
        newInteractor.execute(viewModelScope, NewsDetailsBaseInteractor.RequestValues(newId), {
            handleNew(it)
        })
        return newLiveData;
    }

    private fun handleNew(new: Resource<New>?) {
        newLiveData.postValue(new)
    }

}
