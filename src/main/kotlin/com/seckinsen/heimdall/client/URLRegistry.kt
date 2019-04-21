package com.seckinsen.heimdall.client

import com.seckinsen.heimdall.client.model.Operation
import java.net.URL

class URLRegistry(private val baseUrl: String) {

    private val registeredPathMap: Map<Operation, String> = mapOf(
        Operation.LOGIN to "/login",
        Operation.RENEW_TOKEN to "/token/refresh",
        Operation.USER_CREATE to "/api/v1/account/user/create",
        Operation.USER_DELETE to "/api/v1/account/user/delete",
        Operation.USER_ACTIVATE to "/api/v1/account/user/activate",
        Operation.USER_GET to "/api/v1/account/user/get",
        Operation.USER_AUTHORITY_ADD to "/api/v1/account/authority/add",
        Operation.USER_AUTHORITY_DELETE to "/api/v1/account/authority/delete",
        Operation.USER_PASSWORD_RESET to "/api/v1/account/user/password/reset",
        Operation.USER_PASSWORD_CHANGE to "/api/v1/account/user/password/change"
    )

    fun getURL(operation: Operation): URL =
        registeredPathMap.getValue(operation).let { URL(baseUrl + it) }

}