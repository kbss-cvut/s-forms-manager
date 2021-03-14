package cz.cvut.kbss.sformsmanager.service.process;

import java.io.IOException;
import java.net.URI;

public interface RemoteDataProcessingOrchestrator {

    void processDataSnapshotInRemoteContext(String connectionName, URI contextUri) throws IOException;
}
