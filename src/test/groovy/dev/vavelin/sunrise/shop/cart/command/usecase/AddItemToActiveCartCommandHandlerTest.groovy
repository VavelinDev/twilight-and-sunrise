package dev.vavelin.sunrise.shop.cart.command.usecase

import dev.vavelin.sunrise.shop.cart.command.ui.AddItemToActiveCartCommandEndpoint
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication
import org.springframework.http.*
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddItemToActiveCartCommandHandlerTest extends Specification {

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



    @Autowired
    private Environment environment

    private WebTestClient webTestClient

    private String validCartUrl;

    def setup() {
        def port = environment.getProperty("local.server.port")
        validCartUrl = "http://localhost:" + port + AddItemToActiveCartCommandEndpoint.PATH
        webTestClient = WebTestClient.bindToServer().baseUrl(validCartUrl)
                .filter(basicAuthentication("admin", "admin")).build()
    }

    def "Should add a valid product to the active cart"() {
        when:
        def result = webTestClient.put().uri(validCartUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(VALID_PAYLOAD)
                .exchange()

        then:
        result.expectStatus().isOk()
    }

    def "Should not add an invalid product to the active cart"() {
        when:
        def result = webTestClient.put().uri(validCartUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(INVALID_PAYLOAD)
                .exchange()

        then:
        result.expectStatus().isBadRequest()
    }



}
