package com.denmroot.restapi.service

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class DefaultLinkMapperService : LinkMapperService {

    private val datastore: MutableMap<String, String> = ConcurrentHashMap()

    override fun addLink(key: String, link: String): LinkMapperService.Add {
        if (datastore.containsKey(key)) {
            return LinkMapperService.Add.AlreadyExist(key)
        } else {
            datastore.put(key, link)
            return LinkMapperService.Add.Success(key, link)
        }
    }

    override fun getLink(key: String) = if (datastore.contains(key)) {
        LinkMapperService.Get.Link(datastore.get(key)!!)
    } else {
        LinkMapperService.Get.NotFound(key)
    }

}