package com.denmroot.restapi.controller

import com.denmroot.restapi.service.LinkMapperService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.anyString
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class RedirectControllerTest {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @MockBean
    private lateinit var linkMapperService: LinkMapperService

    private lateinit var mockMvc: MockMvc

    @Before
    fun init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()
    }

    private val KEY = "myKey"
    private val KEY_NOT_FOUND = "myKey"
    private val SUCCESS_PATH = "/redirect/$KEY"
    private val FAIL_PATH = "/redirect/$KEY_NOT_FOUND"
    private val HEADER_NAME = "location"
    private val HEADER_VALUE = "http://www.ukr.net"

    @Test
    fun shouldRedirectOnRequestSuccess() {
        `when`(linkMapperService.getLink(anyString()))
                .thenReturn(LinkMapperService.Get.Link(HEADER_VALUE))

        mockMvc.perform(get(SUCCESS_PATH))
                .andExpect(status().is3xxRedirection)
                .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
    }

    @Test
    fun shouldReturnNotFoundOnAbsentKey() {
        `when`(linkMapperService.getLink(anyString()))
                .thenReturn(LinkMapperService.Get.NotFound(KEY_NOT_FOUND))

        mockMvc.perform(get(FAIL_PATH))
                .andExpect(status().isNotFound)
    }
}