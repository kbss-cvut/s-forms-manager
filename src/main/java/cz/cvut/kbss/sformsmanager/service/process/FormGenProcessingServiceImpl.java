package cz.cvut.kbss.sformsmanager.service.process;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.kbss.sformsmanager.model.dto.FormGenRawJson;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenInstance;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.model.persisted.FormGenVersion;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenInstanceDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenMetadataDAO;
import cz.cvut.kbss.sformsmanager.persistence.dao.FormGenVersionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormGenProcessingServiceImpl implements FormGenProcessingService {

    private final FormGenInstanceDAO instanceDAO;
    private final FormGenVersionDAO versionDAO;
    private final FormGenMetadataDAO metadataDAO;

    @Autowired
    public FormGenProcessingServiceImpl(FormGenInstanceDAO instanceDAO, FormGenVersionDAO versionTagDAO, FormGenMetadataDAO metadataDAO) {
        this.instanceDAO = instanceDAO;
        this.versionDAO = versionTagDAO;
        this.metadataDAO = metadataDAO;
    }

    @Transactional
    public FormGenMetadata getFormGenMetadata(FormGenRawJson formGenRawJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        FormGenJsonLd formGenJsonLd = mapper.readValue(formGenRawJson.getRawJson(), FormGenJsonLd.class);

        // formGen version
        int hashCode = formGenJsonLd.hashCode();
        String versionKey = FormGenVersion.createKey(formGenRawJson.getConnectionName(), hashCode);
        Optional<FormGenVersion> versionTagOptional = versionDAO.findByKey(versionKey);
        FormGenVersion formGenVersion = versionTagOptional.orElse(
                new FormGenVersion(formGenRawJson.getConnectionName(), formGenRawJson.getContextUri(), versionDAO.count(), hashCode));

        // formGen intance
        int instanceNumber = formGenJsonLd.getInstanceNumber(formGenRawJson.getConnectionName(), formGenRawJson.getContextUri());
        String instanceKey = FormGenInstance.createKey(formGenRawJson.getConnectionName(), formGenRawJson.getContextUri());
        Optional<FormGenInstance> instanceOptional = instanceDAO.findByKey(instanceKey);
        FormGenInstance formGenInstance = instanceOptional.orElse(
                new FormGenInstance(formGenRawJson.getConnectionName(), formGenRawJson.getContextUri(), instanceNumber));

        // formGen
        String formGenMetadataKey = FormGenMetadata.createKey(formGenRawJson.getConnectionName(), formGenRawJson.getContextUri());
        Optional<FormGenMetadata> formGenMetadataOptional = metadataDAO.findByKey(formGenMetadataKey);
        FormGenMetadata formGenMetadata = formGenMetadataOptional.orElse(
                new FormGenMetadata(formGenVersion, formGenInstance, formGenRawJson.getContextUri(), formGenRawJson.getConnectionName()));

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
            // TODO: study-manager save action =-> jopa objekty Q&A, vytahnout question origin pro vsechny otazky a pro ne udelat hash

            List<String> listOfQuestions = graph.stream()
                    .filter(node -> node.getQuestionOrigin() != null)
                    .map(node -> node.getQuestionOrigin())
                    .collect(Collectors.toList());
            Integer hashCode = java.util.Objects.hash(listOfQuestions);
            return hashCode;
        }

        /**
         * So far only a return a sum of all the provided characters codes.
         *
         * @return instance identifier
         */
        public int getInstanceNumber(String connectionName, String contextUri) {
            //


            // TODO: v idealnim pripade bude rozlisovani verze probihat na zaklade konfigurovateleho sparQL dotazu
            // nebo alternativou je pridat to do S-Forms - cas ulozeni
            return (connectionName + contextUri).chars()
                    .reduce(0, (subtotal, element) -> subtotal + element);
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
            if (!Objects.equals(this$questionOrigin, other$questionOrigin))
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
