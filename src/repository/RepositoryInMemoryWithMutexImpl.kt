package com.example.repository

import com.example.model.PostModel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RepositoryInMemoryWithMutexImpl : PostRepository {
    private var nextId = 1L
    private val items = mutableListOf<PostModel>()
    private val mutex =
        Mutex() //Mutex используется для синхронизации общих данных в памяти между корутинами (они могут запускаться на разных потоках, следовательно потоками)

    override suspend fun getAll(): List<PostModel> =
        mutex.withLock {
            return items.reversed()
        }

    override suspend fun getById(id: Long): PostModel? = mutex.withLock {
        return items.find { it.id == id }
    }

    override suspend fun save(item: PostModel): PostModel = mutex.withLock {
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
        mutex.withLock {
            items.removeIf { it.id == id }
        }
    }

    override suspend fun likeById(id: Long): PostModel? = mutex.withLock {
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
        val index = items.indexOfFirst { it.id == id }
        if (index < 0) {
            return null
        }

        val post = items[index]

        val newPost = post.copy(likes = post.likes.dec())

        items[index] = newPost

        return newPost
    }


    override suspend fun shareById(id: Long): PostModel? =
        mutex.withLock {
            val index = items.indexOfFirst { it.id == id }
            if (index < 0) {
                return null
            } else {
                val item= items[index]

                val copy = item.copy(sharedCount = item.sharedCount.inc(), sharedByMe = true)

                items[index] = copy

                return copy

            }
        }

    override suspend fun commentById(id: Long): PostModel? =
        mutex.withLock {

            val index = items.indexOfFirst { it.id == id }
            if (index < 0) {
                return null
            } else {
                val item = items[index]
                val copy = item.copy(commentsCount = item.commentsCount.inc(), commentsByMe = true)
                items[index] = copy
                return copy
            }

        }
    override suspend fun repostById(id: Long): PostModel? =
        when (val index = items.indexOfFirst { it.id == id }) {
            -1 -> null
            else -> {
                val item = items[index]
                val copy = item.copy(
                    repostByMe = true,
                    repostCount = item.repostCount.inc()
                )
                items[index] = copy
                copy
            }
        }

}