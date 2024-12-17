package gatling_exec;

import gatling_exec.utils.wiremock.WireMockSetup;
import io.gatling.app.Gatling;
import io.gatling.core.config.GatlingPropertiesBuilder;
import org.junit.jupiter.api.Test;

class ExecutorTest {
    @Test
    public void runGatlingTest() {
        WireMockSetup.startWireMock();

        GatlingPropertiesBuilder props = new GatlingPropertiesBuilder()
                .resourcesDirectory(PathHelper.mavenResourcesDirectory.toString())
                .resultsDirectory(PathHelper.resultsDirectory.toString())
                .binariesDirectory(PathHelper.mavenBinariesDirectory.toString());

        Gatling.fromMap(props.build());

        WireMockSetup.stopWireMock();
    }
}
