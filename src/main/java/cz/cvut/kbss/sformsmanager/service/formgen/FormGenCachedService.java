package cz.cvut.kbss.sformsmanager.service.formgen;

import cz.cvut.kbss.sformsmanager.model.dto.SFormsRawJson;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class FormGenCachedService implements FormGenJSonLoader {

    private final LocalFormGenJsonLoader localFormGenJsonLoader;
    private final RemoteFormGenJsonLoader remoteFormGenJsonLoader;

    private final Repository repository;

    @Autowired
    public FormGenCachedService(LocalFormGenJsonLoader localFormGenJsonLoader, RemoteFormGenJsonLoader remoteFormGenJsonLoader, Repository repository) {
        this.localFormGenJsonLoader = localFormGenJsonLoader;
        this.remoteFormGenJsonLoader = remoteFormGenJsonLoader;
        this.repository = repository;
    }

    /**
     * Loads formGen from local repository if it exists. If it does not, load it remotely and cache it.
     */
    @Override
    public SFormsRawJson getFormGenRawJson(String projectName, URI contextUri) throws URISyntaxException, IOException {
        SFormsRawJson localFormGen = localFormGenJsonLoader.getFormGenRawJson(projectName, contextUri);
        if (localFormGen != null) {
            return localFormGen;
        }
        SFormsRawJson remoteFormGen = remoteFormGenJsonLoader.getFormGenRawJson(projectName, contextUri);

        // save (cache) formGen to local repository
        Resource contextResource = localFormGenJsonLoader.createFormGenSimpleResource(projectName, contextUri);
        repository.getConnection().add(new StringReader(remoteFormGen.getRawJson()), null, RDFFormat.JSONLD, contextResource);
        return remoteFormGen;
    }

    /**
     * Loads possible values from remote address.
     *
     * @param query
     * @return
     * @throws URISyntaxException
     */
    @Override
    public String getFormGenPossibleValues(String query) throws URISyntaxException {
        return remoteFormGenJsonLoader.getFormGenPossibleValues(query);
    }
}
