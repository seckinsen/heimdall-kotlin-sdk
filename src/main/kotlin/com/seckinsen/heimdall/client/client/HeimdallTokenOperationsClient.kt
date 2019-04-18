package com.seckinsen.heimdall.client.client

import com.seckinsen.heimdall.client.AbstractClient
import com.seckinsen.heimdall.client.URLRegistry
import com.seckinsen.heimdall.client.model.Credentials
import com.seckinsen.heimdall.client.model.Operation
import com.seckinsen.heimdall.client.model.token.RefreshToken
import com.seckinsen.heimdall.client.model.token.TokenContext

class HeimdallTokenOperationsClient(baseUrl: String) : AbstractClient(URLRegistry(baseUrl)) {

    fun login(credentials: Credentials): TokenContext = getTokenContext(credentials = credentials)

    fun renewToken(refreshToken: RefreshToken): TokenContext {
        logMe(
            Operation.RENEW_TOKEN.name,
            "Token renew operation attempt with refresh token [ ${refreshToken.token.take(25)} ... ]"
        )
        return refreshToken(refreshToken)
            .also {
                logMe(
                    Operation.RENEW_TOKEN.name,
                    "Token renew operation succeeded with refresh token [ ${refreshToken.token.take(25)} ... ]"
                )
            }
    }

}