package com.denmroot.restapi.service

interface LinkConverterService {
    fun idToKey(id: Long): String

    fun keyToId(key: String): Long
}
