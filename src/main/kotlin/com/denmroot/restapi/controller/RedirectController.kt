package com.denmroot.restapi.controller

import com.denmroot.restapi.service.LinkMapperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/redirect")
class RedirectController {

    @Autowired
    lateinit var linkMapperService: LinkMapperService

    @RequestMapping("/{key}")
    fun redirect(@PathVariable("key") key: String, response: HttpServletResponse) {

        val result = linkMapperService.getLink(key)

        when (result) {
            is LinkMapperService.Get.Link -> {
                response.setHeader(HEADER_NAME, result.link)
                response.status = 302
            }
            is LinkMapperService.Get.NotFound -> {
                response.status = 404
            }
        }


    }

    companion object {
        private val HEADER_NAME = "Location"
    }

}