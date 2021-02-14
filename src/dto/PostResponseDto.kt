package com.example.dto

import com.example.model.PostModel
import com.example.model.PostType

class PostResponseDto(
    val id: Long,
    var type: PostType,
    val date: String?,
    val author: String?,
    val content: String? = null,
    val adress: String?,
    val location: Pair<Double?, Double?>?,
    val repost: String?,
    val video: String?,
    val adv: String?
) {
    companion object {
        fun fromModel(model: PostModel) =
            PostResponseDto(
                //у классов естсь объекты компаньоны, мы можем обратиться кним через имя класса.
                id = model.id,
                type = model.type,
                date = model.date,
                author = model.author,
                content = model.content,
                adress = model.adress,
                location = model.location,
                repost = model.repost,
                video = model.video,
                adv = model.adv
            )
    }
}

