package gatling_exec;

import gatling_exec.utils.wiremock.WireMockSetup;
import io.gatling.app.Gatling;
import io.gatling.core.config.GatlingPropertiesBuilder;

public class Executor {
    public static void main(String[] args) {
        WireMockSetup.startWireMock();

        GatlingPropertiesBuilder props = new GatlingPropertiesBuilder()
                .resourcesDirectory(PathHelper.mavenResourcesDirectory.toString())
                .resultsDirectory(PathHelper.resultsDirectory.toString())
                .binariesDirectory(PathHelper.mavenBinariesDirectory.toString());

        Gatling.fromMap(props.build());

        WireMockSetup.stopWireMock();
    }
}

