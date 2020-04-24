package com.example.rafaelanastacioalves.moby.repository.database

import com.example.rafaelanastacioalves.moby.domain.entities.New
import com.orhanobut.hawk.Hawk

object DAO {
    private const val MAIN_ENTITY_LIST_KEY = "AAAA"

    fun getNewsList(): List<Long>? {
        return Hawk.get(MAIN_ENTITY_LIST_KEY)
    }

    fun saveNewsList(resultData: List<Long>?) {
        val resultSuccessfull = Hawk.put(MAIN_ENTITY_LIST_KEY, resultData)
        if (!resultSuccessfull) {
            throw Exception()
        }
    }

    fun saveNewsDetail(resultData: New) {
        val resultSuccessfull = Hawk.put(resultData.id.toString(), resultData)
        if (!resultSuccessfull) {
            throw Exception()
        }
    }

    fun getNewDetails(key: String): New? {
        return Hawk.get(key)
    }
}