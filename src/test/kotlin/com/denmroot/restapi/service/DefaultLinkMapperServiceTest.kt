package com.denmroot.restapi.service

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Matchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

class DefaultLinkMapperServiceTest {

    @InjectMocks
    val service: LinkMapperService = DefaultLinkMapperService()

    private val KEY: String = "myKey"
    private val LINK_A: String = "http://correctlink.com"
    private val LINK_B: String = "http://incorrectLink.com"

    @Mock
    lateinit var converterService: LinkConverterService

    private val KEY_A: String = "keyA"
    private val KEY_B: String = "keyB"
    private val ID_A: Long = 1
    private val ID_B: Long = 2

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun clientCanAddNewLinks() {
        Mockito.`when`(converterService.keyToId(KEY_A)).thenReturn(ID_A)
        Mockito.`when`(converterService.idToKey(ID_A)).thenReturn(KEY_A)

        Mockito.`when`(converterService.keyToId(KEY_B)).thenReturn(ID_B)
        Mockito.`when`(converterService.idToKey(ID_B)).thenReturn(KEY_B)

        val newKeyA = service.addLink(LINK_A)
        assertEquals(LinkMapperService.Get.Link(LINK_A), service.getLink(newKeyA))

        val newKeyB = service.addLink(LINK_B)
        assertEquals(LinkMapperService.Get.Link(LINK_B), service.getLink(newKeyB))

        assertNotEquals(newKeyA, newKeyB)
    }

    @Test
    fun clientLinkNotFound() {
        assertEquals(LinkMapperService.Get.NotFound(KEY), service.getLink(KEY))
    }
}