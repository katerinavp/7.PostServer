package com.example.model

data class PostModel (
    var type: PostType,
    val id: Long,
    val date: String?,
    val author: String?,
    val content: String?,
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
