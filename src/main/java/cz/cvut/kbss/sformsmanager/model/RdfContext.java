package cz.cvut.kbss.sformsmanager.model;

import lombok.Value;

import java.net.URI;
import java.net.URISyntaxException;

@Value
public class RdfContext {

    private final URI uri;

    public static RdfContext create(String uri) {
        try {
            return new RdfContext(new URI(uri));
        } catch (URISyntaxException e) {
            return new RdfContext(null);
        }
    }

}
