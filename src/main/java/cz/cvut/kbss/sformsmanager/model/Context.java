package cz.cvut.kbss.sformsmanager.model;

public class Context {

    private final String uriString;
    private final boolean isProcessed;
    private final boolean isProcessedSet;

    public Context(String uriString, boolean isProcessed) {
        this.uriString = uriString;
        this.isProcessed = isProcessed;
        this.isProcessedSet = true;
    }

    public Context(String uriString) {
        this.uriString = uriString;
        this.isProcessed = false;
        this.isProcessedSet = false;
    }

    public String getUriString() {
        return uriString;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public boolean isProcessedSet() {
        return isProcessedSet;
    }
}
