package com.example.rafaelanastacioalves.moby.repository

import com.example.rafaelanastacioalves.moby.domain.entities.New
import com.example.rafaelanastacioalves.moby.repository.database.DAO
import com.example.rafaelanastacioalves.moby.repository.http.APIClient
import com.example.rafaelanastacioalves.moby.repository.http.ServiceGenerator

object AppRepository {

    suspend fun getNewsIdsList(): Resource<List<Long>> {
        return object : NetworkBoundResource<List<Long>, List<Long>>() {
            override suspend fun makeCall(): List<Long> {

                var apiClient: APIClient = ServiceGenerator.createService(APIClient::class.java);
                return apiClient.getNewsList()
            }

            override suspend fun getFromDB(): List<Long>? {
                return DAO.getNewsList()
            }

            override fun saveIntoDB(resultData: List<Long>?) {
                DAO.saveNewsList(resultData)
            }

        }.fromHttpAndDB()
    }

    suspend fun getNewDetails(requestId: Long): Resource<New> {
        return object : NetworkBoundResource<New, New>() {
            override suspend fun makeCall(): New? {
                var apiClient: APIClient = ServiceGenerator.createService(APIClient::class.java)
                return apiClient.getNewsDetails(requestId)
            }

            override suspend fun getFromDB(): New? {
                return DAO.getNewDetails(requestId.toString())
            }

            override fun saveIntoDB(resultData: New?) {
                resultData?.let{DAO.saveNewsDetail(resultData)}
            }
        }.fromHttpOnly()
    }
}