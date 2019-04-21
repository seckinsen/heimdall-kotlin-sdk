package com.seckinsen.heimdall.client.model.user

import com.seckinsen.heimdall.client.model.BaseResponse

data class UserCreateResponse(val user: UserDetail) : BaseResponse()