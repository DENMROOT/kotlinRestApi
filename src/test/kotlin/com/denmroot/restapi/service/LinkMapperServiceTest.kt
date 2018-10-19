package com.denmroot.restapi.service

import org.junit.Assert.*
import org.junit.Test
import org.springframework.boot.test.mock.mockito.MockBean

class LinkMapperServiceTest {

    private val service: LinkMapperService = DefaultLinkMapperService()

    private val KEY: String = "myKey"
    private val LINK: String = "http://correctlink.com"
    private val LINK_NEW: String = "http://incorrectLink.com"

    @Test
    fun clientCanAddNewLink() {
        assertEquals(LinkMapperService.Add.Success(KEY, LINK), service.addLink(KEY, LINK))
    }

    @Test
    fun clientNotAllowedToAddDuplicateLink() {
        service.addLink(KEY, LINK)
        assertEquals(LinkMapperService.Add.AlreadyExist(KEY), service.addLink(KEY, LINK_NEW))
        assertEquals(LinkMapperService.Get.Link(LINK), service.getLink(KEY))
    }

    @Test
    fun clientLinkNotFound() {
        assertEquals(LinkMapperService.Get.NotFound(KEY), service.getLink(KEY))
    }
}