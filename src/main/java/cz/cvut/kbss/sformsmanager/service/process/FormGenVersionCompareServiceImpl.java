package cz.cvut.kbss.sformsmanager.service.process;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.kbss.sformsmanager.exception.VersionNotFoundException;
import cz.cvut.kbss.sformsmanager.model.dto.FormGenRawJson;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenVersion;
import cz.cvut.kbss.sformsmanager.service.data.FormGenJsonLoader;
import cz.cvut.kbss.sformsmanager.service.model.local.FormGenVersionService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormGenVersionCompareServiceImpl implements FormGenVersionCompareService {

    private final FormGenJsonLoader formGenJsonLoader;
    private final FormGenVersionService versionService;

    @Autowired
    public FormGenVersionCompareServiceImpl(FormGenJsonLoader formGenJsonLoader, FormGenVersionService versionService) {
        this.formGenJsonLoader = formGenJsonLoader;
        this.versionService = versionService;
    }

    @Override
    public String getMergedVersionsJson(String connectionName, String versionName1, String versionName2) throws URISyntaxException {
        FormGenVersion formGenVersion1 = versionService.findByVersionNameOrSynonym(versionName1)
                .orElseThrow(() -> new VersionNotFoundException(versionName1 + " version not found!"));
//        FormGenVersion formGenVersion2 = versionService.findByVersionNameOrSynonym(versionName2)
//                .orElseThrow(() -> new VersionNotFoundException(versionName2 + " version not found!"));

        FormGenRawJson versionJson1 = formGenJsonLoader.getFormGenRawJsonFromConnection(connectionName, formGenVersion1.getSampleContextUri());
//        FormGenRawJson versionJson2 = formGenJsonLoader.getFormGenRawJsonFromConnection(connectionName, formGenVersion2.getSampleContextUri());
        FormGenRawJson versionJson2 = null;
        try {
            versionJson2 = new FormGenRawJson(null, null, new String(Files.readAllBytes(ResourceUtils.getFile("classpath:form_with_categories.json").toPath())));
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            ObjectMapper mapper = new ObjectMapper();
            FormGenJsonLd formGenJsonLd1 = mapper.readValue(versionJson1.getRawJson(), FormGenJsonLd.class);
            FormGenJsonLd formGenJsonLd2 = mapper.readValue(versionJson2.getRawJson(), FormGenJsonLd.class);

            List<String> questionOrigins1 = formGenJsonLd1.getGraph().stream()
                    .filter(node -> node.containsKey("has-question-origin"))
                    .map(node -> (String) node.get("has-question-origin")) // question-origin is always a String
                    .collect(Collectors.toList());

            List<String> questionOrigins2 = formGenJsonLd2.getGraph().stream()
                    .filter(node -> node.containsKey("has-question-origin"))
                    .map(node -> (String) node.get("has-question-origin")) // question-origin is always a String
                    .collect(Collectors.toList());

            List<String> intersection = new ArrayList<>(CollectionUtils.intersection(questionOrigins1, questionOrigins2));
            questionOrigins1.removeAll(intersection);
            questionOrigins2.removeAll(intersection);

            formGenJsonLd1.getGraph().forEach(node -> {
                questionOrigins1.forEach(qo -> {
                    if (node.getOrDefault("has-question-origin", "").equals(qo)) {
                        if (node.containsKey("has-layout-class")) {

                            Object layoutClass = node.get("has-layout-class");
//                            if (layoutClass instanceof LinkedHashMap) {
//                                LinkedHashMap<String, String> layoutMap = (LinkedHashMap<String, String>) layoutClass;
//
//
//                            } else if (layoutClass instanceof String) {
//
//                            }
                        }
                    }
                });
            });

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class FormGenJsonLd {

        @JsonProperty("@graph")
        private List<LinkedHashMap<String, Object>> graph;

        @JsonProperty("@context")
        private Object context;

        public FormGenJsonLd() {
        }

        public Object getContext() {
            return this.context;
        }

        public List<LinkedHashMap<String, Object>> getGraph() {
            return graph;
        }

        public void setGraph(List<LinkedHashMap<String, Object>> graph) {
            this.graph = graph;
        }

        public void setContext(Object context) {
            this.context = context;
        }
    }
}
