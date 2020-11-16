package cz.cvut.kbss.sformsmanager.utils;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public final class URLUtils {

    private URLUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static URI addParametersToUri(String remoteUrl, Map<String, String> queryParams) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(remoteUrl);
        for (Map.Entry<String, String> entry :
                queryParams.entrySet()) {
            builder.addParameter(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
