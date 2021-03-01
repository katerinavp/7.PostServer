package com.example.test

import com.example.module
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertTrue

class ApplicationTest {

    //        @Test
//        fun `test get all`() {
//            withTestApplication({ module() }) {
//                with(handleRequest(HttpMethod.Get, "/api/v1/post")) {
//                    assertEquals(HttpStatusCode.OK, response.status())
//                    assertEquals(
//                        ContentType.Application.Json.withCharset(Charsets.UTF_8),
//                        response.contentType()
//                    )
//
//                }
//            }
//        }
    private val jsonContentType = ContentType.Application.Json.withCharset(Charsets.UTF_8)

    @Test
    fun testGetAll() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/api/v1/posts").run {
                kotlin.test.assertEquals(HttpStatusCode.OK, response.status())
                kotlin.test.assertEquals(jsonContentType, response.contentType())
            }
        }
    }

    @Test
    fun testAdd() {
        withTestApplication({ module() }) {
            with(handleRequest(HttpMethod.Post, "/api/v1/posts") {
                addHeader(HttpHeaders.ContentType, jsonContentType.toString())
                setBody(
                    """
                        {
                          "id": 3,
                          "author": "Ivanov",
                          "content": "Java is the best language",
                          "type": "SIMPLE",
                          "date": "2020-12-09 19:20:03",
                          "adress": "null",
                          "location": null,
                          "repost": "null",
                          "video":"https://www.youtube.com/watch?v\u003dWhWc3b3KhnY",
                          "adv":"null",
                          "likes": 5,
                          "commentsCount": 10,
                          "commentsByMe": true,
                          "sharedByMe": false,
                          "sharedCount": 5
                        }
                    """.trimIndent()
                )
            }) {
                response
                kotlin.test.assertEquals(HttpStatusCode.OK, response.status())
                assertTrue(response.content!!.contains("\"id\": 1"))
            }
        }
    }


}