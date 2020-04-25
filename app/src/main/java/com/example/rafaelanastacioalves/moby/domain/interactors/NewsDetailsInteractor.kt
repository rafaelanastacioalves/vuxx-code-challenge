package com.example.rafaelanastacioalves.moby.domain.interactors


import com.example.rafaelanastacioalves.moby.domain.entities.New
import com.example.rafaelanastacioalves.moby.repository.AppRepository
import com.example.rafaelanastacioalves.moby.repository.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsDetailsInteractor :
        BaseInteractor<Resource<New?>, NewsDetailsInteractor.RequestValues>() {
    val appRepository: AppRepository

    init {
        appRepository = AppRepository
    }

    override suspend fun run(requestValue: NewsDetailsInteractor.RequestValues?): Resource<New?> {
        var result: Resource<New?> = Resource.loading()
        withContext(Dispatchers.IO) {
            requestValue?.requestId?.let { result = appRepository.getNewDetails(it) }
        }
        return result
    }

    class RequestValues(val requestId: Long) : BaseInteractor.RequestValues

}
