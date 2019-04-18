package com.seckinsen.heimdall.client.exception

class HeimdallClientRequestNotSucceededException(message: String) : Exception("Http request failed! $message")