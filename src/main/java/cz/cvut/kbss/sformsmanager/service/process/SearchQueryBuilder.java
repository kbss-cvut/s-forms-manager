package cz.cvut.kbss.sformsmanager.service.process;

import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

import static cz.cvut.kbss.sformsmanager.model.Vocabulary.*;
import static cz.cvut.kbss.sformsmanager.utils.PredicateUtils.not;


public class SearchQueryBuilder {

    private static final Prefix[] POSSIBLE_PREFIXES = {
            new Prefix("formsmanager", URI_BASE),
            new Prefix("doc", DOC_URI),
            new Prefix("form", FORM_URI)};

    private static final String SEARCH_QUERY_FILE = "searchQueryTemplate.ftl";

    @Autowired
    private freemarker.template.Configuration templateCfg;

    private final Map<String, Object> templateParameters = new HashMap<>();
    private final Set<Prefix> usedPrefixes = new HashSet<>();

    public SearchQueryBuilder(freemarker.template.Configuration templateCfg) {
        this.templateCfg = templateCfg;
        setupVocabulary();
    }

    private void setupVocabulary() {
        addVocabularyParameter("classURI", Vocabulary.FormGenMetadata);
        addVocabularyParameter("versionAssignedURI", Vocabulary.p_hasVersion);
        addVocabularyParameter("versionClassURI", Vocabulary.p_versionName);
        addVocabularyParameter("saveHashURI", Vocabulary.p_save_hash);
        addVocabularyParameter("synonymURI", Vocabulary.p_internalName);
        addVocabularyParameter("formGenMetadataURI", Vocabulary.FormGenMetadata);
        addVocabularyParameter("connectionNameURI", Vocabulary.p_connectionName);
        addVocabularyParameter("formGenSaveHashURI", Vocabulary.p_save_hash);
        addVocabularyParameter("formGenModifiedURI", Vocabulary.p_formGen_modified);
    }

    public void setVersions(List<String> versions) {
        List<String> fixedVersions = prepareParameters(versions);
        if (!fixedVersions.isEmpty()) {
            templateParameters.put("versions", fixedVersions);
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
        templateParameters.put("prefixes", usedPrefixes);

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

    private void addVocabularyParameter(String parameterName, String vocabulary) {
        Optional<Prefix> usedPrefixOpt = Arrays.stream(POSSIBLE_PREFIXES).filter(prefix -> vocabulary.contains(prefix.getUri())).findAny();
        if (usedPrefixOpt.isPresent()) {
            Prefix usedPrefix = usedPrefixOpt.get();
            usedPrefixes.add(usedPrefix);
            templateParameters.put(parameterName, usedPrefix.getName() + ":" + vocabulary.substring(usedPrefix.getUri().length()));
        } else {
            templateParameters.put(parameterName, vocabulary);
        }
    }

    public static class Prefix {
        private final String name;
        private final String uri;

        private Prefix(String name, String uri) {
            this.name = name;
            this.uri = uri;
        }

        public String getName() {
            return name;
        }

        public String getUri() {
            return uri;
        }
    }
}
