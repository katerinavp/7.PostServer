package com.example.model

data class PostModel (

    val id: Long,
    val author: String?,
    val content: String?,
    var type: PostType,
    val date: String?,
    val adress: String?,
    val location: Pair<Double?, Double?>?,
    val repost: String?,
    val video:String?,
    val adv:String?,
    val likes: Int = 0,
    var commentsCount: Int,
    var commentsByMe: Boolean,
    var sharedByMe: Boolean,
    var sharedCount: Int,
)
