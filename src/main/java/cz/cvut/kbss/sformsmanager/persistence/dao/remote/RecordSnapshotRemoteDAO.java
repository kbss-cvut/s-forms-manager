package cz.cvut.kbss.sformsmanager.persistence.dao.remote;

import cz.cvut.kbss.sformsmanager.model.persisted.local.Project;
import cz.cvut.kbss.sformsmanager.model.persisted.response.RecordSnapshotRemoteData;
import cz.cvut.kbss.sformsmanager.persistence.base.RemoteProjectEntityManagerProvider;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.ProjectDAO;
import cz.cvut.kbss.sformsmanager.utils.QueryUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Service
public class RecordSnapshotRemoteDAO extends RemoteEntityDAO {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RecordSnapshotRemoteDAO.class);

    public static final String CLASSPATH_PREFIX = "classpath:";
    private static final String FORMGEN_SAVE_QUERY_FILE = CLASSPATH_PREFIX + "templates/remote/recordSnapshotRemoteData.sparql";

    private ProjectDAO projectDAO;

    @Autowired
    protected RecordSnapshotRemoteDAO(RemoteProjectEntityManagerProvider entityManagerProvider, ProjectDAO projectDAO) {
        super(entityManagerProvider, RecordSnapshotRemoteDAO.class);
        this.projectDAO = projectDAO;
    }

    public RecordSnapshotRemoteData getRecordSnapshot(String projectName, URI contextUri) throws IOException {
        String query = getRecordRecognitionQuery(projectName);
        return (RecordSnapshotRemoteData) executeOnEntityManager(
                projectName,
                em -> em.createNativeQuery(query, "RecordSnapshotRemoteDataResponse")
                        .setParameter("contextUri", contextUri)
                        .getSingleResult(),
                "RecordSnapshotIntermediate formGen context not found: " + contextUri.toString());
    }

    private String getRecordRecognitionQuery(String projectName) throws IOException {
        //  get query from project configuration or default from file

        Optional<Project> projectOpt = projectDAO.findByKey(projectName, projectName);
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();
            String projectSPARQL = project.getRecordRecognitionSPARQL();
            if (projectSPARQL != null && !projectSPARQL.isEmpty()) {
                return projectSPARQL;
            }
        }

        return QueryUtils.getQueryFromFile(FORMGEN_SAVE_QUERY_FILE);
    }
}
