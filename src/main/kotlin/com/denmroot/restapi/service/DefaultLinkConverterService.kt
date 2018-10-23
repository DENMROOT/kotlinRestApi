package com.denmroot.restapi.service

import org.springframework.stereotype.Service

@Service
class DefaultLinkConverterService : LinkConverterService {

    final var chars = "aswedrftgyhujikolpzxcvbnmQAWSEDRFTGYHUJIKOLPZXCVBNM1234567890-_".toCharArray()
    var charsToLong = (0 until chars.size).map{ i -> Pair(chars[i], i.toLong())}.toMap()

    override fun idToKey(id: Long): String {
        var n = id
        val builder = StringBuilder()

        while (n != 0L) {
            builder.append(chars[(n % chars.size).toInt()])
            n /= chars.size
        }

        return builder.reverse().toString()
    }

    override fun keyToId(key: String) = key.map { c -> charsToLong[c]!! }.fold(0L, {a,b -> a * chars.size + b})

}