package com.seckinsen.heimdall.client.model.user

data class UserActivateResponse(val user: UserDetail, val status: String, val code: String, val message: String)