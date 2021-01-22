package cz.cvut.kbss.sformsmanager.persistence.dao.remote;

import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.sformsmanager.exception.PersistenceException;
import cz.cvut.kbss.sformsmanager.persistence.base.ConnectionEntityManagerProvider;
import cz.cvut.kbss.sformsmanager.persistence.dao.response.StringAndDateResponse;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ContextQueryDAO {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ContextQueryDAO.class);

    private final ConnectionEntityManagerProvider entityManagerProvider;
    private final freemarker.template.Configuration templateCfg;

    @Autowired
    public ContextQueryDAO(ConnectionEntityManagerProvider entityManagerProvider, Configuration templateCfg) {
        this.entityManagerProvider = entityManagerProvider;
        this.templateCfg = templateCfg;
    }

    private String queryFromTemplate(String contextUri, QueryTemplate queryTemplate) throws IOException, TemplateException {
        // get the query from template
        Template temp = templateCfg.getTemplate(queryTemplate.getQueryName());
        Map templateParameters = new HashMap();
        templateParameters.put("contextUri", contextUri);
        StringWriter stringWriter = new StringWriter();
        temp.process(templateParameters, stringWriter);

        return stringWriter.toString();
    }

    public <T> T executeQuerySingleColumnResponse(String connectionName, String contextUri, QueryTemplate queryTemplate) throws IOException, TemplateException {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);

        Class<T> clazz = queryTemplate.getResultType();
        String query = queryFromTemplate(contextUri, queryTemplate);

        try {
            return (T) em.createNativeQuery(query, clazz).getSingleResult();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException(e);
        }
    }

    public Optional<StringAndDateResponse> executeQuery(String connectionName, String contextUri, QueryTemplate queryTemplate) throws IOException, TemplateException {
        EntityManager em = entityManagerProvider.getEntityManagerFactory(connectionName);

        String query = queryFromTemplate(contextUri, queryTemplate);

        try {
            Object response = em.createNativeQuery(query).getSingleResult();
            if (response.getClass().isArray()) {

                Object[] responseArray = (Object[]) response;
                StringAndDateResponse sad = new StringAndDateResponse();
                if (responseArray[0] != null)
                    sad.setString((String) responseArray[0]);
                if (responseArray[1] != null)
                    sad.setDate((Date) responseArray[1]);

                return Optional.of(sad);
            } else {
                throw new PersistenceException("Query returns different type than expected.");
            }

        } catch (NoResultException e) {
            return Optional.empty();

        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new PersistenceException(e);
        }
    }
}
