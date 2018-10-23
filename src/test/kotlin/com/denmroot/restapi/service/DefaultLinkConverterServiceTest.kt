package com.denmroot.restapi.service

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class DefaultLinkConverterServiceTest {

    private val service:LinkConverterService = DefaultLinkConverterService()

    @Test
    fun idMustBeConvertableBothWays() {
        val rand = Random()

        for(i in 0..1000L) {
            val initialId = Math.abs(rand.nextLong())
            val key = service.idToKey(initialId)
            val id = service.keyToId(key)

            assertEquals(initialId, id)
        }
    }
}