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
    val commentsCount: Int=0,
    val commentsByMe: Boolean= false,
    val sharedByMe: Boolean= false,
    val sharedCount: Int=0,
    val repostCount: Int = 0,
    val repostByMe: Boolean = false,
)
