package com.seckinsen.heimdall.client

import com.seckinsen.heimdall.client.client.HeimdallAuthorityOperationsClient
import com.seckinsen.heimdall.client.exception.HeimdallClientRequestNotSucceededException
import com.seckinsen.heimdall.client.model.Credentials
import com.seckinsen.heimdall.client.model.authority.AuthorityAddRequest
import com.seckinsen.heimdall.client.model.authority.AuthorityDeleteRequest
import org.junit.Ignore
import spock.lang.Specification

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString

@Ignore
class HeimdallAuthorityOperationsClientSpec extends Specification {

    private HeimdallAuthorityOperationsClient client

    void setup() {
        def baseUrl = "https://XXX.herokuapp.com"
        client = new HeimdallAuthorityOperationsClient(baseUrl)
    }

    def "non defined user authority add attempt should succeeded"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user007@heimdall.io"
        def role = "ROLE_VOID"
        def request = new AuthorityAddRequest(userName, role)

        when:
        def response = client.addAuthority(credentials, request)

        then:
        println "Response => ${reflectionToString(response)}"
        response.status == "APPROVED"
    }

    def "already defined user authority add attempt should throw exception"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user007@heimdall.io"
        def role = "ROLE_VOID"
        def request = new AuthorityAddRequest(userName, role)

        when:
        client.addAuthority(credentials, request)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "already defined user authority delete attempt should succeeded"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user007@heimdall.io"
        def role = "ROLE_VOID"
        def request = new AuthorityDeleteRequest(userName, role)

        when:
        def response = client.deleteAuthority(credentials, request)

        then:
        println "Response => ${reflectionToString(response)}"
        response.status == "APPROVED"
    }

    def "non defined user authority delete attempt should throw exception"() {
        given:
        def credentials = 'retrieve admin user credentials'()

        and:
        def userName = "trial-user007@heimdall.io"
        def role = "ROLE_VOID"
        def request = new AuthorityDeleteRequest(userName, role)

        when:
        client.deleteAuthority(credentials, request)

        then:
        thrown HeimdallClientRequestNotSucceededException
    }

    def "retrieve admin user credentials"() {
        return new Credentials("adminuser@heimdall.io", "XXX")
    }

}
