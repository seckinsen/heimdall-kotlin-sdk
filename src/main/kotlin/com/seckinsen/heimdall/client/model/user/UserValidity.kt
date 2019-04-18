package com.seckinsen.heimdall.client.model.user

import java.time.LocalDate

// TODO: its name might be `UserValidityDto`
data class UserValidity(
    val status: UserStatus,
    var expiryDate: LocalDate?
)