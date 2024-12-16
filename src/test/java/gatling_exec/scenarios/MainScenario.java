package gatling_exec.scenarios;

import gatling_exec.actions.MainScenarioActions;
import gatling_exec.utils.GatlingHelper;
import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;

import java.time.Duration;
import java.util.Calendar;

import static io.gatling.javaapi.core.CoreDsl.*;
import static org.eclipse.jetty.webapp.MetaDataComplete.False;
import static org.eclipse.jetty.webapp.MetaDataComplete.True;

public class MainScenario {

    public static final ScenarioBuilder MainScenario = scenario("MainScenario")
            .forever().on(
                    pace(Duration.ofMinutes(2), Duration.ofMinutes(2).plus(Duration.ofSeconds(30)))
                            .group("Authorization").on(
                                    exec(GatlingHelper.authorization)
                                            .exec(session -> session.set("employee", False))
                                            .pause(Duration.ofSeconds(1), Duration.ofSeconds(2))
                            )
                            .group("MainActions").on(
                                    randomSwitch().on(
                                                    new Choice.WithWeight(30,
                                                            pace(Duration.ofSeconds(30))
                                                                    .exec(MainScenarioActions.products)
                                                                    .exec(MainScenarioActions.addToCart)
                                                                    .pause(Duration.ofSeconds(1), Duration.ofSeconds(2))
                                                                    .exec(MainScenarioActions.cart)
                                                                    .exec(session -> session.set("creationDate", GatlingHelper.createDate(Calendar.DATE, -14)))
                                                                    .exec(MainScenarioActions.createOrder)
                                                                    .exec(MainScenarioActions.cancelOrder)
                                                                    .pause(Duration.ofSeconds(3), Duration.ofSeconds(4))
                                                                    .exec(session -> session.set("creationDate", GatlingHelper.createDate(Calendar.DATE, -2)))
                                                                    .exec(MainScenarioActions.createOrder)
                                                                    .exec(MainScenarioActions.shipOrder)
                                                                    .exec(MainScenarioActions.returnOrder)),
                                                    new Choice.WithWeight(40,
                                                            pace(Duration.ofSeconds(40))
                                                                    .exec(MainScenarioActions.orders)
                                                                    .pause(Duration.ofSeconds(2), Duration.ofSeconds(5))
                                                                    .exec(session -> session.set("creationDate", GatlingHelper.createDate(Calendar.DATE, -7)))
                                                                    .exec(MainScenarioActions.createOrder)
                                                                    .exec(MainScenarioActions.cancelOrder)),
                                                    new Choice.WithWeight(30,
                                                            pace(Duration.ofSeconds(30))
                                                                    .exec(MainScenarioActions.cart)
                                                                    .pause(Duration.ofSeconds(1), Duration.ofSeconds(2))
                                                                    .exec(MainScenarioActions.cart)
                                                                    .exec(session -> session.set("employee", True)
                                                                    )
                                                    )
                                            )
                                            .doIfEqualsOrElse(session -> session.getBoolean("employee"), True).then(
                                                    repeat(4).on(
                                                            exec(MainScenarioActions.products, MainScenarioActions.orders)
                                                    )
                                            ).orElse(exec(MainScenarioActions.createOrder))
                            ).exec(session -> {
                                System.out.println("End Of loop userN" + session.userId() + ", waiting for new loop");
                                return session;
                            })
            );
}
