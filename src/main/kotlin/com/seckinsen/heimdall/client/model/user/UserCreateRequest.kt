package com.seckinsen.heimdall.client.model.user

import java.time.LocalDate

data class UserCreateRequest(
    val userName: String,
    val password: String?,
    val audiences: Set<String> = mutableSetOf(),
    val roles: Set<String> = mutableSetOf(),
    val expiryDate: LocalDate?
)