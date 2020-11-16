package cz.cvut.kbss.sformsmanager.service.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.cvut.kbss.sformsmanager.model.dto.FormGenRawJson;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenMetadata;

public interface FormGenProcessingService {

    FormGenMetadata getFormGenMetadata(FormGenRawJson formGenRawJson) throws JsonProcessingException;
}
