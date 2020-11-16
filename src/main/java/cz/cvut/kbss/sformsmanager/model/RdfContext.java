package cz.cvut.kbss.sformsmanager.model;

import java.net.URI;

public final class RdfContext {

    private final URI uri;

    public RdfContext(URI uri) {
        this.uri = uri;
    }

    public URI getUri() {
        return this.uri;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof RdfContext)) return false;
        final RdfContext other = (RdfContext) o;
        final Object this$uri = this.getUri();
        final Object other$uri = other.getUri();
        if (this$uri == null ? other$uri != null : !this$uri.equals(other$uri)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $uri = this.getUri();
        result = result * PRIME + ($uri == null ? 43 : $uri.hashCode());
        return result;
    }

    public String toString() {
        return "RdfContext(uri=" + this.getUri() + ")";
    }
}
