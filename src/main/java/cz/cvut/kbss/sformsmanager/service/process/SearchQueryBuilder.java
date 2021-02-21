package cz.cvut.kbss.sformsmanager.service.process;

import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cz.cvut.kbss.sformsmanager.utils.PredicateUtils.not;


public class SearchQueryBuilder {

    private static final String SEARCH_QUERY_FILE = "searchQueryTemplate.ftl";

    @Autowired
    private freemarker.template.Configuration templateCfg;

    static int i = 0;

    public boolean latestSaves;
    private final Map<String, Object> templateParameters = new HashMap<>();

    public SearchQueryBuilder(freemarker.template.Configuration templateCfg) {
        this.templateCfg = templateCfg;
        setupDefaultParameters();
        i++;
    }

    private void setupDefaultParameters() {
        templateParameters.put("classURI", Vocabulary.FormGenMetadata);
        templateParameters.put("versionAssignedURI", Vocabulary.p_assigned_version);
        templateParameters.put("versionClassURI", Vocabulary.p_versionName);
        templateParameters.put("saveHashURI", Vocabulary.p_save_hash);
        templateParameters.put("synonymURI", Vocabulary.p_synonym);
    }

    public void setVersions(List<String> versions) {
        List<String> fixedVersions = prepareParameters(versions);
        if (!fixedVersions.isEmpty()) {
            templateParameters.put("versions", versions);
        }
    }

    public void setSaveHashes(List<String> saveHashes) {
        List<String> fixedSaveHashes = prepareParameters(saveHashes);
        if (!fixedSaveHashes.isEmpty()) {
            templateParameters.put("saveHashes", fixedSaveHashes);
        }
    }

    public void setLatestSaves(boolean latestSaves) {
        if (latestSaves) {
            templateParameters.put("latestSavesOnly", true);
        }
    }

    public void setConnectionName(String connectionName) {
        templateParameters.put("connectionName", connectionName);
    }

    public String build() throws IOException, TemplateException {
        Template temp = templateCfg.getTemplate(SEARCH_QUERY_FILE);
        StringWriter stringWriter = new StringWriter();
        temp.process(templateParameters, stringWriter);

        return stringWriter.toString();
    }

    public static List<String> prepareParameters(List<String> parameters) {
        return parameters.stream()
                .filter(not(String::isEmpty))
                .map(String::trim)
                .distinct()
                .collect(Collectors.toList());
    }
}
