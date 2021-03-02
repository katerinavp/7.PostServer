package com.example.dto

import com.example.model.PostType

data class PostRequestDto( //запрос
    var id: Long,
    val author: String?,
    val content: String? = null,
    var type: PostType,
    val date: String?,
    val adress: String?,
    val location: Pair<Double?, Double?>?,
    val repost: String?,
    val video: String?,
    val adv: String?,
    val likes: Int = 0,
    var commentsCount: Int,
    var commentsByMe: Boolean,
    var sharedByMe: Boolean,
    var sharedCount: Int,
)