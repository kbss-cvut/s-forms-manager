package cz.cvut.kbss.sformsmanager.utils;

public class ObjectUtils {

    /**
     * Return string of absolute value of Object#hashCode. (only positive numbers)
     *
     * @param o object
     * @return positive String hashCode
     */
    public static String getStringHashCode(Object o) {
        return String.valueOf(Math.abs(o.hashCode()));
    }
}
