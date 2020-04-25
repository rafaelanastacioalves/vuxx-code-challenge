package com.example.rafaelanastacioalves.moby.domain.interactors

import com.example.rafaelanastacioalves.moby.repository.AppRepository
import com.example.rafaelanastacioalves.moby.repository.Resource
import com.example.rafaelanastacioalves.moby.repository.Resource.Status.GENERIC_ERROR
import com.example.rafaelanastacioalves.moby.repository.Resource.Status.SUCCESS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class NewsListInteractor :
        BaseInteractor<Resource<List<Long>?>, NewsListInteractor.RequestValues>() {

    private val LIST_QUANTITY = 20
    val appRepository: AppRepository

    init {
        appRepository = AppRepository
    }

    override suspend fun run(requestValues: RequestValues?): Resource<List<Long>?> {
        var newsIdListResource: Resource<List<Long>?> = Resource.loading()
        withContext(Dispatchers.IO) {
            val deferredOne = async { appRepository.getNewsIdsList() }
            val deferredTwo = async { appRepository.getReadNewsIdsList() }
            newsIdListResource = deferredOne.await()
            val readNewsIdListResource: Resource<List<Long>?> = deferredTwo.await()
            if (newsIdListResource.status == SUCCESS &&
                    readNewsIdListResource.status == SUCCESS) {
                newsIdListResource.data = transform(newsIdListResource.data, readNewsIdListResource.data)
            }
            if (readNewsIdListResource.status == GENERIC_ERROR) {
                newsIdListResource.status = GENERIC_ERROR
                newsIdListResource.message = readNewsIdListResource.message
            }
        }
        return newsIdListResource
    }

    private fun transform(completeList: List<Long>?, readList: List<Long>?): List<Long>? {
        val listWithoutReadItems = mutableListOf<Long>()
        if (completeList != null && readList != null) {
             listWithoutReadItems.addAll(completeList.minus(readList))
        }
        return ArrayList<Long>(listWithoutReadItems?.subList(0, LIST_QUANTITY))
    }

    class RequestValues : BaseInteractor.RequestValues// in this case we don't need nothing for this use case
}
