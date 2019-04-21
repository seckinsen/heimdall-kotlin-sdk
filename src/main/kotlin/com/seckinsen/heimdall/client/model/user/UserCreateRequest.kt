package com.seckinsen.heimdall.client.model.user

import java.time.LocalDate

data class UserCreateRequest(
    val userName: String,
    val password: String?,
    val roles: Set<String> = mutableSetOf(),
    val audiences: Set<String> = mutableSetOf(),
    val scopes: Set<String> = mutableSetOf(),
    val expiryDate: LocalDate?
)