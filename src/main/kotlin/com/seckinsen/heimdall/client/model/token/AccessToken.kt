package com.seckinsen.heimdall.client.model.token

data class AccessToken(val token: String) {

    companion object {
        @JvmStatic
        fun create(token: String) = AccessToken(token)

        fun parse(tokenContext: TokenContext) =
            AccessToken(tokenContext.accessToken)
    }

}