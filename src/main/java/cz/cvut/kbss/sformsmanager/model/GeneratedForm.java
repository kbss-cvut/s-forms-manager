package cz.cvut.kbss.sformsmanager.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneratedForm {

    private String key;
    private String rawJson;
    private GraphContextJsonLD objectRepresentation;

    public GraphContextJsonLD getObjectRepresentation() throws JsonProcessingException {
        if (objectRepresentation != null) {
            return this.objectRepresentation;
        }
        ObjectMapper mapper = new ObjectMapper();
        GraphContextJsonLD objectRepresentation = mapper.readValue(rawJson, GraphContextJsonLD.class);
        this.objectRepresentation = objectRepresentation;
        return objectRepresentation;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GraphContextJsonLD {
        @JsonProperty("@graph")
        private List<GraphJsonLD> graph;
        @JsonProperty("@context")
        private Object context;

        private int hashCode;

        @Override
        public int hashCode() {
            if (hashCode != 0) {
                return hashCode;
            }
            List<String> listOfQuestions = graph.stream()
                    .filter(node -> node.getQuestionOrigin() != null)
                    .map(node -> node.getQuestionOrigin())
                    .collect(Collectors.toList());
            return Objects.hashCode(listOfQuestions);
        }
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GraphJsonLD {
        @JsonProperty("has-question-origin")
        String questionOrigin;
    }
}
