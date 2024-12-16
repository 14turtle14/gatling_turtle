package gatling_exec;

import io.gatling.recorder.GatlingRecorder;
import io.gatling.recorder.config.RecorderPropertiesBuilder;
import scala.Option;

public class Recorder {
    public static void main(String[] args) {
        RecorderPropertiesBuilder props = new RecorderPropertiesBuilder()
                .simulationsFolder(PathHelper.mavenSourcesDirectory.toString())
                .resourcesFolder(PathHelper.mavenResourcesDirectory.toString())
                .simulationPackage("gatling_exec");

        GatlingRecorder.fromMap(props.build(), Option.apply(PathHelper.recorderConfigFile));
    }
}
