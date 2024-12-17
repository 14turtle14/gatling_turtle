package gatling_exec.simulations;

import gatling_exec.scenarios.MainScenario;
import gatling_exec.utils.wiremock.WireMockSetup;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class MainSimulation extends Simulation {

    public static final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8089")
            .contentTypeHeader("application/json")
            .header("charset", "UTF-8")
            .acceptHeader("application/json")
            .check(
                    status().is(200)
            );

    {
        System.out.println("Начало симуляции");
        setUp(MainScenario.MainScenario.injectClosed(
                rampConcurrentUsers(1).to(5).during(Duration.ofMinutes(3)),
                constantConcurrentUsers(10).during(Duration.ofMinutes(4))
        ).protocols(httpProtocol)).maxDuration(Duration.ofMinutes(7));
    }
}
