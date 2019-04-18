package com.seckinsen.heimdall.client.model.user

data class UserCreateResponse(val user: UserDetail, val status: String, val code: String, val message: String)