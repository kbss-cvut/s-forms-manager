package cz.cvut.kbss.sformsmanager.persistence.dao.local;

import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.model.Vocabulary;
import cz.cvut.kbss.sformsmanager.model.persisted.local.FormGenMetadata;
import cz.cvut.kbss.sformsmanager.persistence.dao.remote.QueryTemplate;
import cz.cvut.kbss.sformsmanager.persistence.dao.response.StringIntDateStringResponse;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FormGenMetadataDAO extends LocalWithConnectionBaseDAO<FormGenMetadata> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FormGenMetadataDAO.class);

    private final freemarker.template.Configuration templateCfg;

    @Autowired
    protected FormGenMetadataDAO(EntityManager em, Configuration templateCfg) {
        super(em, FormGenMetadata.class);
        this.templateCfg = templateCfg;
    }

    public int countAllNonEmptyInConnection(@NonNull String connectionName) {
        try {
            return (int) em.createNativeQuery(
                    "SELECT (count(?x) as ?object) WHERE { ?x ?hasConnectionName ?connectionName ;a ?type . ?x ?saveHash ?b }")
                    .setParameter("hasConnectionName", URI.create(Vocabulary.p_connectionName))
                    .setParameter("connectionName", connectionName)
                    .setParameter("saveHash", URI.create(Vocabulary.p_save_hash))
                    .setParameter("type", typeUri).getSingleResult();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not run 'count' on " + typeUri.toString() + ".", e);
        }
    }

    private String queryFromTemplate(QueryTemplate queryTemplate, String connectionName) throws IOException, TemplateException {
        // get the query from template
        Template temp = templateCfg.getTemplate(queryTemplate.getQueryName());
        Map templateParameters = new HashMap();
        templateParameters.put("connectionName", connectionName);
        StringWriter stringWriter = new StringWriter();
        temp.process(templateParameters, stringWriter);

        return stringWriter.toString();
    }

    public List<StringIntDateStringResponse> getLatestFormGensWithHistoryCount(@NonNull String connectionName) {
        try {
            String query = queryFromTemplate(QueryTemplate.LOCAL_FORMGEN_SAVE_HASH_QUERY, connectionName);

            List<Object> response = em.createNativeQuery(query).getResultList();
            if (response.isEmpty()) {
                return new ArrayList<>();
            }
            if (response.get(0).getClass().isArray()) {
                return response.stream().map(o -> { // TODO: make that part of the object itself
                    Object[] responseArray = (Object[]) o;
                    StringIntDateStringResponse sad = new StringIntDateStringResponse();
                    sad.setString((String) responseArray[0]);
                    sad.setInteger((Integer) responseArray[1]);
                    sad.setDate((Date) responseArray[2]);
                    sad.setString1((String) responseArray[3]);
                    return sad;
                }).collect(Collectors.toList());

            } else {
                throw new PersistenceException("Query returns different type than expected.");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PersistenceException("Could not run 'count' on " + typeUri.toString() + ".", e);
        }
    }
}
