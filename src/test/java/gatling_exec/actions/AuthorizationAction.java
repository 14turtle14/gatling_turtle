package gatling_exec.actions;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;

import static io.gatling.javaapi.core.CoreDsl.ElFileBody;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.http.HttpDsl.http;

public class AuthorizationAction {
    public static final ChainBuilder authorization = CoreDsl
            .exec(http("Authorization")
                    .post("/auth")
                    .header("password", "141414")
                    .body(ElFileBody("bodies/name_surname_clientId.json")).asJson()
                    .check(jsonPath("$.authenticated").is("true"))
            );
}
