package cz.cvut.kbss.sformsmanager.service.process;

import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface FormGenProcessingService {

    FormGenMetadata getFormGenMetadata(String connectionName, String contextUri) throws IOException, TemplateException;
}
