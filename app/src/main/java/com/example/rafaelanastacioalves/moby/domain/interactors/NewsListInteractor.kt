package com.example.rafaelanastacioalves.moby.domain.interactors

import com.example.rafaelanastacioalves.moby.repository.AppRepository
import com.example.rafaelanastacioalves.moby.repository.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class NewsListInteractor :
        BaseInteractor<Resource<List<Long>>, NewsListInteractor.RequestValues>() {
    val appRepository: AppRepository

    init {
        appRepository = AppRepository
    }

    override suspend fun run(requestValues: RequestValues?): Resource<List<Long>> {
        var result: Resource<List<Long>> = Resource.loading()
        withContext(Dispatchers.IO) {
            val deferredOne = async { appRepository.getNewsIdsList() }
            result = deferredOne.await()
        }
        return result
    }

    class RequestValues : BaseInteractor.RequestValues// in this case we don't need nothing for this use case
}
