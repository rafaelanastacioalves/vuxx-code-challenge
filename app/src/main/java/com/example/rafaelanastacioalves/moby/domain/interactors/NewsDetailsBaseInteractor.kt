package com.example.rafaelanastacioalves.moby.domain.interactors


import com.example.rafaelanastacioalves.moby.domain.entities.New
import com.example.rafaelanastacioalves.moby.repository.AppRepository
import com.example.rafaelanastacioalves.moby.repository.Resource

class NewsDetailsBaseInteractor :
        BaseInteractor<Resource<New>?, NewsDetailsBaseInteractor.RequestValues>() {
    val appRepository: AppRepository

    init {
        appRepository = AppRepository
    }

    override suspend fun run(requestValue: NewsDetailsBaseInteractor.RequestValues?): Resource<New>? {
        var result = requestValue?.requestId?.let { appRepository.getNewDetails(it) }
        return result
    }

    class RequestValues(val requestId: Long) : BaseInteractor.RequestValues

}
