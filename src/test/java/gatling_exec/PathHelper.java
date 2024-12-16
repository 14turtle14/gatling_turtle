package gatling_exec;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;

public class PathHelper {

    static final Path mavenResourcesDirectory;
    static final Path mavenBinariesDirectory;
    static final Path mavenSourcesDirectory;
    static final Path resultsDirectory;
    static final Path recorderConfigFile;

    static {
        try {
            Path projectRootDir = Paths.get(requireNonNull(PathHelper.class.getResource("gatling.conf"), "Couldn't locate gatling.conf").toURI()).getParent().getParent().getParent();
            Path mavenTargetDirectory = projectRootDir.resolve("target");
            Path mavenSrcTestDirectory = projectRootDir.resolve("src").resolve("test");

            mavenSourcesDirectory = mavenSrcTestDirectory.resolve("java");
            mavenResourcesDirectory = mavenSrcTestDirectory.resolve("");
            mavenBinariesDirectory = mavenTargetDirectory.resolve("test-classes");
            resultsDirectory = mavenTargetDirectory.resolve("recorder.conf");
            recorderConfigFile = mavenResourcesDirectory.resolve("recorder.conf");
        } catch (URISyntaxException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
