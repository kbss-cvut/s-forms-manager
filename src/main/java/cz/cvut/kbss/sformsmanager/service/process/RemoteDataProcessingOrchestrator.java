package cz.cvut.kbss.sformsmanager.service.process;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public interface RemoteDataProcessingOrchestrator {

    void processDataSnapshotInRemoteContext(String projectName, URI contextUri) throws IOException, URISyntaxException;
}
