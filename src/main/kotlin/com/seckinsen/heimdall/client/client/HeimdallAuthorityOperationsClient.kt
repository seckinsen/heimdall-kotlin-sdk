package com.seckinsen.heimdall.client.client

import com.seckinsen.heimdall.client.AbstractClient
import com.seckinsen.heimdall.client.URLRegistry
import com.seckinsen.heimdall.client.model.Credentials
import com.seckinsen.heimdall.client.model.Operation
import com.seckinsen.heimdall.client.model.authority.AuthorityAddRequest
import com.seckinsen.heimdall.client.model.authority.AuthorityAddResponse
import com.seckinsen.heimdall.client.model.authority.AuthorityDeleteRequest
import com.seckinsen.heimdall.client.model.authority.AuthorityDeleteResponse
import com.seckinsen.heimdall.client.model.token.AccessToken

class HeimdallAuthorityOperationsClient(baseUrl: String) : AbstractClient(URLRegistry(baseUrl)) {

    fun addAuthority(credentials: Credentials, request: AuthorityAddRequest): AuthorityAddResponse =
        getTokenContext(credentials)
            .let { AccessToken.parse(it) }
            .run { addAuthority(this, request) }

    fun addAuthority(accessToken: AccessToken, request: AuthorityAddRequest): AuthorityAddResponse {
        logMe(
            Operation.USER_AUTHORITY_ADD.name,
            "[ ${request.userName} ] named user [ ${request.role} ] role add operation attempt."
        )

        return execute(
            request = request,
            operation = Operation.USER_AUTHORITY_ADD,
            headers = mapOf("Content-Type" to MEDIA_TYPE_JSON) + accessToken.toBearerAuthenticationHeader(),
            responseClass = AuthorityAddResponse::class.java
        ).also {
            logMe(
                Operation.USER_AUTHORITY_ADD.name,
                "[ ${request.userName} ] named user [ ${request.role} ] role add operation succeeded."
            )
        }
    }

    fun deleteAuthority(credentials: Credentials, request: AuthorityDeleteRequest): AuthorityDeleteResponse =
        getTokenContext(credentials)
            .let { AccessToken.parse(it) }
            .run { deleteAuthority(this, request) }

    fun deleteAuthority(accessToken: AccessToken, request: AuthorityDeleteRequest): AuthorityDeleteResponse {
        logMe(
            Operation.USER_AUTHORITY_DELETE.name,
            "[ ${request.userName} ] named user [ ${request.role} ] role delete operation attempt."
        )

        return execute(
            request = request,
            operation = Operation.USER_AUTHORITY_DELETE,
            headers = mapOf("Content-Type" to MEDIA_TYPE_JSON) + accessToken.toBearerAuthenticationHeader(),
            responseClass = AuthorityDeleteResponse::class.java
        ).also {
            logMe(
                Operation.USER_AUTHORITY_DELETE.name,
                "[ ${request.userName} ] named user [ ${request.role} ] role delete operation succeeded."
            )
        }
    }

}