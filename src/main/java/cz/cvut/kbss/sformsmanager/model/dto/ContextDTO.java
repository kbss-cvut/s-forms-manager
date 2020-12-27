package cz.cvut.kbss.sformsmanager.model.dto;

import cz.cvut.kbss.sformsmanager.model.Context;

public class ContextDTO {

    private String uri;
    private boolean processed;

    public ContextDTO() {
    }

    public ContextDTO(String uri, boolean processed) {
        this.uri = uri;
        this.processed = processed;
    }

    public ContextDTO(Context context) {
        this.uri = context.getUriString();
        this.processed = context.isProcessed();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
