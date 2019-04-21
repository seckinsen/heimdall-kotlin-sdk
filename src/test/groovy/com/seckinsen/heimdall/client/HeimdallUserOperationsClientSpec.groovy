package com.seckinsen.heimdall.client

import com.seckinsen.heimdall.client.client.HeimdallUserOperationsClient
import com.seckinsen.heimdall.client.exception.HeimdallClientRequestNotSucceededException
import com.seckinsen.heimdall.client.model.Credentials
import com.seckinsen.heimdall.client.model.user.*
import org.junit.Ignore
import spock.lang.Specification

import java.time.LocalDate

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString

@Ignore
class HeimdallUserOperationsClientSpec extends Specification {

    private HeimdallUserOperationsClient client

    void setup() {
        def baseUrl = "https://XXX.herokuapp.com"
        client = new HeimdallUserOperationsClient(baseUrl)
    }

    def "not exist permanent user create attempt should succeeded"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user007@heimdall.io"
        def password = "trial-user007"
        def roles = ["ROLE_USER"].toSet()
        def audiences = ["heimdall-aud"].toSet()
        def scopes = ["read", "write"].toSet()
        def request = new UserCreateRequest(userName, password, roles, audiences, scopes, null)

        when:
        def response = client.createUser(credentials, request)

        then:
        println "Response => ${reflectionToString(response)}"
        response.user.userName == userName
        response.user.password == password
        response.user.authorities.containsAll(roles)
        response.user.audiences.containsAll(audiences)
        response.user.scopes.containsAll(scopes)
        response.user.validity.status == UserStatus.ACTIVE
        response.user.validity.expiryDate == null
    }

    def "exists permanent user create attempt with already exist user should throw exception"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user008@heimdall.io"
        def password = "trial-user008"
        def roles = ["ROLE_USER"].toSet()
        def audiences = ["heimdall-aud"].toSet()
        def scopes = ["read", "write"].toSet()
        def request = new UserCreateRequest(userName, password, roles, audiences, scopes, null)

        when:
        client.createUser(credentials, request)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "not exist temporary user create attempt with user should succeeded"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user010@heimdall.io"
        def password = "trial-user010"
        def roles = ["ROLE_USER"].toSet()
        def audiences = ["heimdall-aud"].toSet()
        def scopes = ["read", "write"].toSet()
        def expiryDate = LocalDate.now().plusMonths(3)
        def request = new UserCreateRequest(userName, password, roles, audiences, scopes, expiryDate)

        when:
        def response = client.createUser(credentials, request)

        then:
        println "Response => ${reflectionToString(response)}"
        response.user.userName == userName
        response.user.password == password
        response.user.authorities.containsAll(roles)
        response.user.audiences.containsAll(audiences)
        response.user.validity.status == UserStatus.ACTIVE
        response.user.validity.expiryDate == expiryDate
    }

    def "not exist temporary user create attempt with expiry date that is not at future should throw exception"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "non-exist-user@heimdall.io"
        def password = "non-exist-user"
        def roles = ["ROLE_USER"].toSet()
        def audiences = ["heimdall-aud"].toSet()
        def scopes = ["read", "write"].toSet()
        def expiryDate = LocalDate.now().minusDays(1)
        def request = new UserCreateRequest(userName, password, roles, audiences, scopes, expiryDate)

        when:
        client.createUser(credentials, request)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "exists active user delete attempt should succeeded"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user007@heimdall.io"
        def request = new UserDeleteRequest(userName)

        when:
        def response = client.deleteUser(credentials, request)

        then:
        println "Response => ${reflectionToString(response)}"
        response.status == "APPROVED"
    }

    def "exists passive user delete attempt should throw exception"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user007@heimdall.io"
        def request = new UserDeleteRequest(userName)

        when:
        client.deleteUser(credentials, request)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "non exist user delete attempt should throw exception"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "non-exist-user@heimdall.io"
        def request = new UserDeleteRequest(userName)

        when:
        client.deleteUser(credentials, request)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "exists passive user activate attempt should succeeded"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user007@heimdall.io"
        def request = new UserActivateRequest(userName)

        when:
        def response = client.activateUser(credentials, request)

        then:
        println "Response => ${reflectionToString(response)}"
        response.user.userName == userName
        !response.user.deleted
    }

    def "exists active user activate attempt should throw exception"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user007@heimdall.io"
        def request = new UserActivateRequest(userName)

        when:
        client.activateUser(credentials, request)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "not exist user activate attempt should throw exception"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "non-exist-user@heimdall.io"
        def request = new UserActivateRequest(userName)

        when:
        client.activateUser(credentials, request)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "exists user get attempt should succeeded"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user007@heimdall.io"
        def request = new UserGetRequest(userName)

        when:
        def response = client.getUser(credentials, request)

        then:
        println "Response => ${reflectionToString(response)}"
        response.user.userName == userName
    }

    def "not exist user get attempt should throw exception"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "non-exist-user@heimdall.io"
        def request = new UserActivateRequest(userName)

        when:
        client.activateUser(credentials, request)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "retrieve admin user credentials"() {
        return new Credentials("adminuser@heimdall.io", "XXX")
    }

}
