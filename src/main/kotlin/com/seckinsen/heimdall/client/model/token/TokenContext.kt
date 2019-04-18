package com.seckinsen.heimdall.client.model.token

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenContext(
    @field:JsonProperty("access_token")
    val accessToken: String,
    @field:JsonProperty("token_type")
    val tokenType: String,
    @field:JsonProperty("refresh_token")
    val refreshToken: String,
    @field:JsonProperty("expires_in")
    val expiresIn: Int,
    val scope: String,
    val jti: String
)