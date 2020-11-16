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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormGenProcessingServiceImpl implements FormGenProcessingService {

    private final FormGenVersionTagDAO versionTagDAO;
    private final FormGenMetadataDAO metadataDAO;

    @Autowired
    public FormGenProcessingServiceImpl(FormGenVersionTagDAO versionTagDAO, FormGenMetadataDAO metadataDAO) {
        this.versionTagDAO = versionTagDAO;
        this.metadataDAO = metadataDAO;
    }

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
            versionTag = new FormGenVersionTag(formGenRawJson.getConnectionName() + versionTagDAO.count(), versionTagKey);
        }

        String formGenMetadataKey = OWLUtils.createFormGenkey(formGenRawJson.getConnectionName(), formGenRawJson.getContextUri());
        Optional<FormGenMetadata> formGenMetadataOptional = metadataDAO.findByKey(formGenMetadataKey);
        FormGenMetadata formGenMetadata;
        if (formGenMetadataOptional.isPresent()) {
            formGenMetadata = formGenMetadataOptional.get();
            formGenMetadata.setVersionTag(versionTag);
        } else {
            formGenMetadata = new FormGenMetadata(
                    versionTag,
                    formGenRawJson.getContextUri(),
                    formGenRawJson.getConnectionName(),
                    OWLUtils.createFormGenkey(
                            formGenRawJson.getConnectionName(),
                            formGenRawJson.getContextUri()));
        }

        return formGenMetadata;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class FormGenJsonLd {

        @JsonProperty("@graph")
        private List<FormGenJsonLdNode> graph;

        @JsonProperty("@context")
        private Object context;

        public FormGenJsonLd() {
        }

        @Override
        public int hashCode() {
            List<String> listOfQuestions = graph.stream()
                    .filter(node -> node.getQuestionOrigin() != null)
                    .map(node -> node.getQuestionOrigin())
                    .collect(Collectors.toList());
            Integer hashCode = java.util.Objects.hash(listOfQuestions);
            return hashCode;
        }

        public List<FormGenJsonLdNode> getGraph() {
            return this.graph;
        }

        public Object getContext() {
            return this.context;
        }

        public void setGraph(List<FormGenJsonLdNode> graph) {
            this.graph = graph;
        }

        public void setContext(Object context) {
            this.context = context;
        }

        public String toString() {
            return "FormGenProcessingService.FormGenJsonLd(graph=" + this.getGraph() + ", context=" + this.getContext() + ")";
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class FormGenJsonLdNode {

        @JsonProperty("has-question-origin")
        String questionOrigin;

        public FormGenJsonLdNode() {
        }

        public String getQuestionOrigin() {
            return this.questionOrigin;
        }

        public void setQuestionOrigin(String questionOrigin) {
            this.questionOrigin = questionOrigin;
        }

        public boolean equals(final Object o) {
            if (o == this) return true;
            if (!(o instanceof FormGenJsonLdNode))
                return false;
            final FormGenJsonLdNode other = (FormGenJsonLdNode) o;
            if (!other.canEqual((Object) this)) return false;
            final Object this$questionOrigin = this.getQuestionOrigin();
            final Object other$questionOrigin = other.getQuestionOrigin();
            if (this$questionOrigin == null ? other$questionOrigin != null : !this$questionOrigin.equals(other$questionOrigin))
                return false;
            return true;
        }

        protected boolean canEqual(final Object other) {
            return other instanceof FormGenJsonLdNode;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $questionOrigin = this.getQuestionOrigin();
            result = result * PRIME + ($questionOrigin == null ? 43 : $questionOrigin.hashCode());
            return result;
        }

        public String toString() {
            return "FormGenProcessingService.FormGenJsonLdNode(questionOrigin=" + this.getQuestionOrigin() + ")";
        }
    }
}
