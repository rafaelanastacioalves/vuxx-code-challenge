package com.example.rafaelanastacioalves.moby.domain.interactors

import com.example.rafaelanastacioalves.moby.repository.AppRepository
import com.example.rafaelanastacioalves.moby.repository.Resource

class MarkItemAsReadIInteractor :
        BaseInteractor<Resource<Boolean?>, MarkItemAsReadIInteractor.RequestValues>() {
    val appRepository: AppRepository

    init {
        appRepository = AppRepository
    }

    override suspend fun run(requestValue: RequestValues?): Resource<Boolean?> {
        var result = Resource.loading<Boolean?>()
        requestValue?.requestId?.let { result = appRepository.marNewAsRead(it) }
        return result

    }

    class RequestValues(val requestId: Long) : BaseInteractor.RequestValues
}