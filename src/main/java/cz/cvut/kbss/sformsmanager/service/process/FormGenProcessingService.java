package cz.cvut.kbss.sformsmanager.service.process;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.kbss.sformsmanager.model.dto.FormGenRawJson;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenVersionTag;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenMetadataDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenVersionTagDAO;
import cz.cvut.kbss.sformsmanager.utils.OWLUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormGenProcessingService {

    private final FormGenVersionTagDAO versionTagDAO;
    private final FormGenMetadataDAO metadataDAO;

    @Transactional
    public FormGenMetadata getFormGenMetadata(FormGenRawJson formGenRawJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        FormGenJsonLd formGenJsonLd = mapper.readValue(formGenRawJson.getRawJson(), FormGenJsonLd.class);

        String versionTagKey = OWLUtils.createFormGenVersionTagKey(formGenRawJson.getConnectionName(), formGenJsonLd.hashCode());
        Optional<FormGenVersionTag> versionTagOptional = versionTagDAO.findByKey(versionTagKey);
        FormGenVersionTag versionTag;
        if (versionTagOptional.isPresent()) {
            versionTag = versionTagOptional.get();
        } else {
            versionTag = FormGenVersionTag.builder()
                    .version(formGenRawJson.getConnectionName() + versionTagDAO.count())
                    .key(versionTagKey)
                    .build();
        }

        String formGenMetadataKey = OWLUtils.createFormGenMetadataKey(formGenRawJson.getConnectionName(), formGenRawJson.getContextUri());
        Optional<FormGenMetadata> formGenMetadataOptional = metadataDAO.findByKey(formGenMetadataKey);
        FormGenMetadata formGenMetadata;
        if (formGenMetadataOptional.isPresent()) {
            formGenMetadata = formGenMetadataOptional.get();
            formGenMetadata.setVersionTag(versionTag);
        } else {
            formGenMetadata = FormGenMetadata.builder()
                    .connectionName(formGenRawJson.getConnectionName())
                    .contextUri(formGenRawJson.getContextUri())
                    .versionTag(versionTag)
                    .key(OWLUtils.createFormGenMetadataKey(formGenRawJson.getConnectionName(), formGenRawJson.getContextUri()))
                    .build();
        }

        return formGenMetadata;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class FormGenJsonLd {

        @JsonProperty("@graph")
        private List<FormGenJsonLdNode> graph;

        @JsonProperty("@context")
        private Object context;

        @Override
        public int hashCode() {
            List<String> listOfQuestions = graph.stream()
                    .filter(node -> node.getQuestionOrigin() != null)
                    .map(node -> node.getQuestionOrigin())
                    .collect(Collectors.toList());
            Integer hashCode = java.util.Objects.hash(listOfQuestions);
            return hashCode;
        }
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class FormGenJsonLdNode {

        @JsonProperty("has-question-origin")
        String questionOrigin;
    }
}
