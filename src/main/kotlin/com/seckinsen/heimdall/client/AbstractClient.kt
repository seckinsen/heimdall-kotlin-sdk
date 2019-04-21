package com.seckinsen.heimdall.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.seckinsen.heimdall.client.exception.HeimdallClientHttpCallFailedException
import com.seckinsen.heimdall.client.exception.HeimdallClientRequestNotSucceededException
import com.seckinsen.heimdall.client.model.Operation
import com.seckinsen.heimdall.client.model.token.AccessToken
import com.seckinsen.heimdall.client.model.token.RefreshToken
import com.seckinsen.heimdall.client.model.token.TokenContext
import com.seckinsen.heimdall.client.utils.http.OkHttpClientBuilder
import com.seckinsen.heimdall.client.utils.json.ObjectMapperBuilder
import okhttp3.*
import org.apache.commons.codec.binary.Base64
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.URL
import java.time.LocalDateTime

abstract class AbstractClient(private val URLRegistry: URLRegistry) {

    companion object {
        const val MEDIA_TYPE_FORM_ENCODED = "application/x-www-form-urlencoded"
        const val MEDIA_TYPE_JSON = "application/json"
    }

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    private val httpClient: OkHttpClient = OkHttpClientBuilder.build()

    private val objectMapper: ObjectMapper = ObjectMapperBuilder.getObjectMapper()

    protected fun getTokenContext(credentials: com.seckinsen.heimdall.client.model.Credentials): TokenContext {
        logMe(Operation.LOGIN.name, "[ ${credentials.username} ] named user login operation attempt!")
        return execute(
            request = mapOf<String, Any>(),
            operation = Operation.LOGIN,
            headers = credentials.toBasicAuthenticationHeader(),
            responseClass = TokenContext::class.java
        ).also { logMe(Operation.LOGIN.name, "[ ${credentials.username} ] named user login operation succeeded.") }
    }

    protected fun refreshToken(refreshToken: RefreshToken): TokenContext =
        execute(
            request = mapOf("refreshToken" to refreshToken.token),
            operation = Operation.RENEW_TOKEN,
            headers = mapOf("Content-Type" to MEDIA_TYPE_FORM_ENCODED),
            responseClass = TokenContext::class.java
        )

    protected fun <T : Any, R : Any> execute(
        request: T,
        operation: Operation,
        headers: Map<String, String> = mapOf(),
        responseClass: Class<R>
    ): R = sendPostRequest(
        url = URLRegistry.getURL(operation),
        request = toMap(request),
        headers = headers,
        responseClass = responseClass
    )

    private fun <R : Any> sendPostRequest(
        url: URL,
        request: Map<String, Any> = mapOf(),
        headers: Map<String, String> = mapOf(),
        responseClass: Class<R>
    ): R {
        val body = request
            .also { logMe("REQUEST", mapOf("url" to url.toString(), "body" to it, "headers" to headers)) }
            .let {
                when (headers["Content-Type"]) {
                    MEDIA_TYPE_FORM_ENCODED -> prepareFormRequestBody(request)
                    else -> prepareJsonRequestBody(request)
                }
            }

        return Request.Builder()
            .headers(prepareHeaders(headers))
            .url(url)
            .post(body)
            .build()
            .toResponse()
            .let { toObject(it, responseClass) }
    }

    private fun prepareFormRequestBody(request: Map<String, Any?>): RequestBody =
        FormBody.Builder()
            .apply { request.forEach { (key, value) -> value?.also { this.add(key, it.toString()) } } }
            .build()

    private fun prepareJsonRequestBody(request: Map<String, Any>): RequestBody =
        RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(request))

    private fun prepareHeaders(headers: Map<String, String>): Headers =
        Headers.Builder()
            .apply { headers.forEach { (key, value) -> this.add(key, value) } }
            .build()

    private fun Request.toResponse(): String = try {
        httpClient.newCall(this).execute()
    } catch (exp: IOException) {
        log.error("HTTP REQUEST ERROR", exp)
        throw HeimdallClientHttpCallFailedException(exp.localizedMessage)
    }.let { response ->
        val responseBody = response.body()?.string() ?: throw IOException("Unable to process operation!")

        val message = mapOf("code" to response.code(), "body" to responseBody)

        if (!response.isSuccessful)
            throw HeimdallClientRequestNotSucceededException(objectMapper.writeValueAsString(mapOf("message" to message)))

        responseBody.also { logMe("RESPONSE", message) }
    }

    private fun toMap(value: Any): Map<String, Any> = objectMapper.convertValue(value)

    private fun <R : Any> toObject(content: String, responseClass: Class<R>): R =
        objectMapper.readValue(content, responseClass)

    private fun com.seckinsen.heimdall.client.model.Credentials.toBasicAuthenticationHeader(): Map<String, String> =
        mapOf("Authorization" to "Basic ${Base64.encodeBase64String("${this.username}:${this.password}".toByteArray())}")

    protected fun AccessToken.toBearerAuthenticationHeader(): Map<String, String> =
        mapOf("Authorization" to "Bearer ${this.token}")

    protected fun logMe(type: String, message: Map<String, Any>) {
        val context = mapOf("time" to LocalDateTime.now().toString(), "type" to type, "message" to message)
        objectMapper.writeValueAsString(context).also { log.info(it) }
    }

    protected fun logMe(type: String, message: String) {
        val context = mapOf("time" to LocalDateTime.now().toString(), "type" to type, "message" to message)
        objectMapper.writeValueAsString(context).also { log.info(it) }
    }

}