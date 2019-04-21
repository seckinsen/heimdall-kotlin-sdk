package com.seckinsen.heimdall.client.model

enum class Operation {
    NONE,
    LOGIN,
    RENEW_TOKEN,
    USER_CREATE,
    USER_DELETE,
    USER_ACTIVATE,
    USER_GET,
    USER_AUTHORITY_ADD,
    USER_AUTHORITY_DELETE
}