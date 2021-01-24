package cz.cvut.kbss.sformsmanager.service.process;

import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;

import java.io.IOException;

public interface FormGenProcessingService {

    FormGenMetadata processFormGen(String connectionName, String contextUri) throws IOException;
}
