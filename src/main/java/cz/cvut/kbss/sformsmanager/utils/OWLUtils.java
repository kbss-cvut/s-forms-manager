package cz.cvut.kbss.sformsmanager.utils;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;

public final class OWLUtils {

    private OWLUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Gets IRI of the OWL class mapped by the specified entity.
     *
     * @param entityClass Entity class
     * @return IRI of mapped OWL class (as String)
     */
    public static String getOwlClassForEntity(Class<?> entityClass) {
        final OWLClass owlClass = entityClass.getDeclaredAnnotation(OWLClass.class);
        if (owlClass == null) {
            throw new IllegalArgumentException("Class " + entityClass + " is not an entity.");
        }
        return owlClass.iri();
    }

    public static String createFormGenkey(String connectionName, String contextUri) {
        return connectionName + "/" + contextUri;
    }

    public static String createFormGenVersionTagKey(String connectionName, int hashcode) {
        return connectionName + "/" + hashcode;
    }
}
