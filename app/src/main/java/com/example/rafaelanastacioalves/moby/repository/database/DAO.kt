package com.example.rafaelanastacioalves.moby.repository.database

import com.example.rafaelanastacioalves.moby.domain.entities.New
import com.orhanobut.hawk.Hawk

object DAO {
    private const val READ_NEWS_KEY: String = "BBBB"
    private const val NEWS_LIST_KEY: String = "AAAA"

    fun getNewsList(): List<Long>? {
        return Hawk.get(NEWS_LIST_KEY)
    }

    fun saveNewsList(resultData: List<Long>?) {
        val resultSuccessful = Hawk.put(NEWS_LIST_KEY, resultData)
        if (!resultSuccessful) {
            throw Exception()
        }
    }

    fun saveNewsDetail(resultData: New) {
        val resultSuccessful = Hawk.put(resultData.id.toString(), resultData)
        if (!resultSuccessful) {
            throw Exception()
        }
    }

    fun getNewDetails(key: String): New? {
        return Hawk.get(key)
    }

    fun getReadNewsIdsList(): List<Long>? {
        return Hawk.get(READ_NEWS_KEY) ?: mutableListOf()
    }

    fun saveReadNewsIdsList(resultData: List<Long>?) {
        val resultSuccessful = Hawk.put(READ_NEWS_KEY, resultData)
        if (!resultSuccessful) {
            throw Exception()
        }
    }
}