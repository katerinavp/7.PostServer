package com.example



import com.example.repository.PostRepository
import com.example.repository.RepositoryInMemory
import com.example.route.v1
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.kodein.di.ktor.KodeinFeature

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }

    install(StatusPages) { // инсталим StatusPages , описываем наши эксепшион.
        exception<NotImplementedError> { e ->
            call.respond(HttpStatusCode.NotImplemented, Error("Error"))
            throw e
        }
        exception<ParameterConversionException> { e ->
            call.respond(HttpStatusCode.BadRequest) // отправляем HttpStatusCode.BadRequest

            throw e
        }
        exception<Throwable> { e ->
            call.respond(HttpStatusCode.InternalServerError)
            throw e
        }
    }

    install(KodeinFeature) {

        bind<PostRepository>() with singleton { RepositoryInMemory() }
    }

    install(Routing) {
        v1()
    }




}

