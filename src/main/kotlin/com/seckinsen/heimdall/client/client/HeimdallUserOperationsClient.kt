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

    fun resetUserPassword(credentials: Credentials, request: UserPasswordResetRequest): UserPasswordResetResponse =
        getTokenContext(credentials)
            .let { AccessToken.parse(it) }
            .run { resetUserPassword(this, request) }

    fun resetUserPassword(accessToken: AccessToken, request: UserPasswordResetRequest): UserPasswordResetResponse {
        logMe(
            Operation.USER_PASSWORD_RESET.name,
            "[ ${request.userName} ] named user password reset operation attempt."
        )

        return execute(
            request = request,
            operation = Operation.USER_PASSWORD_RESET,
            headers = mapOf("Content-Type" to MEDIA_TYPE_JSON) + accessToken.toBearerAuthenticationHeader(),
            responseClass = UserPasswordResetResponse::class.java
        ).also {
            logMe(
                Operation.USER_PASSWORD_RESET.name,
                "[ ${request.userName} ] named user password reset operation succeeded."
            )
        }
    }

    fun changeUserPassword(credentials: Credentials, request: UserPasswordChangeRequest): UserPasswordChangeResponse =
        getTokenContext(credentials)
            .let { AccessToken.parse(it) }
            .run { changeUserPassword(this, request) }

    fun changeUserPassword(accessToken: AccessToken, request: UserPasswordChangeRequest): UserPasswordChangeResponse {
        logMe(Operation.USER_PASSWORD_CHANGE.name, "user password reset operation attempt.")

        return execute(
            request = request,
            operation = Operation.USER_PASSWORD_CHANGE,
            headers = mapOf("Content-Type" to MEDIA_TYPE_JSON) + accessToken.toBearerAuthenticationHeader(),
            responseClass = UserPasswordChangeResponse::class.java
        ).also {
            logMe(Operation.USER_PASSWORD_CHANGE.name, "user password reset operation succeeded.")
        }
    }

    fun lockUser(credentials: Credentials, request: UserLockRequest): UserLockResponse =
        getTokenContext(credentials)
            .let { AccessToken.parse(it) }
            .run { lockUser(this, request) }

    fun lockUser(accessToken: AccessToken, request: UserLockRequest): UserLockResponse {
        logMe(Operation.USER_LOCK.name, "[ ${request.userName} ] named user lock operation attempt.")

        return execute(
            request = request,
            operation = Operation.USER_LOCK,
            headers = mapOf("Content-Type" to MEDIA_TYPE_JSON) + accessToken.toBearerAuthenticationHeader(),
            responseClass = UserLockResponse::class.java
        ).also {
            logMe(Operation.USER_LOCK.name, "[ ${request.userName} ] named user lock operation succeeded.")
        }
    }

    fun unlockUser(credentials: Credentials, request: UserUnlockRequest): UserUnlockResponse =
        getTokenContext(credentials)
            .let { AccessToken.parse(it) }
            .run { unlockUser(this, request) }

    fun unlockUser(accessToken: AccessToken, request: UserUnlockRequest): UserUnlockResponse {
        logMe(Operation.USER_UNLOCK.name, "[ ${request.userName} ] named user unlock operation attempt.")

        return execute(
            request = request,
            operation = Operation.USER_UNLOCK,
            headers = mapOf("Content-Type" to MEDIA_TYPE_JSON) + accessToken.toBearerAuthenticationHeader(),
            responseClass = UserUnlockResponse::class.java
        ).also {
            logMe(Operation.USER_UNLOCK.name, "[ ${request.userName} ] named user unlock operation succeeded.")
        }
    }

}