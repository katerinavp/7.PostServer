package com.example.route

import com.example.dto.PostRequestDto
import com.example.dto.PostResponseDto
import com.example.model.PostModel
import com.example.repository.PostRepository
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

fun Routing.v1() {
    route("/api/v1/posts") {
        val repo by kodein().instance<PostRepository>()
        get {
            val response = repo.getAll().map { PostResponseDto.fromModel(it) }
            call.respond(response)
        }
        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
            val model = repo.getById(id) ?: throw NotFoundException()
            val response = PostResponseDto.fromModel(model)
            call.respond(response)
        }
        get("/{id}/like") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
            val model = repo.likeById(id) ?: throw NotFoundException()
            val response = PostResponseDto.fromModel(model)
            call.respond(response)
        }

        get("/{id}/comment") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
            val model = repo.commentById(id) ?: throw NotFoundException()
            val response = PostResponseDto.fromModel(model)
            call.respond(response)
        }
        get("/{id}/share") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
            val model = repo.shareById(id) ?: throw NotFoundException()
            val response = PostResponseDto.fromModel(model)
            call.respond(response)
        }
        post{
            val input = call.receive<PostRequestDto>()
            val model = PostModel(
                id = input.id,
                author = input.author,
                content = input.content,
                type = input.type,
                date = input.date,
                adress = input.adress,
                location = input.location,
                repost = input.repost,
                video = input.video,
                adv = input.adv,
                likes = input.likes,
                commentsCount = input.commentsCount,
                commentsByMe = input.commentsByMe,
                sharedByMe = input.sharedByMe,
                sharedCount = input.sharedCount,
            )
            val response = PostResponseDto.fromModel(repo.save(model))
            call.respond(response)
        }
        delete("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException("id", "Long")
            repo.removeById(id)
        }
    }
}