package com.vavelin.example.hexagon.cart.command.ui

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddItemToCartCommandEndpointTest extends Specification {

    private static final String VALID_PAYLOAD = """
            {
              "productId": 20
            }
    """

    @LocalServerPort
    private int port

    @Autowired
    private TestRestTemplate restTemplate;

    private String validCartUrl

    void setup() {
        validCartUrl = "http://localhost:" + port + "/carts/10"
    }

    def "Successfully add a valid product to the existing cart"() {
        when:
        def result = restTemplate.put(validCartUrl, VALID_PAYLOAD)

        then:
        noExceptionThrown()
    }
}
