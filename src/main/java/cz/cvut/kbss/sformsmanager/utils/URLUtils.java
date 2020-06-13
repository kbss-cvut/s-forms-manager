package cz.cvut.kbss.sformsmanager.utils;

import lombok.experimental.UtilityClass;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@UtilityClass
public class URLUtils {

    public URI addParametersToUri(String remoteUrl, Map<String, String> queryParams) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(remoteUrl);
        for (Map.Entry<String, String> entry :
                queryParams.entrySet()) {
            builder.addParameter(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
