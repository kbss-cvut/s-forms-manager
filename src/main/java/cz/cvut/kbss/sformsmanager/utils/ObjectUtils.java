package cz.cvut.kbss.sformsmanager.utils;

public class ObjectUtils {

    /**
     * Return string of absolute value of Object#hashCode. (only positive numbers)
     *
     * @param o object
     * @return positive String hashCode
     */
    public static String createStringHashCode(Object o) {
        return String.valueOf(Math.abs(o.hashCode()));
    }

    /**
     * Creates hash from the given object and puts it together with context identifier.
     *
     * @param projectDescriptorName - context identifier
     * @param o                     - object for hashing
     * @return context initials + object hash
     */
    public static String createKeyForContext(String projectDescriptorName, Object o) {
        return OWLUtils.createInitialsAndConcat(projectDescriptorName, createStringHashCode(o));
    }
}
