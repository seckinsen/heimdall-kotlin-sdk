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
        def audiences = ["apis"].toSet()
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
        def audiences = ["apis"].toSet()
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
        def audiences = ["apis"].toSet()
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
        def audiences = ["apis"].toSet()
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
        isSanitized(response.user.password)
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
        isSanitized(response.user.password)
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

    def "exists user password reset attempt should succeeded"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user007@heimdall.io"
        def password = "new-trial-user007"
        def request = new UserPasswordResetRequest(userName, password)

        when:
        def response = client.resetUserPassword(credentials, request)

        then:
        println "Response => ${reflectionToString(response)}"
        response.user.userName == userName
        response.user.password == password
        response.status == "APPROVED"
    }

    def "not exist user password reset attempt should throw exception"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "non-exist-user@heimdall.io"
        def password = "new-non-exist-user"
        def request = new UserPasswordResetRequest(userName, password)

        when:
        client.resetUserPassword(credentials, request)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "exists user password change attempt should succeeded"() {
        given:
        def userName = "trial-user007@heimdall.io"
        def password = "trial-user007"
        def newPassword = "new-trial-user007"
        def credentials = new Credentials(userName, password)
        def request = new UserPasswordChangeRequest(newPassword)

        when:
        def response = client.changeUserPassword(credentials, request)

        then:
        println "Response => ${reflectionToString(response)}"
        response.user.userName == userName
        response.user.password == newPassword
        response.status == "APPROVED"
    }

    def "not exist user password change attempt should throw exception"() {
        given:
        def userName = "non-exist-user@heimdall.io"
        def password = "non-exist-user"
        def newPassword = "new-non-exist-user"
        def credentials = new Credentials(userName, password)
        def request = new UserPasswordChangeRequest(newPassword)

        when:
        client.changeUserPassword(credentials, request)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "exists user lock attempt should succeeded"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user007@heimdall.io"
        def request = new UserLockRequest(userName)

        when:
        def response = client.lockUser(credentials, request)

        then:
        println "Response => ${reflectionToString(response)}"
        response.status == "APPROVED"
    }

    def "not exist user lock attempt should throw exception"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "non-exist-user@heimdall.io"
        def request = new UserLockRequest(userName)

        when:
        client.lockUser(credentials, request)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "exists user unlock attempt should succeeded"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user007@heimdall.io"
        def request = new UserUnlockRequest(userName)

        when:
        def response = client.unlockUser(credentials, request)

        then:
        println "Response => ${reflectionToString(response)}"
        response.user.userName == userName
        isSanitized(response.user.password)
        response.status == "APPROVED"
    }

    def "not exist user unlock attempt should throw exception"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "non-exist-user@heimdall.io"
        def request = new UserUnlockRequest(userName)

        when:
        client.unlockUser(credentials, request)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "retrieve admin user credentials"() {
        return new Credentials("adminuser@heimdall.io", "XXX")
    }

    def isSanitized(String password) {
        password.matches(/X+/)
    }

}
