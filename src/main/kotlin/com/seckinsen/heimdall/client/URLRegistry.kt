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
        Operation.USER_GET to "/api/v1/account/user/get"
    )

    fun getURL(operation: Operation): URL =
        registeredPathMap.getValue(operation).let { URL(baseUrl + it) }

}