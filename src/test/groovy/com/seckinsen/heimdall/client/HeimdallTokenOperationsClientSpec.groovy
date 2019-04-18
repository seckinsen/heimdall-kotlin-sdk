package com.seckinsen.heimdall.client

import com.seckinsen.heimdall.client.client.HeimdallTokenOperationsClient
import com.seckinsen.heimdall.client.exception.HeimdallClientRequestNotSucceededException
import com.seckinsen.heimdall.client.model.Credentials
import com.seckinsen.heimdall.client.model.token.RefreshToken
import spock.lang.Specification

class HeimdallTokenOperationsClientSpec extends Specification {

    private HeimdallTokenOperationsClient client

    void setup() {
        def baseUrl = "https:/XXX.herokuapp.com"
        client = new HeimdallTokenOperationsClient(baseUrl)
    }

    def "login attempt with valid credentials should succeeded"() {
        given:
        def credentials = new Credentials("trial-user007@heimdall.io", "trial-user007")

        when:
        def tokenContext = client.login(credentials)

        then:
        println "Token Context => $tokenContext"
        tokenContext.expiresIn > 0
    }

    def "login attempt with invalid credentials should throw exception"() {
        given:
        def credential = new Credentials("non-exist-user@heimdall.io", "non-exists-user")

        when:
        client.login(credential)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "renew token attempt with valid token should succeeded"() {
        given:
        def token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBpcyJdLCJ1c2VyX25hbWUiOiJ0cmlhbC11c2VyMDA3QGhlaW1kYWxsLmlvIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6ImM4NzNjOWVlLWVlZWQtNGQ1ZC1iN2RmLTU0ZGM4ZmMxZDUyMSIsImV4cCI6MTU1NTUxODI4MywiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImE4NjU3YTBhLTc2NjEtNGNhYi04MmVkLWNlNDExMGM5NzM3MyIsImNsaWVudF9pZCI6ImFkbWluQGhlaW1kYWxsLmlvIn0.Yz_5689fCuZFZnBhLj0bYWhIv_oibWdJYTrbV-4xPU4"
        def refreshToken = new RefreshToken(token)

        when:
        def tokenContext = client.renewToken(refreshToken)

        then:
        println "Token Context => $tokenContext"
        tokenContext.expiresIn > 0
    }

    def "renew token attempt with invalid token should throw exception"() {
        given:
        def token = "refresh token"
        def refreshToken = new RefreshToken(token)

        when:
        client.renewToken(refreshToken)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "renew token attempt with expired token should throw exception"() {
        given:
        def token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBpcyJdLCJ1c2VyX25hbWUiOiJ0cmlhbC11c2VyMDA3QGhlaW1kYWxsLmlvIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6IjRmZGQwNDQzLTRmYTctNDkyNi1hMTY4LWU4NTZjYWFmNjQ4NCIsImV4cCI6MTU1NTUwNjQ5NiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImI2MzA5MWI5LWNlZWMtNDM4Yy05MTQ0LTJmYWEyN2IwMzkyYSIsImNsaWVudF9pZCI6ImFkbWluQGhlaW1kYWxsLmlvIn0.VBUvkhofZAPzxpca_HX8nB78723TvKV0pfx5oQzU9os"
        def refreshToken = new RefreshToken(token)

        when:
        client.renewToken(refreshToken)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

}
