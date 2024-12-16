package gatling_exec.utils.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockSetup {

    private static WireMockServer wireMockServer;

    public static void startWireMock() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8089);

        stubFor(post(urlEqualTo("/auth"))
                .withRequestBody(matchingJsonPath("$.clientId"))
                .withRequestBody(matchingJsonPath("$.name"))
                .withRequestBody(matchingJsonPath("$.surname"))
                .withHeader("password", equalTo("141414"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"authenticated\": true}")));

        stubFor(post(urlEqualTo("/products"))
                .withRequestBody(matchingJsonPath("$.clientId"))
                .withRequestBody(matchingJsonPath("$.name"))
                .withRequestBody(matchingJsonPath("$.surname"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"products\": [{\"id\": 1, \"name\": \"Product 1\"}, {\"id\": 2, \"name\": \"Product 2\"}]}")));

        stubFor(post(urlEqualTo("/cart/add"))
                .withRequestBody(matchingJsonPath("$.clientId"))
                .withRequestBody(matchingJsonPath("$.name"))
                .withRequestBody(matchingJsonPath("$.surname"))
                .withRequestBody(matchingJsonPath("$.productId"))
                .withRequestBody(matchingJsonPath("$.quantity"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Product added to cart\"}")));

        stubFor(post(urlEqualTo("/cart"))
                .withRequestBody(matchingJsonPath("$.clientId"))
                .withRequestBody(matchingJsonPath("$.name"))
                .withRequestBody(matchingJsonPath("$.surname"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"cart\": [{\"productId\": 1, \"quantity\": 1}]}")));

        stubFor(post(urlEqualTo("/order/create"))
                .withRequestBody(matchingJsonPath("$.clientId"))
                .withRequestBody(matchingJsonPath("$.name"))
                .withRequestBody(matchingJsonPath("$.surname"))
                .withRequestBody(matchingJsonPath("$.cart"))
                .withRequestBody(matchingJsonPath("$.paymentMethod"))
                .withRequestBody(matchingJsonPath("$.shippingAddress"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"orderId\": 12345, \"status\": \"created\"}")));

        stubFor(post(urlEqualTo("/order/12345"))
                .withRequestBody(matchingJsonPath("$.clientId"))
                .withRequestBody(matchingJsonPath("$.name"))
                .withRequestBody(matchingJsonPath("$.surname"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"orderId\": 12345, \"status\": \"completed\"}")));

        stubFor(post(urlEqualTo("/order/12345/cancel"))
                .withRequestBody(matchingJsonPath("$.clientId"))
                .withRequestBody(matchingJsonPath("$.name"))
                .withRequestBody(matchingJsonPath("$.surname"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"orderId\": 12345, \"status\": \"cancelled\"}")));

        stubFor(post(urlEqualTo("/orders"))
                .withRequestBody(matchingJsonPath("$.clientId"))
                .withRequestBody(matchingJsonPath("$.name"))
                .withRequestBody(matchingJsonPath("$.surname"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"orders\": [{\"orderId\": 12345, \"status\": \"completed\"}]}")));

        stubFor(post(urlEqualTo("/order/12345/shipping"))
                .withRequestBody(matchingJsonPath("$.clientId"))
                .withRequestBody(matchingJsonPath("$.name"))
                .withRequestBody(matchingJsonPath("$.surname"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"orderId\": 12345, \"shippingStatus\": \"shipped\"}")));

        stubFor(post(urlEqualTo("/order/12345/return"))
                .withRequestBody(matchingJsonPath("$.clientId"))
                .withRequestBody(matchingJsonPath("$.name"))
                .withRequestBody(matchingJsonPath("$.surname"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"orderId\": 12345, \"returnStatus\": \"returned\"}")));
    }

    public static void stopWireMock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}
