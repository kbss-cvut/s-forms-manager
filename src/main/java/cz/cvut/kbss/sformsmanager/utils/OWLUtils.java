package cz.cvut.kbss.sformsmanager.utils;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OWLUtils {

    /**
     * Gets IRI of the OWL class mapped by the specified entity.
     *
     * @param entityClass Entity class
     * @return IRI of mapped OWL class (as String)
     */
    public String getOwlClassForEntity(Class<?> entityClass) {
        final OWLClass owlClass = entityClass.getDeclaredAnnotation(OWLClass.class);
        if (owlClass == null) {
            throw new IllegalArgumentException("Class " + entityClass + " is not an entity.");
        }
        return owlClass.iri();
    }

    public String createFormGenMetadataKey(String connectionName, String contextUri) {
        return connectionName + "/" + contextUri;
    }

    public String createFormGenVersionTagKey(String connectionName, int hashcode) {
        return connectionName + "/" + String.valueOf(hashcode);
    }
}
