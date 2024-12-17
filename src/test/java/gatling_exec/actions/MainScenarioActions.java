package gatling_exec.actions;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;

import static io.gatling.javaapi.core.CoreDsl.ElFileBody;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.http.HttpDsl.http;

public class MainScenarioActions {
    public static final ChainBuilder products = CoreDsl
            .exec(http("Products")
                    .post("/products")
                    .body(ElFileBody("bodies/name_surname_clientId.json")).asJson()
                    .check(jsonPath("$..id").exists().saveAs("productId"))
            );

    public static final ChainBuilder cart = CoreDsl
            .exec(http("Cart")
                    .post("/cart")
                    .body(ElFileBody("bodies/name_surname_clientId.json")).asJson()
                    .check(jsonPath("$.cart").saveAs("cart"))
            );

    public static final ChainBuilder orders = CoreDsl
            .exec(http("Orders")
                    .post("/orders")
                    .body(ElFileBody("bodies/name_surname_clientId.json")).asJson()
                    .check(jsonPath("$..orderId").exists().saveAs("orderId"))
            );

    public static final ChainBuilder addToCart = CoreDsl
            .exec(http("AddToCart")
                    .post("/cart/add")
                    .body(ElFileBody("bodies/name_surname_clientId_productId_quantity.json")).asJson()
                    .check(jsonPath("$.message").is("Product added to cart"))
            );

    public static final ChainBuilder createOrder = CoreDsl
            .exec(http("CreateOrder")
                    .post("/order/create")
                    .body(ElFileBody("bodies/clientId_name_surname_cart_paymentMethod_shippingAddress.json")).asJson()
                    .check(
                            jsonPath("$.orderId").saveAs("oderId"),
                            jsonPath("$.status").is("created")
                    )
            );

    public static final ChainBuilder cancelOrder = CoreDsl
            .exec(http("CancelOrder")
                    .post("/order/#{orderId}/cancel")
                    .body(ElFileBody("bodies/name_surname_clientId.json")).asJson()
                    .check(
                            jsonPath("$.orderId").saveAs("oderId"),
                            jsonPath("$.status").is("cancelled")
                    )
            );

    public static final ChainBuilder shipOrder = CoreDsl
            .exec(http("ShipOrder")
                    .post("/order/#{orderId}/shipping")
                    .body(ElFileBody("bodies/name_surname_clientId.json")).asJson()
                    .check(jsonPath("$.orderId").saveAs("oderId"),
                            jsonPath("$.status").is("shipped"))
            );

    public static final ChainBuilder returnOrder = CoreDsl
            .exec(http("returnOrder")
                    .post("/order/#{orderId}/return")
                    .body(ElFileBody("bodies/name_surname_clientId.json")).asJson()
                    .check(jsonPath("$.orderId").saveAs("oderId"),
                            jsonPath("$.status").is("returned"))
            );
}
