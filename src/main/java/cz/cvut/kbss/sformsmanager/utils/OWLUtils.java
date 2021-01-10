package cz.cvut.kbss.sformsmanager.utils;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;

import java.util.Arrays;
import java.util.stream.Collectors;

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

//    public static String getOwlObjectPropertyForEntity(Class<?> entityClass, String property) throws NoSuchFieldException {
//        OWLObjectProperty annotation = entityClass.getField(property).getDeclaredAnnotation(OWLObjectProperty.class);
//        return annotation.iri();
//    }

    /**
     * Take initials of the first string delimited by '-' and concat it with '/' and the second parameter.
     *
     * @param toTakeInitials
     * @param word
     * @return
     */
    public static String createInitialsAndConcatWithSlash(String toTakeInitials, String word) {
        String connectionNameInitials = Arrays.stream(toTakeInitials.split("-")).map(part -> String.valueOf(part.charAt(0))).collect(Collectors.joining());
        return connectionNameInitials + "/" + word;
    }

    public static String createInitialsAndConcatWithSlash(String toTakeInitials, int number) {
        return createInitialsAndConcatWithSlash(toTakeInitials, String.valueOf(number));
    }
}
