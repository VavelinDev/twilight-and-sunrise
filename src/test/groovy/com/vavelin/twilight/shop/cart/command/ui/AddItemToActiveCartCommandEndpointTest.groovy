package com.vavelin.twilight.shop.cart.command.ui


import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddItemToActiveCartCommandEndpointTest extends Specification {

    private static final String VALID_PAYLOAD = """
            {
              "productId": 1, "quantity": 1
            }
    """

    private static final String INVALID_PAYLOAD = """
            {
              "productId": 1, "quantity": 0
            }
    """

    @LocalServerPort
    private int port

    private TestRestTemplate restTemplate

    private String validCartUrl;

    def setup() {
        validCartUrl = testUrl(AddItemToCartCommandEndpoint.PATH)
        restTemplate = new TestRestTemplate("admin", "admin")
    }

    def "Should add a valid product to the active cart"() {
        when:
        def httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)
        def validPayload = new HttpEntity<String>(VALID_PAYLOAD, httpHeaders)
        def result = restTemplate.exchange(validCartUrl, HttpMethod.PUT, validPayload, Void.class)

        then:
        result.statusCode == HttpStatus.OK
    }

    def "Should not add an invalid product to the active cart"() {
        when:
        def httpHeaders = new HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)
        def invalidPayload = new HttpEntity<String>(INVALID_PAYLOAD, httpHeaders)
        def result = restTemplate.exchange(validCartUrl, HttpMethod.PUT, invalidPayload, Void.class)

        then:
        result.statusCode == HttpStatus.BAD_REQUEST
    }

    def testUrl(String uri) {
        return "http://localhost:" + port + uri
    }

}
