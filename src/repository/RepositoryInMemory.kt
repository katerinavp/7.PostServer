package com.example.repository

import com.example.model.PostModel
import kotlinx.coroutines.sync.Mutex

class RepositoryInMemory : PostRepository {
    private var nextId = 1L
    private val items = mutableListOf<PostModel>()


    override suspend fun getAll(): List<PostModel> {
        return items.reversed()
    }

    override suspend fun getById(id: Long): PostModel? {
        return items.find { it.id == id }
    }

    override suspend fun save(item: PostModel): PostModel {
        return when (val index = items.indexOfFirst { it.id == item.id }) {
            -1 -> {
                val copy = item.copy(id = nextId++)
                items.add(copy)
                copy
            }
            else -> {
                items[index] = item
                item
            }
        }
    }

    override suspend fun removeById(id: Long) {
        items.removeIf { it.id == id }
    }

    override suspend fun likeById(id: Long): PostModel? {
        val index = items.indexOfFirst { it.id == id }
        if (index < 0) {
            return null
        }

        val post = items[index]

        val newPost = post.copy(likes = post.likes.inc())

        items[index] = newPost

        return newPost
    }

    override suspend fun dislikeById(id: Long): PostModel? {
        TODO("Not yet implemented")
    }

    override suspend fun shareById(id: Long): PostModel? {
        val index = items.indexOfFirst { it.id == id }
        if (index < 0) {
            return null
        } else {
            val item = items[index]
            val copy = item.copy(sharedCount = item.sharedCount + 1, sharedByMe = true)
            return copy

        }
    }

    override suspend fun commentById(id: Long): PostModel? {

        val index = items.indexOfFirst { it.id == id }
        if (index < 0) {
            return null
        } else {
            val item = items[index]
            val copy = item.copy(commentsCount = item.commentsCount + 1, commentsByMe = true)
            return copy
        }

    }

}