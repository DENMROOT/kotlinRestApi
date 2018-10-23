package com.denmroot.restapi.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Service
class DefaultLinkMapperService : LinkMapperService {

    @Autowired
    lateinit var converter: LinkConverterService

    val sequence = AtomicLong(1)

    private val datastore: MutableMap<Long, String> = ConcurrentHashMap()

    override fun addLink(link: String): String {
        val id = sequence.getAndIncrement()
        val key = converter.idToKey(id)

        datastore[id] = link
        return key
    }

    override fun getLink(key: String): LinkMapperService.Get {
        val id = converter.keyToId(key)
        val link = datastore[id]
        return if (link != null) {
            LinkMapperService.Get.Link(link)
        } else {
            LinkMapperService.Get.NotFound(key)
        }
    }

}