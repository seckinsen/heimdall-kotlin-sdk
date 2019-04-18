package com.seckinsen.heimdall.client.exception

class HeimdallClientHttpCallFailedException(cause: String) : Exception("Http call failed by getting `$cause` error")