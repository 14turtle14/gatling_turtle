package gatling_exec.utils;

import gatling_exec.actions.AuthorizationAction;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.CoreDsl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.exec;

public class GatlingHelper {
    public static final Iterator<Map<String, Object>> feeder =
            Stream.generate((Supplier<Map<String, Object>>) () -> Map.of(
                            "clientId", String.format("1%014d", (ThreadLocalRandom.current().nextInt(10001, 200000))),
                            "creationDate", createDate(Calendar.DATE, -1),
                            "randomName", generateRandomName(6),
                            "randomSurname", generateRandomSurname(10),
                            "shippingAddress", "Vavilova k. 3"
                    )
            ).iterator();

    public static String createDate(int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(field, amount);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(calendar.getTime());
    }

    private static String generateRandomName(int range) {
        Random random = new Random();
        return IntStream.range(0, range)
                .mapToObj(i -> String.valueOf((char) (random.nextInt(26) + 'a')))
                .collect(Collectors.joining());
    }

    private static String generateRandomSurname(int range) {
        Random random = new Random();
        return IntStream.range(0, range)
                .mapToObj(i -> String.valueOf((char) (random.nextInt(26) + 'a')))
                .collect(Collectors.joining()) + "ov";
    }

    public static final ChainBuilder authorization = CoreDsl
            .feed(feeder)
            .exec(AuthorizationAction.authorization)
            .randomSwitch().on(
                    new Choice.WithWeight(29, exec(session -> session.set("paymentMethod", "CARD"))),
                    new Choice.WithWeight(29, exec(session -> session.set("paymemtMethod", "CASH"))),
                    new Choice.WithWeight(29, exec(session -> session.set("paymentMethod", "SBP"))),
                    new Choice.WithWeight(10, exec(session -> session.set("paymentMethod", "ATM"))),
                    new Choice.WithWeight(2, exec(session -> session.set("paymentMethod", "NOTHING")))
            );
}
