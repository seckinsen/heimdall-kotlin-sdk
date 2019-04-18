package com.seckinsen.heimdall.client.client

import com.seckinsen.heimdall.client.AbstractClient
import com.seckinsen.heimdall.client.URLRegistry
import com.seckinsen.heimdall.client.model.Credentials
import com.seckinsen.heimdall.client.model.Operation
import com.seckinsen.heimdall.client.model.token.AccessToken
import com.seckinsen.heimdall.client.model.user.*

class HeimdallUserOperationsClient(baseUrl: String) : AbstractClient(URLRegistry(baseUrl)) {

    fun createUser(credentials: Credentials, request: UserCreateRequest): UserCreateResponse =
        getTokenContext(credentials)
            .let { AccessToken.parse(it) }
            .run { createUser(this, request) }

    fun createUser(accessToken: AccessToken, request: UserCreateRequest): UserCreateResponse {
        logMe(Operation.USER_CREATE.name, "[ ${request.userName} ] named user create operation attempt.")

        return execute(
            request = request,
            operation = Operation.USER_CREATE,
            headers = mapOf("Content-Type" to MEDIA_TYPE_JSON) + accessToken.toBearerAuthenticationHeader(),
            responseClass = UserCreateResponse::class.java
        ).also { logMe(Operation.USER_CREATE.name, "[ ${request.userName} ] named user create operation succeeded.") }
    }

    fun deleteUser(credentials: Credentials, request: UserDeleteRequest): UserDeleteResponse =
        getTokenContext(credentials)
            .let { AccessToken.parse(it) }
            .run { deleteUser(this, request) }

    fun deleteUser(accessToken: AccessToken, request: UserDeleteRequest): UserDeleteResponse {
        logMe(Operation.USER_DELETE.name, "[ ${request.userName} ] named user delete operation attempt.")

        return execute(
            request = request,
            operation = Operation.USER_DELETE,
            headers = mapOf("Content-Type" to MEDIA_TYPE_JSON) + accessToken.toBearerAuthenticationHeader(),
            responseClass = UserDeleteResponse::class.java
        ).also { logMe(Operation.USER_DELETE.name, "[ ${request.userName} ] named user delete operation succeeded.") }
    }

    fun activateUser(credentials: Credentials, request: UserActivateRequest): UserActivateResponse =
        getTokenContext(credentials)
            .let { AccessToken.parse(it) }
            .run { activateUser(this, request) }

    fun activateUser(accessToken: AccessToken, request: UserActivateRequest): UserActivateResponse {
        logMe(Operation.USER_ACTIVATE.name, "[ ${request.userName} ] named user activate operation attempt.")

        return execute(
            request = request,
            operation = Operation.USER_ACTIVATE,
            headers = mapOf("Content-Type" to MEDIA_TYPE_JSON) + accessToken.toBearerAuthenticationHeader(),
            responseClass = UserActivateResponse::class.java
        ).also {
            logMe(
                Operation.USER_ACTIVATE.name,
                "[ ${request.userName} ] named user activate operation succeeded."
            )
        }
    }

    fun getUser(credentials: Credentials, request: UserGetRequest): UserGetResponse =
        getTokenContext(credentials)
            .let { AccessToken.parse(it) }
            .run { getUser(this, request) }

    fun getUser(accessToken: AccessToken, request: UserGetRequest): UserGetResponse {
        logMe(Operation.USER_GET.name, "[ ${request.userName} ] named user get operation attempt.")

        return execute(
            request = request,
            operation = Operation.USER_GET,
            headers = mapOf("Content-Type" to MEDIA_TYPE_JSON) + accessToken.toBearerAuthenticationHeader(),
            responseClass = UserGetResponse::class.java
        ).also {
            logMe(
                Operation.USER_GET.name,
                "[ ${request.userName} ] named user get operation succeeded."
            )
        }
    }

    private fun AccessToken.toBearerAuthenticationHeader(): Map<String, String> =
        mapOf("Authorization" to "Bearer ${this.token}")

}