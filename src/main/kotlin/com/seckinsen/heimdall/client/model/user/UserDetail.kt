package com.seckinsen.heimdall.client.model.user

import java.time.LocalDateTime

data class UserDetail(
    val userName: String,
    val password: String,
    val authorities: Collection<String>,
    val audiences: Collection<String>,
    val validity: UserValidity,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val deleted: Boolean
)